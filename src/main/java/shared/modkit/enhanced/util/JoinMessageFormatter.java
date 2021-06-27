package modkit.enhanced.util;

import net.minecraft.network.chat.Component;
import modkit.enhanced.player.EnhancedPlayer;

/**
 * 
 * Minecraft Join Message Formatter
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public interface JoinMessageFormatter {

	/**
	 * Formats the message
	 * 
	 * @param player Player joining the game
	 * @return Component instance (such as a TranslatableComponent or TextComponent)
	 */
	public Component format(EnhancedPlayer player);

}
