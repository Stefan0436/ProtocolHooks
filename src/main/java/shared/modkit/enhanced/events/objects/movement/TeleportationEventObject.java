package modkit.enhanced.events.objects.movement;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/**
 * 
 * Server teleportation event object -- controller for teleportation actions.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TeleportationEventObject extends EventObject {
	private EnhancedPlayer player;
	private MinecraftServer server;
	private Vec3 destination;
	private Vec2 rotation;

	public TeleportationEventObject(MinecraftServer server, EnhancedPlayer player, Vec3 destination, Vec2 rot) {
		this.server = server;
		this.player = player;
		this.destination = destination;
		this.rotation = rot;
	}

	/**
	 * Retrieves the destination rotation
	 */
	public Vec2 getRotation() {
		return rotation;
	}

	/**
	 * Retrieves the destination coordinates
	 */
	public Vec3 getDestination() {
		return destination;
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
