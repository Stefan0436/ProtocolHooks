package modkit.enhanced.events.objects.chat;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import modkit.enhanced.player.titles.MinecraftTitle;
import net.minecraft.server.MinecraftServer;

/**
 * 
 * Server title event object -- allows for manipulation of titles on the server. (protocol hooks titles only)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TitleEventObject extends EventObject {
	private EnhancedPlayer player;
	private MinecraftServer server;
	private MinecraftTitle title;

	public TitleEventObject(MinecraftTitle title, MinecraftServer server, EnhancedPlayer player) {
		this.title = title;
		this.server = server;
		this.player = player;
	}

	/**
	 * Retrieves the title to be shown
	 * 
	 * @return MinecraftTitle instance
	 */
	public MinecraftTitle getTitle() {
		return title;
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
