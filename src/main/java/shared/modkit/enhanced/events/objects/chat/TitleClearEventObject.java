package modkit.enhanced.events.objects.chat;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * 
 * Title clear event object -- allows for intercepting title resetting and
 * clearing packets
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TitleClearEventObject extends EventObject {
	private EnhancedPlayer player;
	private MinecraftServer server;
	private boolean reset;

	public TitleClearEventObject(boolean reset, MinecraftServer server, EnhancedPlayer player) {
		this.reset = reset;
		this.server = server;
		this.player = player;
	}

	/**
	 * Returns true if this is a reset packet, false if it is a clear packet
	 */
	public boolean isReset() {
		return reset;
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
