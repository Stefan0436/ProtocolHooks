package org.asf.mods.idforward.bungee.commands;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.UUID;

import org.asf.mods.idforward.bungee.IDForward;
import org.asf.rats.processors.HttpUploadProcessor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RequestPlayerCommand extends HttpUploadProcessor {

	@Override
	public void process(String contentType, Socket client, String method) {
		if (!contentType.equals("application/json")) {
			setResponseCode(400);
			setResponseMessage("Bad request: not a json");
			setBody(getServer().genError(getResponse(), getRequest()));
			return;
		}

		JsonObject player = new JsonParser().parse(getRequestBody()).getAsJsonObject();
		if (!player.has("uuid") || !player.has("playername")) {
			setResponseCode(400);
			setResponseMessage("Bad request: malformed request");
			setBody(getServer().genError(getResponse(), getRequest()));
			return;
		}

		String name = player.get("playername").getAsString();
		String uuid = player.get("uuid").getAsString();

		ProxyServer server = ProxyServer.getInstance();
		for (ProxiedPlayer t : server.getPlayers()) {
			UUID onlineUUID = t.getUniqueId();
			UUID offlineUUID;
			try {
				offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + t.getName()).getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + t.getName()).getBytes());
			}

			if (name.equals(t.getName()) && uuid.equals(offlineUUID.toString())) {
				JsonObject obj = new JsonObject();
				obj.addProperty("uuid", onlineUUID.toString());
				obj.addProperty("offlineuuid", offlineUUID.toString());
				obj.addProperty("playername", t.getName());
				obj.addProperty("displayname", t.getDisplayName());
				if (t.getSocketAddress() instanceof InetSocketAddress)
					obj.addProperty("address",
							((InetSocketAddress) t.getSocketAddress()).getAddress().getHostAddress());
				else
					obj.addProperty("address", "unknown");

				byte[] payload;
				try {
					payload = obj.toString().getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					payload = obj.toString().getBytes();
				}
				obj.addProperty("signature", Base64.getEncoder()
						.encodeToString(((IDForward) server.getPluginManager().getPlugin("IDForward")).sign(payload)));
				setBody("application/json", obj.toString());
				return;
			}
		}

		setResponseCode(403);
		setResponseMessage("Forbidden");
		setBody(getServer().genError(getResponse(), getRequest()));
	}

	@Override
	public HttpUploadProcessor createNewInstance() {
		return new RequestPlayerCommand();
	}

	@Override
	public String path() {
		return "/getplayer";
	}

}
