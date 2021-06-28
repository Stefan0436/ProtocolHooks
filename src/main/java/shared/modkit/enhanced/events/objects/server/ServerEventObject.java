package modkit.enhanced.events.objects.server;

import org.asf.cyan.api.events.extended.EventObject;

import net.minecraft.server.MinecraftServer;

/**
 * 
 * Server event container -- contains the server
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class ServerEventObject extends EventObject {
	private MinecraftServer server;

	public ServerEventObject(MinecraftServer server) {
		this.server = server;
	}

	/**
	 * Retrieves the server that is shutting down
	 */
	public MinecraftServer getServer() {
		return server;
	}
}
