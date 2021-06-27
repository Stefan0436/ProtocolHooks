package modkit.enhanced.events.objects.chat;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import modkit.enhanced.util.ChatFormatter;
import net.minecraft.server.MinecraftServer;

/**
 * 
 * Server chat event object -- allows for manipulation of chat messages on the
 * server.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class ChatEventObject extends EventObject {
	private ChatFormatter formatter;
	private EnhancedPlayer player;
	private MinecraftServer server;
	private String message;

	public ChatEventObject(String message, MinecraftServer server, EnhancedPlayer player) {
		this.message = message;
		this.server = server;
		this.player = player;
	}

	/**
	 * Retrieves the non-filtered chat message
	 * 
	 * @return Chat message string
	 */
	public String getMessage() {
		return message;
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

	/**
	 * Assigns the chat formatter used to process chat messages
	 * 
	 * @param formatter ChatFormatter instance
	 */
	public void setFormatter(ChatFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * Retrieves the current chat formatter or null when using the vanilla formatter
	 * 
	 * @return ChatFormatter instance or null
	 */
	public ChatFormatter getFormatter() {
		return formatter;
	}
}
