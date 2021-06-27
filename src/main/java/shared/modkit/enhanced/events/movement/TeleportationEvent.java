package modkit.enhanced.events.movement;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.movement.TeleportationEventObject;

/**
 * 
 * Server teleportation event -- controller for teleportation packets.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TeleportationEvent extends AbstractExtendedEvent<TeleportationEventObject> {

	private static TeleportationEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.teleport.coordinates";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static TeleportationEvent getInstance() {
		return instance;
	}

}
