package modkit.enhanced.events.chat;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.chat.TitleEventObject;

/**
 * 
 * Title event -- allows for manipulation of title messages on the server. (protocol hooks titles only)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TitleEvent extends AbstractExtendedEvent<TitleEventObject> {

	private static TitleEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.title";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static TitleEvent getInstance() {
		return instance;
	}

}
