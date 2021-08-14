package modkit.enhanced.events.player;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.player.PlayerLoginEventObject;

/**
 * 
 * Player login event -- called to process player login requests.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 * @since ProtocolHooks 1.2
 *
 */
public class PlayerLoginEvent extends AbstractExtendedEvent<PlayerLoginEventObject> {

	private static PlayerLoginEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.login.player";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static PlayerLoginEvent getInstance() {
		return instance;
	}

}
