Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- FrozenLib is no longer embedded in Trailier Tales.
  - It must now be downloaded separately.
- Fixed the Sherd Duplication recipe sometimes failing to produce an output.
  - The recipe is still disabled by default in the config, remember to enable it if you want it!
- The amount of mobs required to be killed per-level for Coffins has been decreased:
  - Default: 4 -> 3
  - Irritated: 6 -> 3
  - Aggressive: 10 -> 5
- The time between Apparition spawns per-level for Coffins has been increased:
  - Default: 80 seconds -> 100 seconds
  - Irritated: 60 seconds -> 80 seconds
  - Aggressive: 50 seconds -> 60 seconds
- Ominous Coffins now only spawn 1 Apparition at a time, instead of 2.
- Apparitions now drop Ectoplasm upon death more commonly, and is now consistent with other vanilla mobs.
- Apparitions now must be killed my a player to drop Ectoplasm.
- The cooldown before an Apparition can pick up another item is now 25 seconds, instead of 15.
- Added a config option to toggle whether Apparitions can pick up items.
- Added a config option to toggle whether Apparitions can catch projectiles.
- Added a config option to toggle whether Apparitions can "hypnotize" mobs.
- Added a config option to toggle whether Apparitions can give players the Haunt effect.
- Added four config options to toggle the features of the Haunt effect.
    - Fog
    - Lightmap Dimming
    - Lower Sound Attenuation Distance
    - Obscured Stats In HUD
