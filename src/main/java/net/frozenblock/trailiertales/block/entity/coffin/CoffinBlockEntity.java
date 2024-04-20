package net.frozenblock.trailiertales.block.entity.coffin;

import com.mojang.logging.LogUtils;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.block.impl.TrailierBlockStateProperties;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class CoffinBlockEntity extends BlockEntity implements Spawner, CoffinSpawner.StateAccessor {
	private static final Logger LOGGER = LogUtils.getLogger();
	private CoffinSpawner coffinSpawner;

	public CoffinBlockEntity(BlockPos pos, BlockState state) {
		super(RegisterBlockEntities.COFFIN, pos, state);
		PlayerDetector playerDetector = CoffinSpawner.IN_CATACOMBS_NO_CREATIVE_PLAYERS;
		PlayerDetector.EntitySelector entitySelector = PlayerDetector.EntitySelector.SELECT_FROM_LEVEL;
		this.coffinSpawner = new CoffinSpawner(this, playerDetector, entitySelector);
	}

	@Override
	protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.loadAdditional(nbt, lookupProvider);
		if (nbt.contains("normal_config")) {
			CompoundTag compoundTag = nbt.getCompound("normal_config").copy();
			nbt.put("ominous_config", compoundTag.merge(nbt.getCompound("ominous_config")));
		}

		if (this.getBlockState().getValue(TrailierBlockStateProperties.COFFIN_PART) == CoffinPart.FOOT) {
			this.coffinSpawner.codec().parse(NbtOps.INSTANCE, nbt).resultOrPartial(LOGGER::error).ifPresent(trialSpawner -> this.coffinSpawner = trialSpawner);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.saveAdditional(nbt, lookupProvider);
		if (this.getBlockState().getValue(TrailierBlockStateProperties.COFFIN_PART) == CoffinPart.FOOT) {
			this.coffinSpawner
				.codec()
				.encodeStart(NbtOps.INSTANCE, this.coffinSpawner)
				.ifSuccess(logicNbt -> nbt.merge((CompoundTag) logicNbt))
				.ifError(error -> LOGGER.warn("Failed to encode CoffinSpawner {}", error.message()));
		}
	}

	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		return this.coffinSpawner.getData().getUpdateTag(this.getBlockState().getValue(TrailierBlockStateProperties.COFFIN_STATE));
	}

	@Override
	public boolean onlyOpCanSetNbt() {
		return true;
	}

	@Override
	public void setEntityId(EntityType<?> entityType, RandomSource random) {
		this.coffinSpawner.getData().setEntityId(this.coffinSpawner, random, entityType);
		this.setChanged();
	}

	public CoffinSpawner getCoffinSpawner() {
		return this.coffinSpawner;
	}

	@Override
	public CoffinSpawnerState getState() {
		return !this.getBlockState().hasProperty(TrailierBlockStateProperties.COFFIN_STATE)
			? CoffinSpawnerState.INACTIVE
			: this.getBlockState().getValue(TrailierBlockStateProperties.COFFIN_STATE);
	}

	@Override
	public void setState(@NotNull Level level, CoffinSpawnerState state) {
		this.setChanged();
		level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(TrailierBlockStateProperties.COFFIN_STATE, state));
	}

	@Override
	public void markUpdated() {
		this.setChanged();
		if (this.level != null) {
			this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		}
	}
}
