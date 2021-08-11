package org.asf.mods.idforward.bungee;

import java.io.File;

import org.asf.cyan.api.config.Configuration;

public class ServerConfig extends Configuration<ServerConfig> {

	public ServerConfig(File base) {
		super(base.getAbsolutePath());
	}
	
	public int port = 8084;
	public String ip = "127.0.0.1";

	@Override
	public String filename() {
		return "server.ccfg";
	}

	@Override
	public String folder() {
		return "";
	}

}
