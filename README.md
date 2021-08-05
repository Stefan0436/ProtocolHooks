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

### Developing mods with the ProtocolHooks coremods (Cornflower 1.0.0.A47 or greater)
<b>First, open your mod's `build.gradle` file and edit it as following:</b>
```groovy
// File: build.gradle
// ...


// ProtocolHooks Version
def protocolHooksVersion = "1.1"


// Add ProtocolHooks to the dependencies block
// ...
dependencies {
    // ...
    
    // Protocol hooks dependency:
    implementation Mod.byId("protocol:hooks", protocolHooksVersion + "-" + gameVersion)

    // ...
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
