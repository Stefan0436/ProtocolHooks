package modkit.enhanced.events.objects.player;

import org.asf.cyan.api.events.extended.EventObject;

import com.mojang.authlib.GameProfile;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

/**
 * 
 * Player login event -- called to process player login requests.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 * @since ProtocolHooks 1.2
 *
 */
public class PlayerLoginEventObject extends EventObject {
	private Component disconnectMessage = new TextComponent("Unknown reason for disconnect, sorry.");
	private GameProfile player;
	private Connection connection;

	/**
	 * Retrieves the player connection
	 * 
	 * @return Clientbound Connection instance
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Retrieves the GameProfile instance of the player trying to log in.
	 * 
	 * @return GameProfile instance.
	 */
	public GameProfile getPlayer() {
		return player;
	}

	/**
	 * Retrieves the player disconnect message
	 * 
	 * @return Disconnect Component, used if the event is cancelled
	 */
	public Component getDisconnectMessage() {
		return disconnectMessage;
	}

	/**
	 * Assigns the player disconnect message
	 * 
	 * @param message Player disconnect Component, used if the event is cancelled
	 */
	public void setDisconnectMessage(Component message) {
		disconnectMessage = message;
	}

	public PlayerLoginEventObject(GameProfile player, Connection connection) {
		this.player = player;
		this.connection = connection;
	}
}
