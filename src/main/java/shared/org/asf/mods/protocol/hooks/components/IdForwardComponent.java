package org.asf.mods.protocol.hooks.components;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

import org.asf.cyan.api.common.CyanComponent;
import org.asf.mods.protocol.hooks.events.CommonEvents;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;

import modkit.util.Colors;

import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;

public class IdForwardComponent extends CyanComponent {

	public static GameProfile login(GameProfile gameProfile, MinecraftServer server, Connection connection) {
		if (!CommonEvents.idForwardConfiguration.server.isEmpty()) {
			UUID id = gameProfile.getId();
			if (id == null)
				try {
					id = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameProfile.getName()).getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					id = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameProfile.getName()).getBytes());
				}

			try {
				URL authURL = new URL("http", CommonEvents.idForwardConfiguration.server,
						CommonEvents.idForwardConfiguration.port, "/getplayer");

				HttpURLConnection conn = (HttpURLConnection) authURL.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");

				JsonObject payload = new JsonObject();
				payload.addProperty("uuid", gameProfile.getId().toString());
				payload.addProperty("playername", gameProfile.getName());
				try {
					conn.getOutputStream().write(payload.toString().getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					conn.getOutputStream().write(payload.toString().getBytes());
				}
				String response = new String(conn.getInputStream().readAllBytes());
				conn.disconnect();

				JsonObject data = JsonParser.parseString(response).getAsJsonObject();
				if (!data.has("signature"))
					throw new IOException("Missing signature.");

				byte[] sig = Base64.getDecoder().decode(data.get("signature").getAsString());
				data.remove("signature");
				if (!CommonEvents.checkSignature(data.toString().getBytes(), sig))
					throw new IOException("Invalid signature received.");

				id = UUID.fromString(data.get("uuid").getAsString());
				gameProfile = new GameProfile(id, data.get("playername").getAsString());
				info("The IDForward server authenticated " + gameProfile.getName() + ", new UUID: " + id);
			} catch (Exception e) {
				error("Could not authenticate player " + gameProfile.getName()
						+ " with the IDForward server, disconnecting them.", e);
				connection.send(new ClientboundLoginDisconnectPacket(new TextComponent(Colors.LIGHT_RED
						+ "Failed to authenticate you with the proxy server, please try again later.")));
				connection.disconnect(new TextComponent(Colors.LIGHT_RED
						+ "Failed to authenticate you with the proxy server, please try again later."));
				return gameProfile;
			}
		}
		return gameProfile;
	}

}
