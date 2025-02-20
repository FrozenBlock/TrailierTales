package net.frozenblock.trailiertales.block.entity.coffin;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import net.frozenblock.lib.FrozenLibConstants;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
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
import org.jetbrains.annotations.NotNull;

public enum CoffinWobbleEvent {
	EJECT_LOOT(1F, true, (coffinBlockEntity, blockState) -> !coffinBlockEntity.isEmpty() && TTBlockConfig.get().coffin.wobble_loot),
	ACTIVATE(0.2F, false, (coffinBlockEntity, blockState) -> TTBlockConfig.get().coffin.wobble_activate),
	POTION(0.1F, true, (coffinBlockEntity, blockState) -> TTBlockConfig.get().coffin.wobble_potion),
	EXPERIENCE_BOTTLE(0.1F, true, (coffinBlockEntity, blockState) -> TTBlockConfig.get().coffin.wobble_experience_bottle);

	private static final SimpleWeightedRandomList<MobEffectInstance> MOB_EFFECTS = SimpleWeightedRandomList.<MobEffectInstance>builder()
		.add(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 120 * 20), 2)
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

	public boolean checkExtraConditions(CoffinBlockEntity coffinBlockEntity, BlockState blockState) {
		return this.extraConditions.apply(coffinBlockEntity, blockState);
	}

	public static void onWobble(ServerLevel level, BlockPos pos, BlockState state, CoffinBlockEntity coffinBlockEntity, RandomSource random) {
		CoffinWobbleEvent event = Util.getRandom(CoffinWobbleEvent.values(), random);
		if (random.nextFloat() <= event.getChance() && event.checkExtraConditions(coffinBlockEntity, state)) {

			Vec3 centerPos = CoffinBlock.getCenter(state, pos);

			switch (event) {
				case EJECT_LOOT:
					ejectRandomItem(level, centerPos, coffinBlockEntity);
					break;
				case ACTIVATE:
					coffinBlockEntity.getCoffinSpawner().immediatelyActivate(level, pos);
					break;
				case POTION:
					ejectProjectile(level, centerPos, createItemStackForMobEffect(Items.LINGERING_POTION, MOB_EFFECTS.getRandomValue(random).orElseThrow()), coffinBlockEntity);
					break;
				case EXPERIENCE_BOTTLE:
					ejectProjectile(level, centerPos, new ItemStack(Items.EXPERIENCE_BOTTLE), coffinBlockEntity);
					break;
				default: throw new IllegalStateException("Unexpected value: " + event.name());
			}

			if (event.playsLidAnim()) {
				coffinBlockEntity.coffinWobbleLidAnimTicks = 4;
				Direction coffinOrientation = CoffinBlock.getCoffinOrientation(level, pos);
				if (coffinOrientation != null) {
					CoffinBlock.spawnParticlesFrom(
						level,
						ParticleTypes.DUST_PLUME,
						level.random.nextInt(8, 14),
						0.02D,
						coffinOrientation,
						pos,
						0.375D
					);
				}
			}

			coffinBlockEntity.markUpdated();
		} else if (random.nextBoolean()) {
			onWobble(level, pos, state, coffinBlockEntity, random);
		}
	}

	private static void ejectRandomItem(@NotNull ServerLevel serverLevel, Vec3 centerPos, @NotNull CoffinBlockEntity coffinBlockEntity) {
		coffinBlockEntity.unpackLootTable(null);
		for (ItemStack coffinStack : Util.shuffledCopy(coffinBlockEntity.getItems().toArray(new ItemStack[0]), serverLevel.random)) {
			if (!coffinStack.isEmpty()) {
				ItemStack splitStack = coffinStack.split(1);
				ItemEntity itemEntity = new ItemEntity(
					serverLevel,
					centerPos.x,
					centerPos.y,
					centerPos.z,
					splitStack
				);
				serverLevel.addFreshEntity(itemEntity);
				return;
			}
		}
	}

	public static @NotNull ItemStack createItemStackForMobEffect(Item item, MobEffectInstance effect) {
		ItemStack itemStack = new ItemStack(item);
		itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), List.of(effect)));
		return itemStack;
	}

	private static void ejectProjectile(@NotNull ServerLevel serverLevel, Vec3 centerPos, @NotNull ItemStack stack, CoffinBlockEntity coffinBlockEntity) {
		if (stack.getItem() instanceof ProjectileItem projectileItem) {
			Projectile projectile = projectileItem.asProjectile(serverLevel, centerPos, stack, Direction.UP);
			Vec3 shootAngle = DEFAULT_SHOOT_ANGLE;
			float shootSpeed = DEFAULT_SHOOT_SPEED;

			Optional<Player> closestPlayer = coffinBlockEntity.getCoffinSpawner().getData().getClosestPotentialPlayer(serverLevel, centerPos);
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
			serverLevel.addFreshEntity(projectile);
			return;
		}
		if (FrozenLibConstants.UNSTABLE_LOGGING) {
			throw new IllegalStateException("Projectile item stack is not an instance of ProjectileItem!");
		}
	}
}
