Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Increased the protocol version to 2.
- Added 71 new rooms for Catacombs.
- Skulls, Bones, and Rotten Flesh are no longer archeology loot in Catacombs.
- Added an advancement for entering Catacombs.
- Improved the loot tables for suspicious blocks in all of Trailier Tales' structures, providing more insight into the past.
- Trailier Tales' Armor Trims are now easier to find in Ruins structures.
- Modifications to Loot Tables for the replacement of Pottery Sherds no longer replaces entire Loot Tables, but only replaces the specified Pottery Sherd items.
    - This means Trailier Tales will now be compatible with datapacks and other mods that modify archaeology loot tables.
- Ominous Bottles can now be found in Catacombs.
- Mobs spawned from Coffins now drop twice as much XP if killed by a player with Siege Omen.
- Added the Block of Ectoplasm.
  - While inside this block, gravity is weaker.
  - Apparitions cannot move through these blocks.
  - Grants the `One Small Step` advancement when moving inside the block.
- Added plenty of new large pieces to all Ruins types.
- Revamped how Ruins structure pieces are loaded, chosen, and offset during worldgen, being much easier and more automatic.
  - As a side effect of this, all ruins pieces now have an equal chance of generating. Prior to this revamp, groups of pieces with specific offsets had a certain chance of being selected.
- Generic and Jungle ruins now generate with one more piece on average.
- Added new ruins for snowy biomes.
- Added the Embrace Armor Trim.
- Added the Aurora Pottery Sherd.
- Added the Enclosure Pottery Sherd.
- Added the Frost Pottery Sherd.
- Added the Hare Pottery Sherd.
- Added the Stasis Music Disc.
- Banner Boats no longer offer a speed boost.
  - This functionality has been given to Penguins in Wilder Wild instead.
- All of Trailier Tales' block sound type overrides now rely on tags.
  - These can be found in the `/sound` folder for Trailier Tales' block tags.
- Updated the Undead Armor Trim Smithing Template texture.
- Updated the Undead Armor Trim's leggings texture.
- Added compat for Enchantment Descriptions, thanks to Lufurrius! ([#12](https://github.com/FrozenBlock/TrailierTales/pull/12))
- Added Spanish translations, thanks to Kokoroto!
- Added Ukranian translations, thanks to unroman! ([#8](https://github.com/FrozenBlock/TrailierTales/pull/8))
- Updated Polish translations slightly, thanks to Eggmanplant! ([#4](https://github.com/FrozenBlock/TrailierTales/pull/4))

#### Bug Fixes
- Fixed a massive issue facing Fabric 1.21.4.
- Fixed a critical issue with mobs suddenly vanishing.
- Fixed a vanilla bug where Powered Rails, Activator Rails, and Detector Rails wouldn't rotate properly in structure generation.
- Coffins have been removed from the Creative Inventory's `Functional Blocks` tab, and added to the `Spawn Eggs` tab.
- Fixed Coffins sometimes not saving SpawnData that was set via Spawn Egg.
- Fixed Coffins using the default spawning config while set to `aggressive.`
- Fixed the optional accessibility particles for suspicious blocks not working as intended.
- Cracked and Chiseled Purpur Blocks can now be broken faster with a pickaxe. ([#10](https://github.com/FrozenBlock/TrailierTales/issues/10))
- Fixed an issue where modded boats that aren't implemented the same way as vanilla cause a crash. ([#9](https://github.com/FrozenBlock/TrailierTales/issues/9))
- Fixed the Polished Calcite Wall not being craftable with a Stonecutter. ([#11](https://github.com/FrozenBlock/TrailierTales/issues/11))
- Fixed Choral End Stone Bricks being unobtainable via Stonecutter.
- Fixed some recipe unlocks not working.
- Fixed Cut Sandstone Stairs and Cut Red Sandstone Stairs having incorrect texture mapping.
- Manedrops can no longer be bonemealed to obtain more Manedrops.
- Removed the leftover `TRAILIER TALES` subtitle from the main menu.
- Removed many unused assets.

### Splash Texts
- Added "Light like the moon!"
- Added "Dropping twice in the siege!"
- Added "Taller and handsome! ...er."
- Added "Dressed in a nice black suit!"
- Added "The money pit!"
- Added "Mysterious fragments of human bone!"
- Added "A rat? Located at a depth of 70 feet underground?"
- Added "A stone crypt? Located at a depth of 406 feet underground?"
- Added "Contains hidden treasure!"
- Added "Also try Braver Bundles!"
- Added "Sherds count in large amounts!"
- Added "Eerie ghostly fog!"
