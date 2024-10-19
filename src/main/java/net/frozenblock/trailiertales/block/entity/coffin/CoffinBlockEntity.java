package net.frozenblock.trailiertales.block.entity.coffin;

import com.mojang.logging.LogUtils;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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

	private float previousOpenProgress;
	private float openProgress;

	public CoffinBlockEntity(BlockPos pos, BlockState state) {
		super(TTBlockEntityTypes.COFFIN, pos, state);
		PlayerDetector.EntitySelector entitySelector = PlayerDetector.EntitySelector.SELECT_FROM_LEVEL;
		this.coffinSpawner = new CoffinSpawner(this, entitySelector);
	}

	public float getOpenProgress(float partialTick) {
		return Mth.lerp(partialTick, this.previousOpenProgress, this.openProgress);
	}

	@Override
	protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.loadAdditional(nbt, lookupProvider);
		if (nbt.contains("normal_config")) {
			CompoundTag compoundTag = nbt.getCompound("normal_config").copy();
			nbt.put("ominous_config", compoundTag.merge(nbt.getCompound("ominous_config")));
		}

		if (this.getBlockState().getValue(TTBlockStateProperties.COFFIN_PART) == CoffinPart.FOOT) {
			this.coffinSpawner.codec().parse(NbtOps.INSTANCE, nbt).resultOrPartial(LOGGER::error).ifPresent(coffinSpawner -> this.coffinSpawner = coffinSpawner);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.saveAdditional(nbt, lookupProvider);
		if (this.getBlockState().getValue(TTBlockStateProperties.COFFIN_PART) == CoffinPart.FOOT) {
			this.coffinSpawner
				.codec()
				.encodeStart(NbtOps.INSTANCE, this.coffinSpawner)
				.ifSuccess(logicNbt -> nbt.merge((CompoundTag) logicNbt))
				.ifError(error -> LOGGER.warn("Failed to encode CoffinSpawner {}", error.message()));
		}
	}

	public void tickClient(Level world, BlockPos pos, CoffinPart part, boolean ominous) {
		if (part == CoffinPart.HEAD || !world.isClientSide) {
			return;
		}

		CoffinSpawnerState coffinSpawnerState = this.getState();
		//coffinSpawnerState.emitParticles(world, pos, ominous);

		if (coffinSpawnerState.isCapableOfSpawning()) {
			RandomSource randomSource = world.getRandom();
			if (randomSource.nextFloat() <= 0.0175F) {
				world.playLocalSound(pos, TTSounds.COFFIN_AMBIENT, SoundSource.BLOCKS, randomSource.nextFloat() * 0.15F + 0.05F, randomSource.nextFloat() + 0.5F, false);
			}
		}

		this.previousOpenProgress = this.openProgress;
		float lidIncrement = this.coffinSpawner.isAttemptingToSpawnMob() ? 0.0155F : -0.03F;
		this.openProgress = Mth.clamp(this.openProgress + lidIncrement, 0F, 1F);

		Direction facing = CoffinBlock.getCoffinOrientation(world, pos);
		if (facing != null && world.getBlockEntity(pos.relative(facing)) instanceof CoffinBlockEntity coffinBlockEntity) {
			coffinBlockEntity.previousOpenProgress = this.previousOpenProgress;
			coffinBlockEntity.openProgress = this.openProgress;
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		return this.coffinSpawner.getUpdateTag();
	}

	@Override
	public boolean onlyOpCanSetNbt() {
		return true;
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
		coffinSpawner.getData().setEntityId(entityType, this.level, random, pos);
		this.setChanged();
	}

	public CoffinSpawner getCoffinSpawner() {
		return this.coffinSpawner;
	}

	@Override
	public CoffinSpawnerState getState() {
		return !this.getBlockState().hasProperty(TTBlockStateProperties.COFFIN_STATE)
			? CoffinSpawnerState.INACTIVE
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
}
