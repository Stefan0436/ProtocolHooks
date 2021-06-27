package modkit.enhanced.util;

import net.minecraft.network.chat.Component;
import modkit.enhanced.player.EnhancedPlayer;

/**
 * 
 * Minecraft Chat Formatter
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public interface ChatFormatter {

	/**
	 * Formats the message
	 * 
	 * @param message Chat message
	 * @param player  Player sending it
	 * @return Component instance (such as a TranslatableComponent or TextComponent)
	 */
	public Component format(String message, EnhancedPlayer player);

}
