package net.frozenblock.trailiertales.worldgen.impl.suspicious_handler;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing Xfrtrex data
 * <p>
 * Other than that I have 0 clue
 */
public class SuspiciousData {
	public List<Pair> suspiciousData = new ArrayList<>();

	@NotNull
	public static SuspiciousData getSuspiciousData(@NotNull ServerLevel level) {
		return ((SuspiciousDataInterface)level).trailierTales$getSuspiciousData();
	}

	@NotNull
	public SavedData.Factory<SuspiciousDataStorage> createData() {
		return new SavedData.Factory<>(
			() -> new SuspiciousDataStorage(this),
			(tag, provider) -> SuspiciousDataStorage.load(tag, this),
			DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
		);
	}

	public static void addLootTableToBrushableBlock(@NotNull ServerLevel level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushableBlockEntity) {
			SuspiciousData suspiciousData = SuspiciousData.getSuspiciousData(level);
			SuspiciousData.Pair toRemove = null;
			for (SuspiciousData.Pair data : suspiciousData.suspiciousData) {
				if (data.pos.equals(pos)) {
					toRemove = data;
					brushableBlockEntity.setLootTable(
						ResourceKey.create(Registries.LOOT_TABLE, data.lootTable),
						pos.asLong()
					);
				}
			}

			if (toRemove != null) {
				suspiciousData.suspiciousData.remove(toRemove);
			}
		}
	}

	public record Pair(BlockPos pos, ResourceLocation lootTable) {
			public static final Codec<Pair> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				BlockPos.CODEC.fieldOf("Pos").forGetter(sus -> sus.pos),
				ResourceLocation.CODEC.fieldOf("LootTable").forGetter(sus -> sus.lootTable)
			).apply(instance, Pair::new));

	}
}
