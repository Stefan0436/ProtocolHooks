package modkit.enhanced.events.objects.input;

import org.asf.cyan.api.events.extended.EventObject;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * 
 * Server player input event object -- for the client movement packets. (used in
 * the base game when the player is a passenger)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerInputEventObject extends EventObject {
	private EnhancedPlayer player;
	private MinecraftServer server;
	private float xxa;
	private float zza;
	private boolean jumping;
	private boolean shift;

	public PlayerInputEventObject(float xxa, float zza, boolean jumping, boolean shift, MinecraftServer server,
			EnhancedPlayer player) {
		this.xxa = xxa;
		this.zza = zza;
		this.jumping = jumping;
		this.shift = shift;
		this.server = server;
		this.player = player;
	}

	/**
	 * Retrieves the Xxa value of the packet
	 * 
	 * @return Xxa float value
	 */
	public float getXxa() {
		return xxa;
	}

	/**
	 * Retrieves the Zza value of the packet
	 * 
	 * @return Zza float value
	 */
	public float getZza() {
		return zza;
	}

	/**
	 * Retrieves the shift key status
	 * 
	 * @return True if pressed, false otherwise
	 */
	public boolean isShiftPressed() {
		return shift;
	}

	/**
	 * Retrieves the jump key status
	 * 
	 * @return True if jumping, false otherwise
	 */
	public boolean isJumping() {
		return jumping;
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
