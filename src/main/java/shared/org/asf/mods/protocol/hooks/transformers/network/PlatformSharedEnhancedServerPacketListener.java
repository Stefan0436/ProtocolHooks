package org.asf.mods.protocol.hooks.transformers.network;

import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.TargetClass;

import modkit.enhanced.player.EnhancedPlayer;
import modkit.enhanced.util.JoinMessageFormatter;
import net.minecraft.server.level.ServerPlayer;

@FluidTransformer
@TargetClass(target = "net.minecraft.server.network.ServerGamePacketListenerImpl")
public class PlatformSharedEnhancedServerPacketListener {

	private ServerPlayer player;
	public JoinMessageFormatter protocolHooksJoinFormatter;
	
	public EnhancedPlayer getEnhancedPlayer() {
		return EnhancedPlayer.from(player);
	}

}
