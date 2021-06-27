package modkit.enhanced.events.movement;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.movement.LevelTeleportationEventObject;

/**
 * 
 * Server level teleportation event -- controller for teleportation actions
 * (with level switching)
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class LevelTeleportationEvent extends AbstractExtendedEvent<LevelTeleportationEventObject> {

	private static LevelTeleportationEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.teleport.withlevel";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static LevelTeleportationEvent getInstance() {
		return instance;
	}

}
