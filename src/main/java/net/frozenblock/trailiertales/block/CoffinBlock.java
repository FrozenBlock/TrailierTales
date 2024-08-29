package net.frozenblock.trailiertales.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.networking.FrozenNetworking;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.block.impl.TrailierBlockStateProperties;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Spawner;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoffinBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public static final MapCodec<CoffinBlock> CODEC = RecordCodecBuilder.mapCodec(
		color -> color.group(propertiesCodec()).apply(color, CoffinBlock::new)
	);
	public static final EnumProperty<CoffinPart> PART = TrailierBlockStateProperties.COFFIN_PART;
	public static final EnumProperty<CoffinSpawnerState> STATE = TrailierBlockStateProperties.COFFIN_STATE;
	protected static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 12D, 16D);
	public static final ResourceLocation ATTRIBUTE_COFFIN_FOLLOW_RANGE = TrailierConstants.id("coffin_follow_range");

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
	protected @NotNull BlockState updateShape(@NotNull BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (direction == getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
			boolean isThisFoot = state.getValue(PART) == CoffinPart.FOOT;
			return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART)
				? isThisFoot ? state : state.setValue(STATE, neighborState.getValue(STATE))
				: Blocks.AIR.defaultBlockState();
		} else {
			return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		}
	}

	private static Direction getNeighbourDirection(CoffinPart part, Direction direction) {
		return part == CoffinPart.FOOT ? direction : direction.getOpposite();
	}

	@Override
	public @NotNull BlockState playerWillDestroy(@NotNull Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide && player.isCreative()) {
			CoffinPart coffinPart = state.getValue(PART);
			if (coffinPart == CoffinPart.FOOT) {
				BlockPos blockPos = pos.relative(getNeighbourDirection(coffinPart, state.getValue(FACING)));
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

	public static Direction getConnectedDirection(@NotNull BlockState state) {
		Direction direction = state.getValue(FACING);
		return state.getValue(PART) == CoffinPart.HEAD ? direction.getOpposite() : direction;
	}

	public static DoubleBlockCombiner.BlockType getBlockType(@NotNull BlockState state) {
		CoffinPart coffinPart = state.getValue(PART);
		return coffinPart == CoffinPart.HEAD ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
	}

	@Override
	protected @NotNull RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
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
			? createTickerHelper(
			blockEntityType,
			RegisterBlockEntities.COFFIN,
			(unusedWorld, pos, statex, coffin) -> coffin.getCoffinSpawner()
				.tickServer(serverLevel, pos, statex, statex.getValue(PART), statex.getOptionalValue(BlockStateProperties.OMINOUS).orElse(false)))
			: createTickerHelper(
			blockEntityType,
			RegisterBlockEntities.COFFIN,
			(world, pos, statex, coffin) -> coffin
				.tickClient(world, pos, statex.getValue(PART), statex.getOptionalValue(BlockStateProperties.OMINOUS).orElse(false)));
	}

	public static boolean isCoffinBlockedAt(Direction direction, @NotNull BlockGetter level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		direction = state.getValue(PART) == CoffinPart.HEAD ? direction.getOpposite() : direction;
		return isCoffinHalfBlockedAt(level, pos) || isCoffinHalfBlockedAt(level, pos.relative(direction));
	}

	private static boolean isCoffinHalfBlockedAt(@NotNull BlockGetter level, @NotNull BlockPos pos) {
		BlockPos blockPos = pos.above();
		return level.getBlockState(blockPos).isRedstoneConductor(level, blockPos);
	}

	private static int getLightLevel(@NotNull Level level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			mutableBlockPos.move(direction);
			int newLight = level.getBrightness(LightLayer.BLOCK, mutableBlockPos);
			finalLight = Math.max(finalLight, newLight);
			mutableBlockPos.move(direction, -1);
		}
		return finalLight;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
		super.appendHoverText(stack, tooltipContext, tooltip, options);
		Spawner.appendHoverText(stack, tooltip, "SpawnData");
	}

	public static void onCoffinUntrack(@Nullable Entity entity) {
		if (FrozenLibConfig.IS_DEBUG && entity != null && !entity.isRemoved() && entity.level() instanceof ServerLevel serverLevel) {
			FrozenNetworking.sendPacketToAllPlayers(
				serverLevel,
				new CoffinRemoveDebugPacket(entity.getId())
			);
		}

		if (entity instanceof LivingEntity livingEntity) {
			AttributeInstance followRange = livingEntity.getAttribute(Attributes.FOLLOW_RANGE);
			if (followRange != null) {
				followRange.removeModifier(ATTRIBUTE_COFFIN_FOLLOW_RANGE);
			}
		}
		if (entity instanceof EntityCoffinInterface entityInterface) {
			entityInterface.trailierTales$setCoffinData(null);
		}
		if (entity instanceof Apparition apparition) {
			apparition.dropItem();
			apparition.discard();
		}
	}

	@SuppressWarnings({"unchecked", "SameParameterValue"})
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
		BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker
	) {
		return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
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
		double xOffset = Math.abs(stepX * spread);
		double zOffset = Math.abs(stepZ * spread);
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
}
