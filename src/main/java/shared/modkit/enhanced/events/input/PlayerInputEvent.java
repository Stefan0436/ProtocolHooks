package modkit.enhanced.events.input;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.input.PlayerInputEventObject;

/**
 * 
 * Server player input event -- for the client movement packets. (used in the
 * base game when the player is a passenger)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerInputEvent extends AbstractExtendedEvent<PlayerInputEventObject> {

	private static PlayerInputEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.player.movement.input";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static PlayerInputEvent getInstance() {
		return instance;
	}

}
