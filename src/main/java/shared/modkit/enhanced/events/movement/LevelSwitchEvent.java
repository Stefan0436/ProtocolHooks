package modkit.enhanced.events.movement;

import org.asf.cyan.api.events.extended.AbstractExtendedEvent;

import modkit.enhanced.events.objects.movement.LevelSwitchEventObject;

/**
 * 
 * Server level switch event -- controller for level switch actions.
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class LevelSwitchEvent extends AbstractExtendedEvent<LevelSwitchEventObject> {

	private static LevelSwitchEvent instance;

	@Override
	public String channelName() {
		return "protocol.hooks.events.teleport.levelonly";
	}

	@Override
	public void afterInstantiation() {
		instance = this;
	}

	public static LevelSwitchEvent getInstance() {
		return instance;
	}

}
