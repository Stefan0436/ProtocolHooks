package modkit.enhanced.events.player;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.player.PlayerDamageEventObject;

/**
 * 
 * Server player death event -- called on player death
 * 
 * @since PH 1.1
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerDeathEvent extends AbstractExtendedEvent<PlayerDamageEventObject> {

	private static PlayerDeathEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.player.death";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static PlayerDeathEvent getInstance() {
		return instance;
	}

}
