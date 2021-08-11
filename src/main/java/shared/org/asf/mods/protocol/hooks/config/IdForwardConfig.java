package org.asf.mods.protocol.hooks.config;

import java.io.IOException;

import org.asf.cyan.api.config.annotations.Comment;
import org.asf.mods.protocol.hooks.ProtocolHooksCoremod;

import modkit.config.ModConfiguration;

public class IdForwardConfig extends ModConfiguration<IdForwardConfig, ProtocolHooksCoremod> {

	public IdForwardConfig(ProtocolHooksCoremod instance) throws IOException {
		super(instance);
	}

	@Override
	public String filename() {
		return "idforward.ccfg";
	}

	@Comment("The IDForward BungeeCord server IP (disabled if empty)")
	public String server = "";

	@Comment("The IDForward BungeeCord HTTP port")
	public int port = 8084;

	@Comment("The server public key (stored in public.pem)")
	public String publicKey = "";

}
