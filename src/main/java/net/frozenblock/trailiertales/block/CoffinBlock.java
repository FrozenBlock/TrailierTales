/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.networking.FrozenNetworking;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.block.impl.TTBlockStateProperties;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoffinBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public static final MapCodec<CoffinBlock> CODEC = RecordCodecBuilder.mapCodec(
		color -> color.group(propertiesCodec()).apply(color, CoffinBlock::new)
	);
	public static final EnumProperty<CoffinPart> PART = TTBlockStateProperties.COFFIN_PART;
	public static final EnumProperty<CoffinSpawnerState> STATE = TTBlockStateProperties.COFFIN_STATE;
	protected static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 12D, 16D);
	public static final ResourceLocation ATTRIBUTE_COFFIN_FOLLOW_RANGE = TTConstants.id("coffin_follow_range");

	@Override
	public @NotNull MapCodec<CoffinBlock> codec() {
		return CODEC;
	}

	public CoffinBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(PART, CoffinPart.FOOT)
				.setValue(STATE, CoffinSpawnerState.INACTIVE)
		);
	}

	@Nullable
	public static Direction getCoffinOrientation(@NotNull BlockGetter level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		return blockState.getBlock() instanceof CoffinBlock ? blockState.getValue(FACING) : null;
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState state,
		LevelReader level,
		ScheduledTickAccess tickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (direction == getConnectedDirection(state.getValue(PART), state.getValue(FACING))) {
			boolean isThisFoot = state.getValue(PART) == CoffinPart.FOOT;
			return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART)
				? isThisFoot ? state : state.setValue(STATE, neighborState.getValue(STATE))
				: Blocks.AIR.defaultBlockState();
		} else {
			return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
		}
	}

	private static Direction getConnectedDirection(CoffinPart part, Direction direction) {
		return part == CoffinPart.FOOT ? direction : direction.getOpposite();
	}

	public static Direction getConnectedDirection(@NotNull BlockState state) {
		return getConnectedDirection(state.getValue(PART), state.getValue(FACING));
	}

	@Override
	public @NotNull BlockState playerWillDestroy(@NotNull Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide && player.isCreative()) {
			CoffinPart coffinPart = state.getValue(PART);
			if (coffinPart == CoffinPart.FOOT) {
				BlockPos blockPos = pos.relative(getConnectedDirection(coffinPart, state.getValue(FACING)));
				BlockState blockState = level.getBlockState(blockPos);
				if (blockState.is(this) && blockState.getValue(PART) == CoffinPart.HEAD) {
					level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
					level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
				}
			}
		}

		return super.playerWillDestroy(level, pos, state, player);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		Direction direction = context.getHorizontalDirection();
		BlockPos blockPos = context.getClickedPos();
		BlockPos blockPos2 = blockPos.relative(direction);
		Level level = context.getLevel();
		return level.getBlockState(blockPos2).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPos2)
			? this.defaultBlockState().setValue(FACING, direction)
			: null;
	}

	@Override
	protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction direction = getConnectedDirection(state).getOpposite();
		return switch (direction) {
			case NORTH -> SHAPE;
			case SOUTH -> SHAPE;
			case WEST -> SHAPE;
			default -> SHAPE;
		};
	}

	public static DoubleBlockCombiner.BlockType getBlockType(@NotNull BlockState state) {
		CoffinPart coffinPart = state.getValue(PART);
		return coffinPart == CoffinPart.HEAD ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, PART, STATE);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
		if (!level.isClientSide) {
			BlockPos blockPos = pos.relative(state.getValue(FACING));
			level.setBlock(blockPos, state.setValue(PART, CoffinPart.HEAD), UPDATE_ALL);
			level.blockUpdated(pos, Blocks.AIR);
			state.updateNeighbourShapes(level, pos, UPDATE_ALL);
		}
	}

	@Override
	protected @NotNull InteractionResult useItemOn(
		@NotNull ItemStack stack, BlockState state, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hitResult
	) {
		if (stack.getItem() instanceof SpawnEggItem) return InteractionResult.CONSUME;
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	@Override
	protected @NotNull InteractionResult useWithoutItem(BlockState state, @NotNull Level level, BlockPos pos, Player entity, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof CoffinBlockEntity coffinBlockEntity
			&& (level.getGameTime() - coffinBlockEntity.wobbleStartedAtTick) >= CoffinBlockEntity.WOBBLE_COOLDOWN
			&& TTBlockConfig.get().coffin.wobble
		) {
			wobble(level, pos, state, entity);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	public static void wobble(@NotNull Level level, BlockPos pos, @NotNull BlockState state, Player player) {
		level.blockEvent(pos, state.getBlock(), 1, 0);
		BlockPos neighborPos = pos.relative(CoffinBlock.getConnectedDirection(state));
		BlockState neighborState = level.getBlockState(neighborPos);
		if (neighborState.is(state.getBlock())) {
			level.blockEvent(neighborPos, state.getBlock(), 1, 0);
		}

		level.playSound(null, pos, TTSounds.COFFIN_WOBBLE, SoundSource.BLOCKS, 0.5F, 0.9F + level.random.nextFloat() * 0.2F);
		level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}

	@Override
	protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int type, int data) {
		super.triggerEvent(state, level, pos, type, data);
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(type, data);
	}

	public boolean isCoffinActive(@NotNull BlockState state) {
		return state.getValue(STATE).isCapableOfSpawning();
	}

	@Override
	protected long getSeed(@NotNull BlockState state, @NotNull BlockPos pos) {
		BlockPos blockPos = pos.relative(state.getValue(FACING), state.getValue(PART) == CoffinPart.HEAD ? 0 : 1);
		return Mth.getSeed(blockPos.getX(), pos.getY(), blockPos.getZ());
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
		return false;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CoffinBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level instanceof ServerLevel serverLevel
			? BaseEntityBlock.createTickerHelper(
				blockEntityType,
			TTBlockEntityTypes.COFFIN,
			(unusedWorld, pos, statex, coffin) -> coffin
				.tickServer(serverLevel, pos, statex, statex.getValue(PART), statex.getOptionalValue(BlockStateProperties.OMINOUS).orElse(false)))
			: BaseEntityBlock.createTickerHelper(
				blockEntityType,
			TTBlockEntityTypes.COFFIN,
			(world, pos, statex, coffin) -> coffin
				.tickClient(world, pos, statex.getValue(PART), statex.getOptionalValue(BlockStateProperties.OMINOUS).orElse(false)));
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
		super.appendHoverText(stack, tooltipContext, tooltip, options);
		Spawner.appendHoverText(stack, tooltip, "SpawnData");
	}

	public static void onCoffinUntrack(ServerLevel level, @Nullable Entity entity, @Nullable CoffinSpawner coffinSpawner, boolean remove) {
		if (FrozenLibConfig.IS_DEBUG && entity != null && !entity.isRemoved() && entity.level() instanceof ServerLevel serverLevel) {
			FrozenNetworking.sendPacketToAllPlayers(
				serverLevel,
				new CoffinRemoveDebugPacket(entity.getId())
			);
		}

		if (entity != null) {
			entity.playSound(TTSounds.COFFIN_VANISH_MOB, 0.8F, 0.9F + (entity.getRandom().nextFloat() * 0.2F));
		}

		if (entity instanceof LivingEntity livingEntity) {
			AttributeInstance followRange = livingEntity.getAttribute(Attributes.FOLLOW_RANGE);
			if (followRange != null) followRange.removeModifier(ATTRIBUTE_COFFIN_FOLLOW_RANGE);
		}

		if (entity instanceof EntityCoffinInterface entityInterface) {
			entityInterface.trailierTales$setCoffinData(null);
		}

		if (entity instanceof Apparition apparition && remove) {
			apparition.dropItem();
			apparition.level().broadcastEntityEvent(apparition, EntityEvent.POOF);
			apparition.discard();
			apparition.dropPreservedEquipment(level);
			if (coffinSpawner != null) {
				coffinSpawner.onApparitionRemovedOrKilled(entity.level());
			}
		} else if (remove && entity instanceof Mob mob && !mob.isPersistenceRequired() && !mob.requiresCustomPersistence()) {
			mob.level().broadcastEntityEvent(mob, EntityEvent.POOF);
			mob.discard();
		}
	}

	public static void spawnParticlesFrom(
		@NotNull ServerLevel world,
		ParticleOptions particleOptions,
		int count,
		double speed,
		@NotNull Direction coffinOrientation,
		@NotNull BlockPos pos,
		double spread
	) {
		boolean isNegativeDirection = coffinOrientation.getAxisDirection() == Direction.AxisDirection.NEGATIVE;
		boolean isOppositeX = isNegativeDirection && coffinOrientation.getAxis() == Direction.Axis.X;
		boolean isOppositeZ = isNegativeDirection && coffinOrientation.getAxis() == Direction.Axis.Z;
		double stepX = coffinOrientation.getStepX();
		double stepZ = coffinOrientation.getStepZ();
		double relativeX = isOppositeX ? 0D : stepX == 0D ? 0.5D : stepX;
		double relativeZ = isOppositeZ ? 0D : stepZ == 0D ? 0.5D : stepZ;
		double xOffset = Math.max(0.5D, Math.abs(stepX)) * spread;
		double zOffset = Math.max(0.5D, Math.abs(stepZ)) * spread;
		world.sendParticles(
			particleOptions,
			pos.getX() + relativeX,
			pos.getY() + 0.95D,
			pos.getZ() + relativeZ,
			count,
			xOffset,
			0D,
			zOffset,
			speed
		);
	}

	public static @NotNull Vec3 getCenter(@NotNull BlockState state, @NotNull BlockPos pos) {
		Direction coffinOrientation = state.getValue(FACING);
		boolean isNegativeDirection = coffinOrientation.getAxisDirection() == Direction.AxisDirection.NEGATIVE;
		boolean isOppositeX = isNegativeDirection && coffinOrientation.getAxis() == Direction.Axis.X;
		boolean isOppositeZ = isNegativeDirection && coffinOrientation.getAxis() == Direction.Axis.Z;
		double stepX = coffinOrientation.getStepX();
		double stepZ = coffinOrientation.getStepZ();
		double relativeX = isOppositeX ? 0D : stepX == 0D ? 0.5D : stepX;
		double relativeZ = isOppositeZ ? 0D : stepZ == 0D ? 0.5D : stepZ;
		return new Vec3(pos.getX() + relativeX, pos.getY() + 0.95D, pos.getZ() + relativeZ);
	}
}
