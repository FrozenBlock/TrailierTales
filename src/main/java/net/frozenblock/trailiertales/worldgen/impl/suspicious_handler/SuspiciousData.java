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

public class SuspiciousData {
	public List<Pair> oldData = new ArrayList<>();
	public List<Pair> suspiciousData = new ArrayList<>();
	private final ServerLevel level;

	@SuppressWarnings("unchecked")
	public SuspiciousData(@NotNull ServerLevel level) {
		this.level = level;
	}

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

	public void swapLists() {
		this.oldData.addAll(this.suspiciousData);
		this.suspiciousData = new ArrayList<>();
	}

	public void tick(@NotNull ServerLevel level) {
		List<Pair> toRemove = new ArrayList<>();
		for (Pair sus : this.oldData) {
			if (level.isLoaded(sus.pos)) {
				if (level.getBlockEntity(sus.pos) instanceof BrushableBlockEntity brushableBlockEntity) {
					brushableBlockEntity.setLootTable(
						ResourceKey.create(Registries.LOOT_TABLE, sus.lootTable),
						level.random.nextLong()
					);
					toRemove.add(sus);
				}
			}
		}

		this.oldData.removeAll(toRemove);
	}

	public static class Pair {
		public static final Codec<Pair> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockPos.CODEC.fieldOf("Pos").forGetter(sus -> sus.pos),
			ResourceLocation.CODEC.fieldOf("LootTable").forGetter(sus -> sus.lootTable)
		).apply(instance, Pair::new));

		public final BlockPos pos;
		public final ResourceLocation lootTable;

		public Pair(BlockPos pos, ResourceLocation lootTable) {
			this.pos = pos;
			this.lootTable = lootTable;
		}
	}
}
