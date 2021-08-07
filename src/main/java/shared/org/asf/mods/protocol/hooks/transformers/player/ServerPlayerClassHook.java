package org.asf.mods.protocol.hooks.transformers.player;

import java.security.ProtectionDomain;

import org.asf.cyan.fluid.Fluid;
import org.asf.cyan.fluid.api.ClassLoadHook;
import org.asf.cyan.fluid.bytecode.FluidClassPool;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class ServerPlayerClassHook extends ClassLoadHook {

	private static final String PerverPlayerDeobf = "net.minecraft.server.level.ServerPlayer";
	private static final String EnhancedPlayer = " modkit.enhanced.player.EnhancedPlayer";
	
	private static String ServerPlayer;

	@Override
	public String targetPath() {
		return "@ANY";
	}

	@Override
	public void build() {
		ServerPlayer = Fluid.mapClass(PerverPlayerDeobf);
	}

	@Override
	public void apply(ClassNode cc, FluidClassPool cp, ClassLoader loader, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws ClassNotFoundException {
		boolean edited = false;
		if (cc.superName != null && !cc.name.equals(EnhancedPlayer.replace(".", "/"))
				&& cc.superName.equals(ServerPlayer.replace(".", "."))) {
			cc.superName = EnhancedPlayer.replace(".", "/");
			edited = true;
		}
		if (cc.methods != null) {
			for (MethodNode mth : cc.methods) {
				for (AbstractInsnNode nd : mth.instructions) {
					if (nd instanceof TypeInsnNode) {
						TypeInsnNode typeNode = (TypeInsnNode) nd;
						if (typeNode.desc.equals(ServerPlayer.replace(".", "/"))) {
							typeNode.desc = EnhancedPlayer.replace(".", "/");
							edited = true;
							if (typeNode.getOpcode() == Opcodes.NEW) {
								AbstractInsnNode nd2 = nd.getNext();
								while (nd2 != null) {
									if (nd2 instanceof MethodInsnNode && nd2.getOpcode() == Opcodes.INVOKESPECIAL
											&& ((MethodInsnNode) nd2).owner.equals(ServerPlayer.replace(".", "/"))) {
										MethodInsnNode ctor = (MethodInsnNode) nd2;
										ctor.owner = EnhancedPlayer.replace(".", "/");
									}

									nd2 = nd2.getNext();
								}
							}
						}
					}
				}
			}
		}
		if (edited) {
			ClassWriter writer = new ClassWriter(0);
			cc.accept(writer);
			cp.rewriteClass(cc.name, writer.toByteArray());
		}
	}

}
