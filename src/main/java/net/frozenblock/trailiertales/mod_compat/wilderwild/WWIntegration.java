package net.frozenblock.trailiertales.mod_compat.wilderwild;

import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import java.util.List;

public class WWIntegration extends AbstractWWIntegration {

	public WWIntegration() {
		super();
	}

	@Override
	public void init() {
		StructureProcessorApi.addNamespaceWithKeywordTarget(TrailierConstants.MOD_ID, "catacombs/",
			new RuleProcessor(
				List.of(
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState()),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(RegisterProperties.ANCIENT, true)
					),

					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.LEFT).setValue(RegisterProperties.ANCIENT, true)
					),

					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(RegisterProperties.ANCIENT, true)
					),
					new ProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, RegisterBlocks.STONE_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.RIGHT).setValue(RegisterProperties.ANCIENT, true)
					)
				)
			)
		);
	}
}
