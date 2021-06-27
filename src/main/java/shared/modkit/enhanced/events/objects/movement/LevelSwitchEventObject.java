package modkit.enhanced.events.objects.movement;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

/**
 * 
 * Server level switch event object -- controller for level switch actions.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class LevelSwitchEventObject extends EventObject {
	private EnhancedPlayer player;
	private MinecraftServer server;
	private ServerLevel destination;

	public LevelSwitchEventObject(MinecraftServer server, EnhancedPlayer player, ServerLevel destination) {
		this.server = server;
		this.player = player;
		this.destination = destination;
	}
	
	/**
	 * Retrieves the destination level
	 */
	public ServerLevel getDestination() {
		return destination;
	}

	/**
	 * Retrieves the minecraft server
	 * 
	 * @return MinecraftServer instance
	 */
	public MinecraftServer getServer() {
		return server;
	}

	/**
	 * Retrieves the player sending the message
	 * 
	 * @return EnhancedPlayer instance
	 */
	public EnhancedPlayer getPlayer() {
		return player;
	}

}
