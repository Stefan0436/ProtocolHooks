package org.asf.mods.protocol.hooks.transformers.network;

import java.util.UUID;

import org.asf.cyan.api.fluid.annotations.PlatformOnly;
import org.asf.cyan.api.modloader.information.game.LaunchPlatform;
import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.InjectAt;
import org.asf.cyan.fluid.api.transforming.TargetClass;
import org.asf.cyan.fluid.api.transforming.TargetType;
import org.asf.cyan.fluid.api.transforming.enums.InjectLocation;
import org.asf.cyan.fluid.api.transforming.util.CodeControl;

import modkit.enhanced.events.objects.player.PlayerJoinEventObject;
import modkit.enhanced.events.player.PlayerJoinEvent;
import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

@FluidTransformer
@PlatformOnly(LaunchPlatform.SPIGOT)
@TargetClass(target = "net.minecraft.server.players.PlayerList")
public abstract class SpigotPlayerListModification {

	private MinecraftServer server;

	@InjectAt(location = InjectLocation.HEAD)
	public void broadcastMessage(@TargetType(target = "net.minecraft.network.chat.Component") Component component,
			@TargetType(target = "net.minecraft.network.chat.ChatType") ChatType type, UUID uuid) {
		if (component instanceof TranslatableComponent && type == ChatType.SYSTEM && uuid.equals(Util.NIL_UUID)) {
			TranslatableComponent trComp = (TranslatableComponent) component;
			if (trComp.getArgs()[0] instanceof PlatformSharedEnhancedServerPacketListener) {
				PlatformSharedEnhancedServerPacketListener listener = (PlatformSharedEnhancedServerPacketListener) trComp
						.getArgs()[0];
				if (trComp
						.getKey().equals("multiplayer.player.joined")
						|| trComp.getKey().equals("multiplayer.player.joined.renamed")) {
					component = listener.protocolHooksJoinFormatter.format(listener.getEnhancedPlayer());
				}
			}
		}
	}

	@InjectAt(location = InjectLocation.HEAD, targetCall = "broadcastMessage(net.minecraft.network.chat.Component, net.minecraft.network.chat.ChatType, java.util.UUID)")
	public void postChunkLoadJoin(ServerPlayer player, ServerLevel level, Connection connection,
			@TargetType(target = "net.minecraft.server.network.ServerGamePacketListenerImpl") Object playerListener,
			CompoundTag tag, String addr, String oldName) {
		PlayerJoinEventObject joinEventObjectProtocolHooks = new PlayerJoinEventObject(oldName, server,
				EnhancedPlayer.from(player));
		PlayerJoinEvent.getInstance().dispatch(joinEventObjectProtocolHooks);

		TranslatableComponent joinMessage = CodeControl.ALOAD(8);
		PlatformSharedEnhancedServerPacketListener listener = (PlatformSharedEnhancedServerPacketListener) playerListener;
		listener.protocolHooksJoinFormatter = joinEventObjectProtocolHooks.getFormatter();
		joinMessage.getArgs()[0] = listener;
	}
}
