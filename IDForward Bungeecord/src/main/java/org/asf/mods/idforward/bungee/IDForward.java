package org.asf.mods.idforward.bungee;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.asf.mods.idforward.bungee.commands.RequestPlayerCommand;
import org.asf.rats.ConnectiveHTTPServer;
import org.asf.rats.ConnectiveServerFactory;

import net.md_5.bungee.api.plugin.Plugin;

public class IDForward extends Plugin {

	private PrivateKey privKey;
	private PublicKey pubKey;

	private ConnectiveHTTPServer server;

	public byte[] sign(byte[] payload) {
		try {
			Signature sig = Signature.getInstance("Sha256WithRSA");
			sig.initSign(privKey);
			sig.update(payload);
			return sig.sign();
		} catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
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

	private String pem(byte[] key, String type) {
		String PEM = "-----BEGIN " + type + " KEY-----";
		String base64 = new String(Base64.getEncoder().encode(key));

		while (true) {
			PEM += "\n";
			boolean done = false;
			for (int i = 0; i < 64; i++) {
				if (base64.isEmpty()) {
					done = true;
					break;
				}
				PEM += base64.substring(0, 1);
				base64 = base64.substring(1);
			}
			if (base64.isEmpty())
				break;
			if (done)
				break;
		}

		PEM += "\n";
		PEM += "-----END " + type + " KEY-----";
		return PEM;
	}

	@Override
	public void onDisable() {
		server.stop();
	}

	@Override
	public void onEnable() {

		getLogger().info("Started ProtocolHooks: IDForward! Loading embedded ConnectiveHTTP server...");
		File data = getDataFolder();
		if (!data.exists())
			data.mkdirs();

		File pubKeyFile = new File(data, "public.pem");
		File privKeyFile = new File(data, "private.pem");
		if (!pubKeyFile.exists() || !privKeyFile.exists()) {
			if (pubKeyFile.exists())
				pubKeyFile.delete();
			if (privKeyFile.exists())
				privKeyFile.delete();

			try {
				KeyPair pair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
				privKey = pair.getPrivate();
				pubKey = pair.getPublic();

				FileAttribute<?> permissions = PosixFilePermissions
						.asFileAttribute(PosixFilePermissions.fromString("rw-------"));
				Files.write(pubKeyFile.toPath(), pem(pubKey.getEncoded(), "PUBLIC").getBytes());
				Files.write(Files.createFile(privKeyFile.toPath(), permissions),
						pem(privKey.getEncoded(), "PRIVATE").getBytes());
			} catch (NoSuchAlgorithmException | IOException e) {
				getLogger().severe(
						"Failed to generate RSA keypair! " + e.getClass().getTypeName() + ": " + e.getMessage());
				e.printStackTrace();
				return;
			}
		} else {
			try {
				KeyFactory fac = KeyFactory.getInstance("RSA");
				this.pubKey = fac.generatePublic(
						new X509EncodedKeySpec(pemDecode(new String(Files.readAllBytes(pubKeyFile.toPath())))));
				this.privKey = fac.generatePrivate(
						new PKCS8EncodedKeySpec(pemDecode(new String(Files.readAllBytes(privKeyFile.toPath())))));
			} catch (InvalidKeySpecException | IOException | NoSuchAlgorithmException e) {
				getLogger().severe("Failed to load RSA keypair! " + e.getClass().getTypeName() + ": " + e.getMessage());
				e.printStackTrace();
				return;
			}
		}

		ServerConfig conf = new ServerConfig(data);
		try {
			conf.readAll();

			ConnectiveServerFactory fac = new ConnectiveServerFactory();
			fac.setOption(ConnectiveServerFactory.OPTION_DISABLE_MODULE_IMPLEMENTATIONS);
			fac.setOption(ConnectiveServerFactory.OPTION_ASSIGN_IP);
			fac.setOption(ConnectiveServerFactory.OPTION_ASSIGN_PORT);
			fac.setIp(InetAddress.getByName(conf.ip));
			fac.setPort(conf.port);

			server = fac.build();
			server.registerProcessor(new RequestPlayerCommand());
			server.start();

			getLogger().info("System ready, listening for authorization requests on port " + conf.port + "!");
		} catch (IOException | InvocationTargetException e) {
			getLogger().severe("Failed to start ConnectiveHTTP! " + e.getClass().getTypeName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

}
