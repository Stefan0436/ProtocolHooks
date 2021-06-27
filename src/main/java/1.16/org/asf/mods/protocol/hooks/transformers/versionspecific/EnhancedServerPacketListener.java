package org.asf.mods.protocol.hooks.transformers.versionspecific;

import java.util.Set;

import org.asf.cyan.api.events.extended.EventObject.EventResult;
import org.asf.cyan.api.fluid.annotations.VersionRegex;
import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.InjectAt;
import org.asf.cyan.fluid.api.transforming.Reflect;
import org.asf.cyan.fluid.api.transforming.TargetClass;
import org.asf.cyan.fluid.api.transforming.enums.InjectLocation;

import modkit.enhanced.events.chat.ChatEvent;
import modkit.enhanced.events.movement.LevelTeleportationEvent;
import modkit.enhanced.events.movement.TeleportationEvent;
import modkit.enhanced.events.objects.chat.ChatEventObject;
import modkit.enhanced.events.objects.movement.LevelTeleportationEventObject;
import modkit.enhanced.events.objects.movement.TeleportationEventObject;
import modkit.enhanced.player.EnhancedPlayer;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket.RelativeArgument;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

@FluidTransformer
@VersionRegex("1\\.16(\\.[0-5]+)?")
@TargetClass(target = "net.minecraft.server.network.ServerGamePacketListenerImpl")
public class EnhancedServerPacketListener {

	private ServerPlayer player;
	private MinecraftServer server;
	private int chatSpamTickCount = 0;

	@Reflect
	public void disconnect(Component comp) {
	}

	@InjectAt(location = InjectLocation.HEAD)
	public void teleport(double x, double y, double z, float xR, float yR, Set<RelativeArgument> set) {
		if (TeleportationEvent.getInstance().dispatch(
				new TeleportationEventObject(server, EnhancedPlayer.from(player), new Vec3(x, y, z), new Vec2(xR, yR)))
				.getResult() == EventResult.CANCEL)
			return;
		if (LevelTeleportationEvent.getInstance().dispatch(new LevelTeleportationEventObject(server,
				EnhancedPlayer.from(player), new Vec3(x, y, z), new Vec2(xR, yR), player.getLevel()))
				.getResult() == EventResult.CANCEL)
			return;
		return;
	}

	@InjectAt(location = InjectLocation.HEAD, targetCall = "startsWith(java.lang.String)", targetOwner = "java.lang.String")
	public void handleChat(String message) {
		if (!message.startsWith("/")) {
			ChatEventObject obj = new ChatEventObject(message, server, EnhancedPlayer.from(player));

			if (ChatEvent.getInstance().dispatch(obj).getResult() == EventResult.CANCEL)
				return;

			if (obj.getFormatter() != null) {
				Component comp = obj.getFormatter().format(message, EnhancedPlayer.from(player));
				server.getPlayerList().broadcastMessage(comp, ChatType.CHAT, player.getUUID());

				chatSpamTickCount += 20;
				if (chatSpamTickCount > 200 && !server.getPlayerList().isOp(player.getGameProfile()))
					disconnect(new TranslatableComponent("disconnect.spam"));

				return;
			}
		}

		return;
	}

}
