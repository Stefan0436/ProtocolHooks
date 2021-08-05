package modkit.enhanced.events.objects.player;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.damagesource.DamageSource;

/**
 * 
 * Player Damage Event Object -- used in the player damage and death events.
 * 
 * @since PH 1.1
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerDamageEventObject extends EventObject {
	private EnhancedPlayer player;
	private MinecraftServer server;
	private DamageSource source;
	private float damage;

	public PlayerDamageEventObject(DamageSource source, MinecraftServer server, EnhancedPlayer player, float damage) {
		this.server = server;
		this.player = player;
		this.source = source;
		this.damage = damage;
	}

	/**
	 * Retrieves the damage count (-1 on death)
	 * 
	 * @return Damage floating-point value
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * Retrieves the damage source
	 */
	public DamageSource getDamageSource() {
		return source;
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
