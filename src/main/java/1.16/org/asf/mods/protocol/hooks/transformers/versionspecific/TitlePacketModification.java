package org.asf.mods.protocol.hooks.transformers.versionspecific;

import org.asf.cyan.fluid.api.FluidTransformer;
import org.asf.cyan.fluid.api.transforming.TargetClass;

import net.minecraft.network.protocol.game.ClientboundSetTitlesPacket;

@FluidTransformer
@TargetClass(target = "net.minecraft.network.protocol.game.ClientboundSetTitlesPacket")
public class TitlePacketModification implements TitlePacketExtender {
	private ClientboundSetTitlesPacket.Type type;

	public ClientboundSetTitlesPacket.Type phGetType() {
		return type;
	}
}
