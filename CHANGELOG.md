Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Bumped Trailier Tales' protocol version to 3.
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
- Apparitions now must be killed by a player to drop Ectoplasm.
- The cooldown before an Apparition can pick up another item is now 25 seconds, instead of 15.
- Apparitions are now always visible when using Night Vision on 1.21.2+.
- Added a config option to toggle whether Apparitions can pick up items.
- Added a config option to toggle whether Apparitions can catch projectiles.
- Added a config option to toggle whether Apparitions can "hypnotize" mobs.
- Added a config option to toggle whether Apparitions can give players the Haunt effect.
- Added five config options to toggle the features of the Haunt effect.
  - Nearby Coffin Activation
  - Fog
  - Lightmap Dimming
  - Lower Sound Attenuation Distance
  - Obscured Stats In HUD
- Added two new Sniffer plants!
  - Guzmania
    - A double-tall tropical flower.
  - Lithops
    - A flowerbed-like block, sporting multiple colors of Lithops.
- Finally added the fabled Stone wall after months of community request.
- Added Polished Resin Stairs, Slabs, and Walls.
- Fixed the ordering of Trailier Tales's new Granite, Diorite, and Andesite building blocks in the Creative Inventory in 1.21.2+. ([#25](https://github.com/FrozenBlock/TrailierTales/issues/25))
- Added the Ectoplasm Block to the `Natural Blocks` tab of the Creative Inventory.
- Changed the ordering of Trailier Tales' new suspicious blocks in the Creative Inventory.
- Fixed the ordering of Trailier Tales' new Resin blocks in the Creative Inventory.

### Assets
- Fixed Trailier Tales' crop models floating one pixel above Farmland blocks.
- Updated the Chiseled, Cracked, and Mossy Calcite Bricks textures.
- Updated the Chiseled, Cracked, and Mossy Granite Bricks textures.
- Updated the Cracked, Mossy, and Normal Andesite Bricks textures.
- Updated the Cracked, Mossy, and Normal Diorite Bricks textures.
- Updated the Chiseled Purpur Block texture.
- Updated the Choral End Stone Bricks texture.
- Updated the Ectoplasm item texture.
- Updated the Fausse Vie, Ossuaire, and Stasis Music Disc textures.
- Updated the icon and HUD textures of the Haunt mob effect.
- Updated the icon of the Transfiguring mob effect.
