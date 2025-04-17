/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.worldgen.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.trailiertales.registry.TTRuleBlockEntityModifiers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifierType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.Util;

public class CoffinProcessor implements RuleBlockEntityModifier {
	public static final MapCodec<CoffinProcessor> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			BuiltInRegistries.ENTITY_TYPE.byNameCodec().listOf().fieldOf("entities").forGetter(modifier -> modifier.entities),
			Codec.BOOL.fieldOf("within_catacombs").orElse(false).forGetter(modifier -> modifier.withinCatacombs)
			).apply(instance, CoffinProcessor::new)
	);
	private final List<EntityType<?>> entities;
	private final boolean withinCatacombs;

	public CoffinProcessor(boolean withinCatacombs, EntityType<?>... entities) {
		this(List.of(entities), withinCatacombs);
	}

	public CoffinProcessor(List<EntityType<?>> entities, boolean withinCatacombs) {
		this.entities = entities;
		this.withinCatacombs = withinCatacombs;
		if (this.entities.isEmpty()) {
			throw new IllegalArgumentException("CoffinProcessor requires at least one EntityType!");
		}
	}

	@Override
	public CompoundTag apply(@NotNull RandomSource random, @Nullable CompoundTag nbt) {
		CompoundTag compoundTag = nbt == null ? new CompoundTag() : nbt.copy();
		if (compoundTag.contains("uuid")) {
			compoundTag.remove("uuid");
			compoundTag.remove("max_active_light_level");
			compoundTag.putBoolean("within_catacombs", this.withinCatacombs);

			EntityType<?> entityType = this.getRandomEntity(random);
			SpawnData spawnData = createSpawnData(entityType);
			SpawnData.CODEC
				.encodeStart(NbtOps.INSTANCE, spawnData)
				.resultOrPartial()
				.ifPresent(tag -> compoundTag.put("spawn_data", tag));
		}

		return compoundTag;
	}

	public EntityType<?> getRandomEntity(@NotNull RandomSource random) {
		return Util.getRandom(this.entities, random);
	}

	private static @NotNull SpawnData createSpawnData(EntityType<?> type) {
		SpawnData spawnData = new SpawnData();
		spawnData.getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());
		return spawnData;
	}

	@Override
	public @NotNull RuleBlockEntityModifierType<?> getType() {
		return TTRuleBlockEntityModifiers.COFFIN_PROCESSOR;
	}
}
