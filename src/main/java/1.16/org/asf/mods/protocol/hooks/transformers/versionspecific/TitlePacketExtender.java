package org.asf.mods.protocol.hooks.transformers.versionspecific;

import net.minecraft.network.protocol.game.ClientboundSetTitlesPacket;

public interface TitlePacketExtender {
	public ClientboundSetTitlesPacket.Type phGetType();
}
