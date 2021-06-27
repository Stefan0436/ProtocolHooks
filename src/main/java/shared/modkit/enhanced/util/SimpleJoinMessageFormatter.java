package modkit.enhanced.util;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

/**
 * 
 * Simple Minecraft Join Message Formatter - using format strings.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class SimpleJoinMessageFormatter implements JoinMessageFormatter {

	private String format;

	/**
	 * Instantiates the join message formatter
	 * 
	 * @param format Format string ($0 represents the player display name, $1
	 *               represents the actual player name)
	 */
	public SimpleJoinMessageFormatter(String format) {
		this.format = format;
	}

	@Override
	public Component format(EnhancedPlayer player) {
		return new TextComponent(
				format.replace("$0", player.getDisplayName().getString()).replace("$1", player.getName().getString()));
	}

}
