package org.asf.mods.protocol.hooks.transformers.network;

import org.asf.cyan.api.events.extended.EventObject.EventResult;
import org.asf.cyan.api.fluid.annotations.PlatformExclude;
import org.asf.cyan.api.modloader.information.game.LaunchPlatform;
import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.InjectAt;
import org.asf.cyan.fluid.api.transforming.TargetClass;
import org.asf.cyan.fluid.api.transforming.TargetType;
import org.asf.cyan.fluid.api.transforming.enums.InjectLocation;

import modkit.enhanced.events.input.PlayerInputEvent;
import modkit.enhanced.events.movement.LevelTeleportationEvent;
import modkit.enhanced.events.movement.TeleportationEvent;
import modkit.enhanced.events.objects.input.PlayerInputEventObject;
import modkit.enhanced.events.objects.movement.LevelTeleportationEventObject;
import modkit.enhanced.events.objects.movement.TeleportationEventObject;
import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;

@FluidTransformer
@PlatformExclude(LaunchPlatform.SPIGOT)
@TargetClass(target = "net.minecraft.server.network.ServerGamePacketListenerImpl")
public class SharedEnhancedServerPacketListener {

	private ServerPlayer player;
	private MinecraftServer server;
	private Vec3 awaitingPositionFromClient;

	@InjectAt(location = InjectLocation.HEAD)
	public void handlePlayerInput(
			@TargetType(target = "net.minecraft.network.protocol.game.ServerboundPlayerInputPacket") ServerboundPlayerInputPacket packet) {
		if (PlayerInputEvent.getInstance()
				.dispatch(new PlayerInputEventObject(packet.getXxa(), packet.getZza(), packet.isJumping(),
						packet.isShiftKeyDown(), server, EnhancedPlayer.from(player)))
				.getResult() == EventResult.CANCEL)
			return;
		return;
	}

	@InjectAt(location = InjectLocation.HEAD, targetCall = "absMoveTo(double,double,double,float,float)", targetOwner = "net.minecraft.server.level.ServerPlayer")
	public void handleAcceptTeleportPacket(
			@TargetType(target = "net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket") ServerboundAcceptTeleportationPacket packet) {
		if (cyanHandleAcceptTeleportPacket(packet))
			return;
		return;
	}

	private boolean cyanHandleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket packet) {
		if (TeleportationEvent.getInstance()
				.dispatch(new TeleportationEventObject(server, EnhancedPlayer.from(player), awaitingPositionFromClient,
						new Vec2(EnhancedPlayer.from(player).getXRot(), EnhancedPlayer.from(player).getYRot())))
				.getResult() == EventResult.CANCEL) {
			awaitingPositionFromClient = null;
			return true;
		}
		if (LevelTeleportationEvent.getInstance()
				.dispatch(new LevelTeleportationEventObject(server, EnhancedPlayer.from(player),
						awaitingPositionFromClient,
						new Vec2(EnhancedPlayer.from(player).getXRot(), EnhancedPlayer.from(player).getYRot()),
						player.getLevel()))
				.getResult() == EventResult.CANCEL) {
			awaitingPositionFromClient = null;
			return true;
		}
		return false;
	}

}
