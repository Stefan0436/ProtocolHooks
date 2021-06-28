package modkit.enhanced.events.server;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.server.ServerEventObject;

/**
 * 
 * Server startup event -- called after the server has started.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class ServerStartupEvent extends AbstractExtendedEvent<ServerEventObject> {

	private static ServerStartupEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.server.startup";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static ServerStartupEvent getInstance() {
		return instance;
	}

}
