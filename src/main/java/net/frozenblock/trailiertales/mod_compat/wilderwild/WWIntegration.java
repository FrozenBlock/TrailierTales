package net.frozenblock.trailiertales.mod_compat.wilderwild;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.lib.worldgen.structure.api.StructureProcessorApi;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;

public class WWIntegration extends AbstractWWIntegration {

	public WWIntegration() {
		super();
	}

	@Override
	public void init() {
		BlockSoundTypeOverwrites.addBlock(
			TTBlocks.SUSPICIOUS_CLAY,
			TTSounds.SUSPICIOUS_CLAY_WW,
			() -> WWBlockConfig.get().blockSounds.claySounds
		);
		BlockSoundTypeOverwrites.addBlock(
			Blocks.SUSPICIOUS_GRAVEL,
			TTSounds.SUSPICIOUS_GRAVEL_WW,
			() -> WWBlockConfig.get().blockSounds.claySounds
		);

		StructureProcessorApi.addProcessor(
			SavannaRuinsGenerator.SAVANNA_RUINS_KEY.location(),
			new RuleProcessor(
				ImmutableList.of(
					new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, WWBlocks.CRACKED_MUD_BRICKS.defaultBlockState()),
					new ProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICKS.defaultBlockState())
				)
			)
		);
		StructureProcessorApi.addProcessor(
			SavannaRuinsGenerator.SAVANNA_RUINS_KEY.location(),
			new BlockStateRespectingRuleProcessor(
				ImmutableList.of(
					new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_STAIRS, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_STAIRS),
					new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_SLAB),
					new BlockStateRespectingProcessorRule(new RandomBlockMatchTest(Blocks.MUD_BRICK_SLAB, 0.05F), AlwaysTrueTest.INSTANCE, WWBlocks.MOSSY_MUD_BRICK_WALL)
				)
			)
		);

		StructureProcessorApi.addProcessor(CatacombsGenerator.CATACOMBS_KEY.location(),
			new BlockStateRespectingRuleProcessor(
				List.of(
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState()),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),

					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.LEFT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),

					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.EAST).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					),
					new BlockStateRespectingProcessorRule(
						new BlockStateMatchTest(Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.WEST).setValue(ChestBlock.TYPE, ChestType.RIGHT)),
						AlwaysTrueTest.INSTANCE, WWBlocks.STONE_CHEST
					)
				)
			)
		);
	}

	@Override
	public boolean newClaySounds() {
		return WWBlockConfig.get().blockSounds.claySounds;
	}

	@Override
	public boolean newGravelSounds() {
		return WWBlockConfig.get().blockSounds.gravelSounds;
	}
}
