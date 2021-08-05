package modkit.enhanced.events.player;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.player.PlayerDamageEventObject;

/**
 * 
 * Server player damage event -- called when the player receives damage
 * 
 * @since PH 1.1
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerDamageEvent extends AbstractExtendedEvent<PlayerDamageEventObject> {

	private static PlayerDamageEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.player.damageF";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static PlayerDamageEvent getInstance() {
		return instance;
	}

}
