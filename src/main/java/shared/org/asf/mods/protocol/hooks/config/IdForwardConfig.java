package org.asf.mods.protocol.hooks.config;

import java.io.File;
import org.asf.cyan.api.config.Configuration;
import org.asf.cyan.api.config.annotations.Comment;

public class IdForwardConfig extends Configuration<IdForwardConfig> {

	public IdForwardConfig(File folder) {
		super(folder.getAbsolutePath());
	}

	@Override
	public String filename() {
		return "idforward.ccfg";
	}

	@Override
	public String folder() {
		return "config/protocol/hooks";
	}

	@Comment("The IDForward proxy server IP (disabled if empty)")
	public String server = "";

	@Comment("The IDForward proxy HTTP port")
	public int port = 8084;

	@Comment("The server public key (stored in public.pem)")
	public String publicKey = "";

}
