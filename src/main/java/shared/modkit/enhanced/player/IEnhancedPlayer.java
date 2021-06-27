package modkit.enhanced.player;

import modkit.enhanced.player.titles.MinecraftTitle;
import modkit.enhanced.player.titles.MinecraftTitle.TitleComponent;
import modkit.enhanced.player.titles.TitleIntervalConfiguration;
import modkit.enhanced.player.titles.TitleType;
import modkit.permissions.Permission;
import modkit.permissions.PermissionManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

/**
 * 
 * Enhanced Player Object - replacement ServerPlayer type (version-shared
 * objects)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public interface IEnhancedPlayer {

	/**
	 * Converts the enhanced player object to its vanilla counterpart.
	 * 
	 * @return ServerPlayer instance
	 */
	public ServerPlayer toGameType();

	/**
	 * Retrieves the X rotation axis
	 */
	public float getXRot();

	/**
	 * Retrieves the Y rotation axis
	 */
	public float getYRot();

	/**
	 * Retrieves the server instance
	 * 
	 * @return MinecraftServer instance.
	 */
	public MinecraftServer getServer();

	/**
	 * Assigns a display name for the player
	 * 
	 * @param name Display name component
	 */
	public void setCustomDisplayName(Component name);

	/**
	 * Assigns a display name for the player
	 * 
	 * @param name Display name string
	 */
	public void setCustomDisplayName(String name);

	/**
	 * Assigns a tab list display name for the player
	 * 
	 * @param name Display name component
	 */
	public void setCustomTabListDisplayName(Component name);

	/**
	 * Assigns a tab list display name for the player
	 * 
	 * @param name Display name string
	 */
	public void setCustomTabListDisplayName(String name);

	/**
	 * Removes the custom tab list display name for the player
	 */
	public void removeCustomTabListDisplayName();

	/**
	 * Removes the custom display name for the player
	 */
	public void removeCustomDisplayName();

	/**
	 * Instructs the remote client to refresh its permissions (call after modifying
	 * player permissions)
	 */
	public default void reloadPermissions() {
		getServer().getPlayerList().sendPlayerPermissionLevel(toGameType());
	}

	/**
	 * Retrieves all permission nodes for this player
	 * 
	 * @return Array of permission nodes
	 */
	public default Permission[] getPermissions() {
		return PermissionManager.getInstance().getPermissions(toGameType());
	}

	/**
	 * Checks if the given permission is valid for this player
	 * 
	 * @param permission Permission node to verify
	 * @return True if valid, false otherwise
	 */
	public default boolean hasPermission(String permission) {
		return PermissionManager.getInstance().hasPermission(toGameType(), permission);
	}

	/**
	 * Shows a title of the given type
	 * 
	 * @param type    Title type
	 * @param message Title message
	 */
	public default void showTitle(TitleType type, String message) {
		showTitle(type, new TextComponent(message));
	}

	/**
	 * Shows a title of the given type
	 * 
	 * @param type    Title type
	 * @param message Title message component
	 */
	public default void showTitle(TitleType type, Component message) {
		showTitle(type, message, null);
	}

	/**
	 * Shows a title of the given type and interval configuration
	 * 
	 * @param type    Title type
	 * @param message Title message
	 * @param config  Title interval configuration
	 */
	public default void showTitle(TitleType type, String message, TitleIntervalConfiguration config) {
		showTitle(type, new TextComponent(message), config);
	}

	/**
	 * Shows a title to the player
	 * 
	 * @param title Title to show
	 */
	public default void showTitle(MinecraftTitle title) {
		showTitles(title);
	}

	/**
	 * Shows a title of the given type and interval configuration
	 * 
	 * @param type    Title type
	 * @param message Title message component
	 * @param config  Title interval configuration
	 */
	public default void showTitle(TitleType type, Component message, TitleIntervalConfiguration config) {
		showTitle(MinecraftTitle.create().addComponent(type, message).setIntervalConfiguration(config));
	}

	/**
	 * Shows multiple titles
	 * 
	 * @param config     Title interval configuration
	 * @param components Title components to show
	 */
	public default void showTitles(TitleIntervalConfiguration config, TitleComponent components) {
		showTitles(MinecraftTitle.create().addComponents(components).setIntervalConfiguration(config));
	}

	/**
	 * Shows multiple titles
	 * 
	 * @param titles Titles to show
	 */
	public void showTitles(MinecraftTitle... titles);

	/**
	 * Clears the player titles
	 */
	public void clearTitles();

	/**
	 * Resets the player titles
	 */
	public void resetTitles();

}
