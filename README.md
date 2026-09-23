# Amputated Ragdolls — GWO / NeoForge 1.21.1

Port of the supplied Amputated Ragdolls 1.2.0 (Forge 1.20.1) compatibility mod for Minecraft 1.21.1 and NeoForge 21.1.251. This variant retains the amputation-to-ragdoll bridge and supports **Guns Workshop Origins (GWO)** gunshots only. TaCZ, BetterBloodOverlay and general overlay capture are not included.

## Install

Place the built `amputatedragdolls-1.2.0-neoforge-1.21.1.jar` and these **NeoForge 1.21.1** mods in the game's `mods` folder:

- Mob Amputation (Reborn) 1.21.1-1.0.0 or a compatible newer version
- Ragdollified 1.1.0-RELEASE or a compatible newer version
- Guns Workshop Origins (GWO) Beta1.0-Fix-0.4 (mod ID `gwo`)

GWO needs a separate content pack for usable guns. The server and clients should use matching mod versions. Do not install the original Forge 1.20.1 `amputatedragdolls-1.2.0.jar` alongside this port.

## Behavior

GWO projectile hit classification is captured at the bullet's `isHeadshot` decision. On positive server-side bullet damage, a headshot can detach the head; a non-headshot can detach a random arm or leg. Mob Amputation's supported-entity and enabled-limb checks still apply. Default chances are 75% for headshots and 20% for non-headshots. Edit `config/amputatedragdolls-common.toml` after first launch to change them. A detached limb is carried over to the Ragdollified corpse.

## Build from source

Requires JDK 21. Place these exact upstream JARs in `libs/`, then run `gradlew.bat build` on Windows or `./gradlew build` elsewhere:

- `ragdollified-1.21.1-1.1.0.jar`
- `mobamputation-1.21.1-1.0.0.jar`
- `GWO-Beta1.0-Fix-0.4.jar`

The upstream JARs are compile-time references only and are not bundled in the output. The source archive intentionally excludes them. The code was reconstructed from the user-provided JAR, whose mod metadata names YMhmD as author and declares MIT licensing.

Validated by Gradle build and dedicated server startup with all three dependencies. Actual in-game gunfire and client ragdoll rendering have not been exercised; verify those in a test world before relying on the port.
