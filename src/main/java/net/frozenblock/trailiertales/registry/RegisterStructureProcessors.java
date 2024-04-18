package net.frozenblock.trailiertales.registry;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class RegisterStructureProcessors {
	public static final float SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE = 0.85F;
	public static final ResourceKey<StructureProcessorList> SUSPICIOUS_BLOCK_TO_NORMAL_085 = createKey("suspicious_block_to_normal_085");
	public static final float SKULL_SMALL_ROTATE_CHANCE = 0.3F;
	public static final float SKULL_LARGE_ROTATE_CHANCE = 0.075F;
	public static final ResourceKey<StructureProcessorList> CATACOMBS_DEGRADATION = createKey("catacombs_degradation");

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);

		register(
			context,
			RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085,
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_SAND, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.SAND.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_RED_SAND, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.RED_SAND.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_CLAY, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.CLAY.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_DIRT, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);

		register(
			context,
			CATACOMBS_DEGRADATION,
			ImmutableList.of(
				new RuleProcessor(
					ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.SKELETON_SKULL, 0.375F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICK_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3F), AlwaysTrueTest.INSTANCE, Blocks.CAVE_AIR.defaultBlockState()),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, TrailierTalesSharedConstants.id(string));
	}

	@NotNull
	private static Holder<StructureProcessorList> register(
		@NotNull BootstrapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list
	) {
		return entries.register(key, new StructureProcessorList(list));
	}
}
