# ProtocolHooks
A work-in-progress library coremod for Cyan.
This mod binds the server protocol with regular mods.

# Building from source
This project is build using gradle, but it supplies the wrapper so you dont need to install it.
What you do need is Java JDK, if you have it installed, proceed with the following commands depending on your operating system.

### Supported versions
ProtocolHooks supports both 1.16 and 1.17.
Adding the `-PoverrideGameVersion=<version>` argument to the build command will select the version.

### Building commands
On Linux, you need to run the following commands:
```bash
chmod +x gradlew
./gradlew build
```

On Windows, you need to run only the following:
```batch
.\gradlew build
```

### Installing the mod
<b>Main installation:</b>

1. Find the `build/ccmf` folder
2. Copy the latest `ccmf` file
3. Find your Cyan installation
4. Navigate to `.cyan-data/coremods`
5. Paste the mod file


<b>After installing the mod file, you will need to install its trust file in order to run it:</b>

1. Find the `build/ctcs` folder
2. Enter the latest directory
3. Copy the `ctc` file
4. Find your Cyan installation
5. Create and/or enter the `.cyan-data/trust` directory
6. Paste the `ctc` file


# Setting up the development environment
### Developing the ProtocolHooks mod
On Linux, you need to run the following commands to prepare the debug environment:
```bash
chmod +x gradlew
./gradlew creatEclipse
```

On Windows, you need to run only the following:
```batch
.\gradlew creatEclipse
```

This process will take a lot of time if run for the first time.
After running the commands, you can import this project in the Eclipse IDE.

<b>TIP:</b> you can add `-PoverrideGameVersion=<version>` to select a game version.

### Developing mods with the ProtocolHooks coremods
<b>First, open your mod's `build.gradle` file and edit it as following:</b>
```groovy
// File: build.gradle
// ...

// ProtocolHooks Version
def protocolHooksVersion = "1.0"


// Add ProtocolHooks to the debug run manifest
// ...
task runManifest() {
    // ...

    def conf = modfileManifest {
        // ...

        dependency "protocol:hooks", protocolHooksVersion + "-" + gameVersion

        // ...
    }

    // ...
}
// ...


// Download ProtocolHooks from maven into the run files
// ...
import java.nio.file.Files
project.afterEvaluate {
    createServerLaunch {
        // ...

        // Download ProtocolHooks into the run directory
        File protocolHooksCCMF = new File(workingDir, ".cyan-data/coremods/protocolhooks.ccmf")

        String urlBase = "https://aerialworks.ddns.net/maven/org/asf/mods/ProtocolHooks/" + protocolHooksVersion + "-" + gameVersion + "/ProtocolHooks-" + protocolHooksVersion + "-" + gameVersion + ".ccmf"
        String hash = ""
        String hashRemote = null

        if (protocolHooksCCMF.exists()) {
            hash = sha1HEX(Files.readAllBytes(protocolHooksCCMF.toPath()))
        }

        try {
            URL hashUrl = new URL(urlBase + ".sha1")
            InputStream strmH = hashUrl.openStream()
            hashRemote = new String(strmH.readAllBytes())
            strmH.close()
        } catch (IOException e) {        
        }

        if (hashRemote != null && !hashRemote.equals(hash)) {
            URL prHooksMvn = new URL(urlBase)
            InputStream strm = prHooksMvn.openStream()
            if (!protocolHooksCCMF.getParentFile().exists())
                protocolHooksCCMF.getParentFile().mkdirs()
            FileOutputStream strmOut = new FileOutputStream(protocolHooksCCMF)
            strm.transferTo(strmOut)
            strmOut.close()
            strm.close()
        }

        // ...
    }

    createClientLaunch {
        // ...

        // Download ProtocolHooks into the run directory
        File protocolHooksCCMF = new File(workingDir, ".cyan-data/coremods/protocolhooks.ccmf")

        String urlBase = "https://aerialworks.ddns.net/maven/org/asf/mods/ProtocolHooks/" + protocolHooksVersion + "-" + gameVersion + "/ProtocolHooks-" + protocolHooksVersion + "-" + gameVersion + ".ccmf"
        String hash = ""
        String hashRemote = null

        if (protocolHooksCCMF.exists()) {
            hash = sha1HEX(Files.readAllBytes(protocolHooksCCMF.toPath()))
        }

        try {
            URL hashUrl = new URL(urlBase + ".sha1")
            InputStream strmH = hashUrl.openStream()
            hashRemote = new String(strmH.readAllBytes())
            strmH.close()
        } catch (IOException e) {        
        }

        if (hashRemote != null && !hashRemote.equals(hash)) {
            URL prHooksMvn = new URL(urlBase)
            InputStream strm = prHooksMvn.openStream()
            if (!protocolHooksCCMF.getParentFile().exists())
                protocolHooksCCMF.getParentFile().mkdirs()
            FileOutputStream strmOut = new FileOutputStream(protocolHooksCCMF)
            strm.transferTo(strmOut)
            strmOut.close()
            strm.close()
        }

        // ...
    }
}
// ...


// Hash method
// ...
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

String sha1HEX(byte[] array) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-1");
    byte[] sha = digest.digest(array);
    StringBuilder result = new StringBuilder();
    for (byte aByte : sha) {
        result.append(String.format("%02x", aByte));
    }
    return result.toString();
}
// ...


// Add ProtocolHooks to the dependencies block
// ...
dependencies {
    // ...
    
    //
    // ProtocolHooks Dependency'
    //
    // Mod.byId() streams the '/<modgroup>/<modid>/mod.artifacts.deps' file from a remote trust server
    // Cyan's Cornflower plugin adds the dependencies noted in that file to gradle.
    //
    // In this case, Cornflower streams from the following URL:
    // https://aerialworks.ddns.net/cyan/trust/download/protocol/hooks/mod.artifacts.deps
    //
    // As a mod developer, you can set this up for your own mods by writing an identical ccfg file.
    // After writing your dependencies, add a 'group' statement, something like: 'group> org.example'
    // Also add a 'modid' statement such as: 'modid> examplecoremod'
    //
    // You can upload the document to the ASF trust server by using the following command:
    // curl -X POST --data-binary @request.ccfg https://aerialworks.ddns.net/cyan/trust/set-mod-depfile -u "insert-moddev-username-here"
    // (this command requires a moddev account, see the cyan coremodkit for more information on how to configure mod trust)
    //
    //
    // Protocol hooks dependency one-liner:
    implementation Mod.byId("protocol:hooks", protocolHooksVersion + "-" + gameVersion)

    // ...
}
// ...


// Add ProtocolHooks to the CMF manifest
// ...
cmf {
    manifest {
        modfileManifest {
            // ...

            dependency "protocol:hooks", protocolHooksVersion + "-" + gameVersion

            // ...
        }
    }
}
// ...
```

<b>After that, re-generate the eclipse files:</b>

On Linux, you need to run the following commands to prepare the debug environment:
```bash
chmod +x gradlew
./gradlew creatEclipse
```

On Windows, you need to run only the following:
```batch
.\gradlew creatEclipse
```

<br/>
<b>Code Documentation:</b><br/>

- [Visit Enhanced ModKit JavaDoc on the AerialWorks website](https://aerialworks.ddns.net/javadoc/ProtocolHooks)
- [Visit Cyan ModKit JavaDoc on the AerialWorks website](https://aerialworks.ddns.net/javadoc/Cyan/ModKit)
