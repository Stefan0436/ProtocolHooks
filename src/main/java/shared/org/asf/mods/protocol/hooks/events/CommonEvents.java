package org.asf.mods.protocol.hooks.events;

import modkit.events.ingame.level.ServerLevelLoadEvent;
import modkit.events.objects.ingame.level.ServerLevelLoadEventObject;

import org.asf.cyan.mods.events.IEventListenerContainer;
import org.asf.cyan.mods.events.SimpleEvent;

public class CommonEvents implements IEventListenerContainer {

	@SimpleEvent(ServerLevelLoadEvent.class)
	public void loadWorld(ServerLevelLoadEventObject event) {
		// Called on world load,
		// The example transformer is redundant, this event provides it.
	}

}
