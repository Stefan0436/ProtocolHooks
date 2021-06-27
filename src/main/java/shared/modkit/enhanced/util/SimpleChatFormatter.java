package modkit.enhanced.util;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

/**
 * 
 * Simple Minecraft Chat Formatter - using format strings.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class SimpleChatFormatter implements ChatFormatter {

	private String format;

	/**
	 * Instantiates the chat formatter
	 * 
	 * @param format Format string ($0 represents the player display name, $1
	 *               represents the player name, $2 represents the message)
	 */
	public SimpleChatFormatter(String format) {
		this.format = format;
	}

	@Override
	public Component format(String message, EnhancedPlayer player) {
		return new TextComponent(format.replace("$0", player.getDisplayName().getString())
				.replace("$1", player.getName().getString()).replace("$2", message));
	}

}
