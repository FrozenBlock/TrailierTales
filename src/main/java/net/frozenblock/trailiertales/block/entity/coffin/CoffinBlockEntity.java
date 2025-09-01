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

import com.mojang.logging.LogUtils;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class CoffinBlockEntity extends RandomizableContainerBlockEntity implements Spawner, CoffinSpawner.StateAccessor {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final float WOBBLE_DURATION = 15F;
	public static final int WOBBLE_COOLDOWN = 10;

	private NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);
	private CoffinSpawner coffinSpawner;

	private float previousOpenProgress;
	private float openProgress;

	public long wobbleStartedAtTick;
	public int coffinWobbleLidAnimTicks;

	public CoffinBlockEntity(BlockPos pos, BlockState state) {
		super(TTBlockEntityTypes.COFFIN, pos, state);
		this.coffinSpawner = new CoffinSpawner(this);
	}

	public float getOpenProgress(float partialTick) {
		return Mth.lerp(partialTick, this.previousOpenProgress, this.openProgress);
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);

		if (this.getBlockState().getValue(TTBlockStateProperties.COFFIN_PART) == CoffinPart.FOOT) {
			this.coffinSpawner = valueInput.read(this.coffinSpawner.mapCodec()).orElse(this.coffinSpawner);

			this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
			if (!this.tryLoadLootTable(valueInput)) {
				ContainerHelper.loadAllItems(valueInput, this.items);
			}
		}

		this.coffinWobbleLidAnimTicks = valueInput.getIntOr("coffin_wobble_lid_anim_ticks", 0);
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);

		if (this.getBlockState().getValue(TTBlockStateProperties.COFFIN_PART) == CoffinPart.FOOT) {
			valueOutput.store(this.coffinSpawner.mapCodec(), this.coffinSpawner);

			ContainerHelper.saveAllItems(valueOutput, this.items);
		}

		valueOutput.putInt("coffin_wobble_lid_anim_ticks", this.coffinWobbleLidAnimTicks);
	}

	@Override
	public boolean canOpen(@NotNull Player player) {
		return false;
	}

	@Override
	protected @NotNull Component getDefaultName() {
		return Component.translatable("container.coffin");
	}

	@Override
	protected @NotNull NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(@NotNull NonNullList<ItemStack> nonNullList) {
		this.items = nonNullList;
	}

	@Override
	protected @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory) {
		return ChestMenu.threeRows(i, inventory, this);
	}

	@Override
	public int getContainerSize() {
		return 54;
	}

	@Override
	public void unpackLootTable(@Nullable Player player) {
		if (this.getBlockState().getValue(TTBlockStateProperties.COFFIN_PART) == CoffinPart.HEAD) {
			this.setLootTable(null);
			return;
		}
		super.unpackLootTable(player);
	}

	public void tickServer(ServerLevel world, BlockPos pos, BlockState state, CoffinPart part, boolean ominous) {
		if (part == CoffinPart.HEAD || world.isClientSide()) return;
		this.coffinSpawner.tickServer(world, pos, state, state.getValue(CoffinBlock.PART), ominous);
		this.coffinWobbleLidAnimTicks = Math.max(0, this.coffinWobbleLidAnimTicks - 1);
	}

	public void tickClient(Level world, BlockPos pos, CoffinPart part, boolean ominous) {
		if (part == CoffinPart.HEAD || !world.isClientSide()) return;

		this.coffinWobbleLidAnimTicks = Math.max(0, this.coffinWobbleLidAnimTicks - 1);

		CoffinSpawnerState coffinSpawnerState = this.getState();
		if (coffinSpawnerState.isCapableOfSpawning()) {
			RandomSource randomSource = world.getRandom();
			if (randomSource.nextFloat() <= 0.0175F) {
				world.playLocalSound(pos, TTSounds.COFFIN_AMBIENT, SoundSource.BLOCKS, randomSource.nextFloat() * 0.15F + 0.05F, randomSource.nextFloat() + 0.5F, false);
			}
		}

		this.previousOpenProgress = this.openProgress;
		this.openProgress = Mth.clamp(this.openProgress + this.getLidOpenIncrement(), 0F, 1F);

		Direction connectedDirection = CoffinBlock.getConnectedDirection(this.getBlockState());
		if (connectedDirection != null) {
			BlockPos connectedPos = pos.relative(connectedDirection);
			if (world.isLoaded(connectedPos) && world.getBlockEntity(connectedPos) instanceof CoffinBlockEntity coffinBlockEntity) {
				coffinBlockEntity.previousOpenProgress = this.previousOpenProgress;
				coffinBlockEntity.openProgress = this.openProgress;
			}
		}
	}

	private float getLidOpenIncrement() {
		if (this.coffinWobbleLidAnimTicks > 0) return 0.03F;
		return this.coffinSpawner.isAttemptingToSpawnMob() ? 0.0155F : -0.03F;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		CompoundTag compoundTag = new CompoundTag();
		compoundTag.putBoolean("attempting_to_spawn_mob", this.coffinSpawner.isAttemptingToSpawnMob());
		compoundTag.putInt("coffin_wobble_lid_anim_ticks", this.coffinWobbleLidAnimTicks);
		return compoundTag;
	}

	@Override
	public void setEntityId(EntityType<?> entityType, RandomSource random) {
		BlockPos pos = this.getBlockPos();
		CoffinSpawner coffinSpawner = this.getCoffinSpawner();
		BlockState state = this.getBlockState();
		if (this.getBlockState().getValue(CoffinBlock.PART) == CoffinPart.HEAD) {
			pos = pos.relative(CoffinBlock.getConnectedDirection(state));
			if (this.level.getBlockEntity(pos) instanceof CoffinBlockEntity coffinBlockEntity) {
				coffinSpawner = coffinBlockEntity.getCoffinSpawner();
			}
		}
		coffinSpawner.getData().setEntityId(entityType, random);

		Direction coffinOrientation = CoffinBlock.getCoffinOrientation(this.level, pos);
		if (coffinOrientation != null && this.level instanceof ServerLevel serverLevel) {
			BlockPos finalPos = pos;
			CoffinSpawnerState.ACTIVE.getParticleOptionsForState().ifPresent(particleOptions ->
				CoffinBlock.spawnParticlesFrom(
					serverLevel,
					particleOptions,
					level.random.nextInt(1, 5),
					0.5D,
					coffinOrientation,
					finalPos,
					0.5D
				)
			);
			CoffinBlock.spawnParticlesFrom(
				serverLevel,
				TTParticleTypes.COFFIN_SOUL_ENTER,
				level.random.nextInt(1, 2),
				0D,
				coffinOrientation,
				finalPos,
				0.5D
			);
		}
		this.setChanged();
	}

	public CoffinSpawner getCoffinSpawner() {
		return this.coffinSpawner;
	}

	@Override
	public CoffinSpawnerState getState() {
		return !this.getBlockState().hasProperty(TTBlockStateProperties.COFFIN_STATE)
			? CoffinSpawnerState.COOLDOWN
			: this.getBlockState().getValue(TTBlockStateProperties.COFFIN_STATE);
	}

	@Override
	public void setState(@NotNull Level level, CoffinSpawnerState state) {
		this.setChanged();
		level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(TTBlockStateProperties.COFFIN_STATE, state));
	}

	@Override
	public void markUpdated() {
		this.setChanged();
		if (this.level != null) {
			this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		}
	}

	@Override
	public boolean triggerEvent(int type, int data) {
		if (this.level != null && type == 1) {
			this.wobbleStartedAtTick = this.level.getGameTime();
			if (this.level instanceof ServerLevel serverLevel
				&& this.getBlockState().getValue(CoffinBlock.PART) == CoffinPart.FOOT
				&& this.coffinSpawner.getData().hasMobToSpawnAndIsntOnCooldown(this.level, this.level.random)
			) {
				CoffinWobbleEvent.onWobble(serverLevel, this.worldPosition, this.getBlockState(), this, this.level.random);
			}
			return true;
		} else {
			return super.triggerEvent(type, data);
		}
	}
}
