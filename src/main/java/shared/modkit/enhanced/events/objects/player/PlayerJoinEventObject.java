package modkit.enhanced.events.objects.player;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import modkit.enhanced.util.JoinMessageFormatter;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;

/**
 * 
 * Server player join event object -- called to process player join logic (often
 * called before the Cyan client handshake)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerJoinEventObject extends EventObject {
	private JoinMessageFormatter formatter;
	private EnhancedPlayer player;
	private MinecraftServer server;
	private String oldname;

	public PlayerJoinEventObject(String oldname, MinecraftServer server, EnhancedPlayer player) {
		this.server = server;
		this.player = player;
		this.oldname = oldname;

		formatter = pl -> {
			if (!pl.getGameProfile().getName().equals(getPreviousName()))
				return new TranslatableComponent("multiplayer.player.joined.renamed", pl.getDisplayName(),
						getPreviousName());
			else
				return new TranslatableComponent("multiplayer.player.joined", pl.getDisplayName());
		};
	}

	/**
	 * Retrieves the name previously used by this player before joining the server
	 * again. (might match the current name)
	 */
	public String getPreviousName() {
		return oldname;
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
	 * Assigns the join message formatter used to process the player join message
	 * 
	 * @param formatter JoinMessageFormatter instance
	 */
	public void setFormatter(JoinMessageFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * Retrieves the current join message formatter
	 * 
	 * @return JoinMessageFormatter instance
	 */
	public JoinMessageFormatter getFormatter() {
		return formatter;
	}
}
