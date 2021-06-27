package modkit.enhanced.player;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.asf.cyan.api.events.extended.EventObject.EventResult;

import com.mojang.authlib.GameProfile;

import modkit.enhanced.events.chat.TitleEvent;
import modkit.enhanced.events.movement.LevelSwitchEvent;
import modkit.enhanced.events.movement.LevelTeleportationEvent;
import modkit.enhanced.events.movement.TeleportationEvent;
import modkit.enhanced.events.objects.chat.TitleEventObject;
import modkit.enhanced.events.objects.movement.LevelSwitchEventObject;
import modkit.enhanced.events.objects.movement.LevelTeleportationEventObject;
import modkit.enhanced.events.objects.movement.TeleportationEventObject;
import modkit.enhanced.player.titles.MinecraftTitle;
import modkit.enhanced.player.titles.MinecraftTitle.TitleComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/**
 * 
 * Enhanced Player Object - replacement ServerPlayer type
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class EnhancedPlayer extends ServerPlayer implements IEnhancedPlayer {

	private Component customDisplayName = null;
	private Component customTabListDisplayName = null;

	/**
	 * 1.17 internal, do not use, use
	 * {@link #EnhancedPlayer(MinecraftServer server, ServerLevel level, GameProfile profile, ServerPlayerGameMode gamemode)}
	 * for 1.16 compatibility.
	 */
	public EnhancedPlayer(MinecraftServer var1, ServerLevel var2, GameProfile var3) {
		super(var1, var2, var3);
	}

	public EnhancedPlayer(MinecraftServer server, ServerLevel level, GameProfile profile,
			ServerPlayerGameMode gamemode) {
		this(server, level, profile);
	}

	/**
	 * Converts the given player type to the EnhancedPlayer type.
	 * 
	 * @param player ServerPlayer instance
	 * @return EnhancedPlayer instance
	 */
	public static EnhancedPlayer from(ServerPlayer player) {
		if (player instanceof EnhancedPlayer)
			return (EnhancedPlayer) player;
		else {
			LogManager.getLogger("EnhancedPlayer")
					.error("Player instance is not assignable to the EnhancedPlayer type, this is a coremod failure.");
			throw new IllegalStateException(
					"Player instance is not assignable to the EnhancedPlayer type, this is a coremod failure.");
		}
	}

	@Override
	public void setCustomDisplayName(Component name) {
		customDisplayName = name;
		server.getPlayerList().broadcastAll(
				new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, this));
	}

	@Override
	public void setCustomDisplayName(String name) {
		setCustomDisplayName(new TextComponent(name));
	}

	@Override
	public void setCustomTabListDisplayName(Component name) {
		customTabListDisplayName = name;
		server.getPlayerList().broadcastAll(
				new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_DISPLAY_NAME, this));
	}

	@Override
	public void setCustomTabListDisplayName(String name) {
		setCustomTabListDisplayName(new TextComponent(name));
	}

	@Override
	public void removeCustomTabListDisplayName() {
		customTabListDisplayName = null;
	}

	@Override
	public void removeCustomDisplayName() {
		customDisplayName = null;
	}

	@Override
	public Component getDisplayName() {
		if (customDisplayName != null)
			return customDisplayName;
		return super.getDisplayName();
	}

	@Override
	public Component getTabListDisplayName() {
		if (customTabListDisplayName != null)
			return customTabListDisplayName;
		return super.getTabListDisplayName();
	}

	@Override
	public void teleportTo(ServerLevel level, double x, double y, double z, float xR, float yR) {
		if (TeleportationEvent.getInstance()
				.dispatch(new TeleportationEventObject(server, this, new Vec3(x, y, z), new Vec2(xR, yR)))
				.getResult() == EventResult.CANCEL)
			return;
		if (LevelTeleportationEvent.getInstance()
				.dispatch(new LevelTeleportationEventObject(server, this, new Vec3(x, y, z), new Vec2(xR, yR), level))
				.getResult() == EventResult.CANCEL)
			return;
		super.teleportTo(level, x, y, z, xR, yR);
	}

	@Override
	public void teleportTo(double x, double y, double z) {
		if (TeleportationEvent.getInstance()
				.dispatch(new TeleportationEventObject(server, this, new Vec3(x, y, z), new Vec2(getXRot(), getYRot())))
				.getResult() == EventResult.CANCEL)
			return;
		if (LevelTeleportationEvent.getInstance().dispatch(new LevelTeleportationEventObject(server, this,
				new Vec3(x, y, z), new Vec2(getXRot(), getYRot()), getLevel())).getResult() == EventResult.CANCEL)
			return;
		super.teleportTo(x, y, z);
	}

	@Override
	public Entity changeDimension(ServerLevel level) {
		if (LevelSwitchEvent.getInstance().dispatch(new LevelSwitchEventObject(server, this, level))
				.getResult() == EventResult.CANCEL)
			return this;
		return super.changeDimension(level);
	}

	@Override
	public void setLevel(ServerLevel level) {
		if (LevelSwitchEvent.getInstance().dispatch(new LevelSwitchEventObject(server, this, level))
				.getResult() == EventResult.CANCEL)
			return;
		super.setLevel(level);
	}

	@Override
	public ServerPlayer toGameType() {
		return (ServerPlayer) this;
	}

	@Override
	public void showTitles(MinecraftTitle... titles) {
		for (MinecraftTitle title : titles) {
			TitleEventObject event = new TitleEventObject(title, getServer(), this);
			EventResult result = TitleEvent.getInstance().dispatch(event).getResult();
			if (result == EventResult.CANCEL)
				return;

			for (TitleComponent comp : title.getComponents()) {
				Function<Component, Packet<?>> packet = null;
				switch (comp.getType()) {
				case TITLE:
					packet = ClientboundSetTitleTextPacket::new;
					break;
				case SUBTITLE:
					packet = ClientboundSetSubtitleTextPacket::new;
					break;
				case ACTIONBAR:
					packet = ClientboundSetActionBarTextPacket::new;
					break;
				}
				if (packet != null) {
					connection.send(packet.apply(comp.getMessage()));
				}
			}
			if (title.getIntervalConfiguration() != null) {
				connection.send(
						new ClientboundSetTitlesAnimationPacket(title.getIntervalConfiguration().getFadeInDuration(),
								title.getIntervalConfiguration().getActiveDuration(),
								title.getIntervalConfiguration().getFadeOutDuration()));
			}
		}
	}

	@Override
	public void clearTitles() {
		connection.send(new ClientboundClearTitlesPacket(false));
	}

	@Override
	public void resetTitles() {
		connection.send(new ClientboundClearTitlesPacket(true));
	}

}
