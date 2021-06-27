package modkit.enhanced.events.chat;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.chat.ChatEventObject;

/**
 * 
 * Server chat event -- allows for manipulation of chat messages on the server.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class ChatEvent extends AbstractExtendedEvent<ChatEventObject> {

	private static ChatEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.chat";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static ChatEvent getInstance() {
		return instance;
	}

}
