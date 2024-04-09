package net.frozenblock.trailiertales.worldgen.impl;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.jetbrains.annotations.NotNull;

public class SuspiciousBlockFeature extends Feature<SuspiciousBlockConfiguration> {

	public SuspiciousBlockFeature(Codec<SuspiciousBlockConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<SuspiciousBlockConfiguration> context) {
		RandomSource randomSource = context.random();
		BlockPos blockPos = context.origin();
		WorldGenLevel worldGenLevel = context.level();
		SuspiciousBlockConfiguration oreConfiguration = context.config();
		float f = randomSource.nextFloat() * (float) Math.PI;
		float g = (float)oreConfiguration.size / 8.0F;
		int i = Mth.ceil(((float)oreConfiguration.size / 16F * 2F + 1F) / 2F);
		double d = (double)blockPos.getX() + Math.sin(f) * (double)g;
		double e = (double)blockPos.getX() - Math.sin(f) * (double)g;
		double h = (double)blockPos.getZ() + Math.cos(f) * (double)g;
		double j = (double)blockPos.getZ() - Math.cos(f) * (double)g;
		int k = 2;
		double l = blockPos.getY() + randomSource.nextInt(3) - 2;
		double m = blockPos.getY() + randomSource.nextInt(3) - 2;
		int n = blockPos.getX() - Mth.ceil(g) - i;
		int o = blockPos.getY() - 2 - i;
		int p = blockPos.getZ() - Mth.ceil(g) - i;
		int q = 2 * (Mth.ceil(g) + i);
		int r = 2 * (2 + i);

		for(int s = n; s <= n + q; ++s) {
			for(int t = p; t <= p + q; ++t) {
				if (o <= worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, s, t)) {
					return this.doPlace(worldGenLevel, randomSource, oreConfiguration, d, e, h, j, l, m, n, o, p, q, r);
				}
			}
		}

		return false;
	}

	protected boolean doPlace(
		WorldGenLevel level,
		RandomSource random,
		@NotNull SuspiciousBlockConfiguration config,
		double minX,
		double maxX,
		double minZ,
		double maxZ,
		double minY,
		double maxY,
		int x,
		int y,
		int z,
		int width,
		int height
	) {
		int i = 0;
		BitSet bitSet = new BitSet(width * height * width);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		int j = config.size;
		double[] ds = new double[j * 4];

		for(int k = 0; k < j; ++k) {
			float f = (float)k / (float)j;
			double d = Mth.lerp(f, minX, maxX);
			double e = Mth.lerp(f, minY, maxY);
			double g = Mth.lerp(f, minZ, maxZ);
			double h = random.nextDouble() * (double)j / 16;
			double l = ((double)(Mth.sin((float) Math.PI * f) + 1F) * h + 1) / 2;
			ds[k * 4] = d;
			ds[k * 4 + 1] = e;
			ds[k * 4 + 2] = g;
			ds[k * 4 + 3] = l;
		}

		for(int k = 0; k < j - 1; ++k) {
			if (!(ds[k * 4 + 3] <= 0)) {
				for(int m = k + 1; m < j; ++m) {
					if (!(ds[m * 4 + 3] <= 0)) {
						double d = ds[k * 4] - ds[m * 4];
						double e = ds[k * 4 + 1] - ds[m * 4 + 1];
						double g = ds[k * 4 + 2] - ds[m * 4 + 2];
						double h = ds[k * 4 + 3] - ds[m * 4 + 3];
						if (h * h > d * d + e * e + g * g) {
							if (h > 0.0) {
								ds[m * 4 + 3] = -1.0;
							} else {
								ds[k * 4 + 3] = -1.0;
							}
						}
					}
				}
			}
		}

		try (BulkSectionAccess bulkSectionAccess = new BulkSectionAccess(level)) {
			for(int m = 0; m < j; ++m) {
				double d = ds[m * 4 + 3];
				if (!(d < 0.0)) {
					double e = ds[m * 4];
					double g = ds[m * 4 + 1];
					double h = ds[m * 4 + 2];
					int n = Math.max(Mth.floor(e - d), x);
					int o = Math.max(Mth.floor(g - d), y);
					int p = Math.max(Mth.floor(h - d), z);
					int q = Math.max(Mth.floor(e + d), n);
					int r = Math.max(Mth.floor(g + d), o);
					int s = Math.max(Mth.floor(h + d), p);

					for(int t = n; t <= q; ++t) {
						double u = ((double)t + 0.5 - e) / d;
						if (u * u < 1) {
							for(int v = o; v <= r; ++v) {
								double w = ((double)v + 0.5 - g) / d;
								if (u * u + w * w < 1) {
									for(int aa = p; aa <= s; ++aa) {
										double ab = ((double)aa + 0.5 - h) / d;
										if (u * u + w * w + ab * ab < 1 && !level.isOutsideBuildHeight(v)) {
											int ac = t - x + (v - y) * width + (aa - z) * width * height;
											if (!bitSet.get(ac)) {
												bitSet.set(ac);
												mutableBlockPos.set(t, v, aa);
												if (level.ensureCanWrite(mutableBlockPos)) {
													LevelChunkSection levelChunkSection = bulkSectionAccess.getSection(mutableBlockPos);
													if (levelChunkSection != null) {
														int ad = SectionPos.sectionRelative(t);
														int ae = SectionPos.sectionRelative(v);
														int af = SectionPos.sectionRelative(aa);
														BlockState blockState = levelChunkSection.getBlockState(ad, ae, af);

														for(OreConfiguration.TargetBlockState targetBlockState : config.targetStates) {
															if (canPlaceSuspicious(blockState, bulkSectionAccess::getBlockState, random, config, targetBlockState, mutableBlockPos)) {
																levelChunkSection.setBlockState(ad, ae, af, targetBlockState.state, false);
																if (level.getBlockEntity(new BlockPos(ad, ae, af)) instanceof BrushableBlockEntity brushableBlockEntity) {
																	brushableBlockEntity.setLootTable(
																		ResourceKey.create(Registries.LOOT_TABLE, config.lootTable),
																		random.nextLong()
																	);
																}
																++i;
																break;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return i > 0;
	}

	public static boolean canPlaceSuspicious(
		BlockState state,
		Function<BlockPos, BlockState> adjacentStateAccessor,
		RandomSource random,
		SuspiciousBlockConfiguration config,
		@NotNull OreConfiguration.TargetBlockState targetState,
		BlockPos.MutableBlockPos mutablePos
	) {
		if (!targetState.target.test(state, random) || random.nextFloat() > config.placeChancePerBlock) {
			return false;
		} else if (shouldSkipAirCheck(random, config.discardChanceOnAirExposure)) {
			return true;
		} else {
			return !isAdjacentToAir(adjacentStateAccessor, mutablePos);
		}
	}

	protected static boolean shouldSkipAirCheck(RandomSource random, float chance) {
		if (chance <= 0F) {
			return true;
		} else if (chance >= 1F) {
			return false;
		} else {
			return random.nextFloat() >= chance;
		}
	}
}
