package modkit.enhanced.events.player;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.player.PlayerJoinEventObject;

/**
 * 
 * Server player join event -- called to process player join logic (often called
 * before the Cyan client handshake)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class PlayerJoinEvent extends AbstractExtendedEvent<PlayerJoinEventObject> {

	private static PlayerJoinEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.join.player";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static PlayerJoinEvent getInstance() {
		return instance;
	}

}
