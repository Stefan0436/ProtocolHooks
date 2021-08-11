package org.asf.mods.protocol.hooks.transformers.network;

import org.asf.mods.protocol.hooks.components.IdForwardComponent;

import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.InjectAt;
import org.asf.cyan.fluid.api.transforming.TargetClass;
import org.asf.cyan.fluid.api.transforming.enums.InjectLocation;

import com.mojang.authlib.GameProfile;

import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;

@FluidTransformer
@TargetClass(target = "net.minecraft.server.network.ServerLoginPacketListenerImpl")
public class ServerLoginPacketListenerModification {

	private GameProfile gameProfile;
	private final MinecraftServer server = null;
	public final Connection connection = null;

	@InjectAt(location = InjectLocation.HEAD, targetCall = "canPlayerLogin(java.net.SocketAddress, com.mojang.authlib.GameProfile)", targetOwner = "net.minecraft.server.players.PlayerList")
	public void handleAcceptedLogin() {
		gameProfile = IdForwardComponent.login(gameProfile, server, connection);
		if (gameProfile == null)
			return;
	}

}
