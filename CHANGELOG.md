Please clear changelog after each release.
Thank you!
Put the changelog BELOW the dashes. ANYTHING ABOVE IS IGNORED
hi
-----------------
- Added 42 new rooms for Catacombs.
- Skulls, Bones, and Rotten Flesh are no longer archeology loot in Catacombs.
- Added the Block of Ectoplasm.
  - While inside this block, gravity is weaker.
  - Apparitions cannot move through these blocks.
- Added plenty of new large pieces to all Ruins types.
- Added new ruins for snowy biomes.
- Added new "Small Trail Ruins," generating rarely in biomes with vanilla's Trail Ruins.
- Added the Embrace Armor Trim.
- Added the Frost Pottery Sherd.
- Added the Hare Pottery Sherd.
- Added the Stasis Music Disc.
- Updated the Undead Armor Trim Smithing Template texture.
- Updated the Undead Armor Trim's leggings texture.
- Revamped how Ruins structure pieces are loaded, chosen, and offset during worldgen, being much easier and more automatic.
  - As a side effect of this, all ruins pieces now have an equal chance of generating. Prior to this revamp, groups of pieces with specific offsets had a certain chance of being selected.
- Generic and Jungle ruins now generate with one more piece on average.
- Added compat for Enchantment Descriptions, thanks to Lufurrius! ([#12](https://github.com/FrozenBlock/TrailierTales/pull/12))
- Added Spanish translations, thanks to Kokoroto!
- Added Ukranian translations, thanks to unroman! ([#8](https://github.com/FrozenBlock/TrailierTales/pull/8))
- Updated Polish translations slightly, thanks to Eggmanplant! ([#4](https://github.com/FrozenBlock/TrailierTales/pull/4))

#### Bugfixes
- Fixed a critical issue with mobs suddenly vanishing.
- Fixed a vanilla bug where Powered Rails, Activator Rails, and Detector Rails wouldn't rotate properly in structure generation.
- Cracked and Chiseled Purpur Blocks can now be broken faster with a pickaxe. ([#10](https://github.com/FrozenBlock/TrailierTales/issues/10))
- Fixed an issue where modded boats that aren't implemented the same way as vanilla cause a crash. ([#9](https://github.com/FrozenBlock/TrailierTales/issues/9))
- Fixed the Polished Calcite Wall not being craftable with a Stonecutter. ([#11](https://github.com/FrozenBlock/TrailierTales/issues/11))
- Fixed some recipe unlocks not working.
- Removed the leftover `TRAILIER TALES` subtitle from the main menu.
- Removed many unused assets.
