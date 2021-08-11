package org.asf.mods.protocol.hooks.events;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.asf.cyan.api.common.CyanComponent;
import org.asf.cyan.minecraft.toolkits.mtk.MinecraftInstallationToolkit;
import org.asf.cyan.mods.events.AttachEvent;
import org.asf.cyan.mods.events.IEventListenerContainer;
import org.asf.mods.protocol.hooks.config.IdForwardConfig;

public class CommonEvents extends CyanComponent implements IEventListenerContainer {

	public static IdForwardConfig idForwardConfiguration;

	private static PublicKey pubKey;

	public static boolean checkSignature(byte[] payload, byte[] signature) {
		try {
			Signature sig = Signature.getInstance("Sha256WithRSA");
			sig.initVerify(pubKey);
			sig.update(payload);
			return sig.verify(signature);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			return false;
		}
	}

	private byte[] pemDecode(String PEM) {
		String base64 = PEM;

		while (base64.startsWith("-")) {
			base64 = base64.substring(1);
		}
		while (!base64.startsWith("-")) {
			base64 = base64.substring(1);
		}
		while (base64.startsWith("-")) {
			base64 = base64.substring(1);
		}

		base64 = base64.replace("\n", "");
		while (base64.endsWith("-"))
			base64 = base64.substring(0, base64.length() - 1);
		while (!base64.endsWith("-"))
			base64 = base64.substring(0, base64.length() - 1);
		while (base64.endsWith("-"))
			base64 = base64.substring(0, base64.length() - 1);

		return Base64.getDecoder().decode(base64);
	}

	@AttachEvent("mods.preinit")
	public void preInit() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		idForwardConfiguration = new IdForwardConfig(MinecraftInstallationToolkit.getMinecraftDirectory());
		idForwardConfiguration.readAll();

		if (!idForwardConfiguration.server.isEmpty()) {
			info("Enabled IDForward, using BungeeCord mirror authentication server at " + idForwardConfiguration.server
					+ ":" + idForwardConfiguration.port);

			KeyFactory fac = KeyFactory.getInstance("RSA");
			pubKey = fac.generatePublic(new X509EncodedKeySpec(pemDecode(idForwardConfiguration.publicKey)));
		}
	}

}
