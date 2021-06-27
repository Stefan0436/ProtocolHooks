package org.asf.mods.protocol.hooks.transformers.versionspecific;

import java.util.concurrent.Future;

import org.asf.cyan.api.events.extended.EventObject.EventResult;
import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.InjectAt;
import org.asf.cyan.fluid.api.transforming.TargetClass;
import org.asf.cyan.fluid.api.transforming.TargetType;
import org.asf.cyan.fluid.api.transforming.enums.InjectLocation;

import io.netty.util.concurrent.GenericFutureListener;
import modkit.enhanced.events.chat.TitleClearEvent;
import modkit.enhanced.events.objects.chat.TitleClearEventObject;
import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

@FluidTransformer
@TargetClass(target = "net.minecraft.server.network.ServerGamePacketListenerImpl")
public class PlatformSharedEnhancedServerPacketListener {

	private ServerPlayer player;
	private MinecraftServer server;

	@InjectAt(location = InjectLocation.HEAD)
	public void send(@TargetType(target = "net.minecraft.network.protocol.Packet") Packet<?> packet,
			GenericFutureListener<? extends Future<? super Void>> future) {
		if (packet instanceof ClientboundClearTitlesPacket) {
			ClientboundClearTitlesPacket ctPacket = (ClientboundClearTitlesPacket) packet;
			TitleClearEventObject cyanEvent = new TitleClearEventObject(ctPacket.shouldResetTimes(), server,
					EnhancedPlayer.from(player));
			EventResult result = TitleClearEvent.getInstance().dispatch(cyanEvent).getResult();
			if (result == EventResult.CANCEL) {
				return;
			}
		}
		return;
	}
}
