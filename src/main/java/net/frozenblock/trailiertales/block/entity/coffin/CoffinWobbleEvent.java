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

package net.frozenblock.trailiertales.block.entity.coffin;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import net.frozenblock.lib.FrozenLibConstants;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public enum CoffinWobbleEvent {
	EJECT_LOOT(1F, true, (coffin, state) -> !coffin.isEmpty() && TTBlockConfig.get().coffin.wobble_loot),
	ACTIVATE(0.2F, false, (coffin, state) -> TTBlockConfig.get().coffin.wobble_activate),
	POTION(0.1F, true, (coffin, state) -> TTBlockConfig.get().coffin.wobble_potion),
	EXPERIENCE_BOTTLE(0.1F, true, (coffin, state) -> TTBlockConfig.get().coffin.wobble_experience_bottle);

	private static final WeightedList<MobEffectInstance> MOB_EFFECTS = WeightedList.<MobEffectInstance>builder()
		.add(new MobEffectInstance(MobEffects.MINING_FATIGUE, 120 * 20), 2)
		.add(new MobEffectInstance(MobEffects.POISON, 30 * 20), 1)
		.add(new MobEffectInstance(TTMobEffects.TRANSFIGURING, 60 * 20), 2)
		.build();
	private static final Vec3 DEFAULT_SHOOT_ANGLE = new Vec3(0D, 1D, 0D);
	private static final float DEFAULT_SHOOT_SPEED = 0.45F;
	private static final float PLAYER_SHOOT_SPEED = 1F;

	private final float chance;
	private final boolean playsLidAnim;
	private final BiFunction<CoffinBlockEntity, BlockState, Boolean> extraConditions;

	CoffinWobbleEvent(float chance, boolean playsLidAnim, BiFunction<CoffinBlockEntity, BlockState, Boolean> extraConditions) {
		this.chance = chance;
		this.playsLidAnim = playsLidAnim;
		this.extraConditions = extraConditions;
	}

	public float getChance() {
		return this.chance;
	}

	public boolean playsLidAnim() {
		return this.playsLidAnim;
	}

	public boolean checkExtraConditions(CoffinBlockEntity coffin, BlockState state) {
		return this.extraConditions.apply(coffin, state);
	}

	public static void onWobble(ServerLevel level, BlockPos pos, BlockState state, CoffinBlockEntity coffin, RandomSource random) {
		final CoffinWobbleEvent event = Util.getRandom(CoffinWobbleEvent.values(), random);

		if (random.nextFloat() <= event.getChance() && event.checkExtraConditions(coffin, state)) {
			final Vec3 center = CoffinBlock.getCenter(state, pos);
			switch (event) {
				case EJECT_LOOT:
					ejectRandomItem(level, center, coffin);
					break;
				case ACTIVATE:
					coffin.getCoffinSpawner().immediatelyActivate(level, pos);
					break;
				case POTION:
					ejectProjectile(level, center, createItemStackForMobEffect(Items.LINGERING_POTION, MOB_EFFECTS.getRandomOrThrow(random)), coffin);
					break;
				case EXPERIENCE_BOTTLE:
					ejectProjectile(level, center, new ItemStack(Items.EXPERIENCE_BOTTLE), coffin);
					break;
				default: throw new IllegalStateException("Unexpected value: " + event.name());
			}

			if (event.playsLidAnim()) {
				coffin.coffinWobbleLidAnimTicks = 4;
				final Direction orientation = CoffinBlock.getCoffinOrientation(level, pos);
				if (orientation != null) {
					CoffinBlock.spawnParticlesFrom(
						level,
						ParticleTypes.DUST_PLUME,
						level.random.nextInt(8, 14),
						0.02D,
						orientation,
						pos,
						0.375D
					);
				}
			}

			coffin.markUpdated();
		} else if (random.nextBoolean()) {
			onWobble(level, pos, state, coffin, random);
		}
	}

	private static void ejectRandomItem(ServerLevel level, Vec3 centerPos, CoffinBlockEntity coffin) {
		coffin.unpackLootTable(null);
		for (ItemStack coffinStack : Util.shuffledCopy(coffin.getItems().toArray(new ItemStack[0]), level.random)) {
			if (coffinStack.isEmpty()) continue;

			final ItemStack splitStack = coffinStack.split(1);
			final ItemEntity itemEntity = new ItemEntity(
				level,
				centerPos.x,centerPos.y, centerPos.z,
				splitStack
			);
			level.addFreshEntity(itemEntity);
			return;
		}
	}

	public static ItemStack createItemStackForMobEffect(Item item, MobEffectInstance effect) {
		final ItemStack stack = new ItemStack(item);
		stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), List.of(effect), Optional.empty()));
		return stack;
	}

	private static void ejectProjectile(ServerLevel level, Vec3 centerPos, ItemStack stack, CoffinBlockEntity coffin) {
		if (!(stack.getItem() instanceof ProjectileItem projectileItem)) {
			if (FrozenLibConstants.UNSTABLE_LOGGING) throw new IllegalStateException("Projectile item stack is not an instance of ProjectileItem!");
			return;
		}

		final Projectile projectile = projectileItem.asProjectile(level, centerPos, stack, Direction.UP);
		Vec3 shootAngle = DEFAULT_SHOOT_ANGLE;
		float shootSpeed = DEFAULT_SHOOT_SPEED;

		final Optional<Player> closestPlayer = coffin.getCoffinSpawner().getData().getClosestPotentialPlayer(level, centerPos);
		if (closestPlayer.isPresent()) {
			shootAngle = (closestPlayer.get().getEyePosition().add(0D, 0.1D, 0D)).subtract(centerPos);
			shootSpeed = PLAYER_SHOOT_SPEED;
		}

		projectileItem.shoot(
			projectile,
			shootAngle.x,
			Math.max(0.1F, shootAngle.y),
			shootAngle.z,
			shootSpeed,
			0F
		);
		level.addFreshEntity(projectile);
	}
}
