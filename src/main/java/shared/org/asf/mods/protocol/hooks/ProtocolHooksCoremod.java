package org.asf.mods.protocol.hooks;

import modkit.util.ContainerConditions;
import modkit.util.EventUtil;

import org.asf.cyan.api.modloader.Modloader;
import org.asf.cyan.api.modloader.TargetModloader;

import org.asf.cyan.mods.AbstractCoremod;
import org.asf.cyan.mods.events.AttachEvent;

@TargetModloader(value = Modloader.class, any = true)
public class ProtocolHooksCoremod extends AbstractCoremod {

	// TODO: scoreboards
	// TODO: custom levels
	// TODO: asynchronous level loading
	// TODO: name tags

	@Override
	protected void setupCoremod() {
	}

	@AttachEvent(value = "mods.all.loaded", synchronize = true)
	public void afterAllMods() {
		String pkg = getClass().getPackageName();
		EventUtil.registerContainer(ContainerConditions.COMMON, () -> pkg + ".events.CommonEvents");

		if (System.getProperty("debug.protocol.hooks") != null)
			EventUtil.registerContainer(ContainerConditions.COMMON, () -> pkg + ".events.TestEvents");
	}
}
