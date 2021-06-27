package modkit.enhanced.events.chat;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.chat.TitleClearEventObject;

/**
 * 
 * Title clear event -- allows for intercepting title resetting and clearing
 * packets
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TitleClearEvent extends AbstractExtendedEvent<TitleClearEventObject> {

	private static TitleClearEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.title.clear";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static TitleClearEvent getInstance() {
		return instance;
	}

}
