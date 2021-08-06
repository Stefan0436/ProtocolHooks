package org.asf.mods.protocol.hooks.transformers.player;

import java.security.ProtectionDomain;

import org.asf.cyan.fluid.api.ClassLoadHook;
import org.asf.cyan.fluid.bytecode.FluidClassPool;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import modkit.enhanced.player.EnhancedPlayer;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayerClassHook extends ClassLoadHook {

	@Override
	public String targetPath() {
		return "@ANY";
	}

	@Override
	public void build() {
	}

	@Override
	public void apply(ClassNode cc, FluidClassPool cp, ClassLoader loader, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws ClassNotFoundException {
		boolean edited = false;
		if (cc.superName != null && !cc.name.equals(EnhancedPlayer.class.getTypeName().replace(".", "/"))
				&& cc.superName.equals(ServerPlayer.class.getTypeName().replace(".", "."))) {
			cc.superName = EnhancedPlayer.class.getTypeName().replace(".", "/");
			edited = true;
		}
		if (cc.methods != null) {
			for (MethodNode mth : cc.methods) {
				for (AbstractInsnNode nd : mth.instructions) {
					if (nd instanceof TypeInsnNode) {
						TypeInsnNode typeNode = (TypeInsnNode) nd;
						if (typeNode.desc.equals(ServerPlayer.class.getTypeName().replace(".", "/"))) {
							typeNode.desc = EnhancedPlayer.class.getTypeName().replace(".", "/");
							edited = true;
							if (typeNode.getOpcode() == Opcodes.NEW) {
								AbstractInsnNode nd2 = nd.getNext();
								while (nd2 != null) {
									if (nd2 instanceof MethodInsnNode && nd2.getOpcode() == Opcodes.INVOKESPECIAL
											&& ((MethodInsnNode) nd2).owner
													.equals(ServerPlayer.class.getTypeName().replace(".", "/"))) {
										MethodInsnNode ctor = (MethodInsnNode) nd2;
										ctor.owner = EnhancedPlayer.class.getTypeName().replace(".", "/");
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
