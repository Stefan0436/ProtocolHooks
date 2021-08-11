package org.asf.mods.protocol.hooks.transformers.versionspecific.server;

import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.InjectAt;
import org.asf.cyan.fluid.api.transforming.TargetClass;
import org.asf.cyan.fluid.api.transforming.enums.InjectLocation;

import modkit.enhanced.events.objects.server.ServerEventObject;
import modkit.enhanced.events.server.ServerStartupEvent;
import net.minecraft.server.MinecraftServer;

@FluidTransformer
@TargetClass(target = "net.minecraft.server.MinecraftServer")
public class MinecraftServerModification {

	@InjectAt(location = InjectLocation.HEAD, targetCall = "getMillis()", targetOwner = "net.minecraft.Util")
	protected void runServer() {
		Object obj = this;
		MinecraftServer protocolHooksServer = (MinecraftServer) obj;
		ServerStartupEvent.getInstance().dispatch(new ServerEventObject(protocolHooksServer)).getResult();
	}

}
