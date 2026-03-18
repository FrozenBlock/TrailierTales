/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.client.renderer.MultiblockCoffinResources;
import net.frozenblock.trailiertales.client.renderer.blockentity.CoffinRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;

@Environment(EnvType.CLIENT)
public class CoffinSpecialRenderer implements NoDataSpecialModelRenderer {
	public static final MultiblockCoffinResources<Identifier> INACTIVE = createDefaultTextures("inactive");
	public static final MultiblockCoffinResources<Identifier> ACTIVE = createDefaultTextures("active");
	public static final MultiblockCoffinResources<Identifier> COOLDOWN = createDefaultTextures("cooldown");
	public static final MultiblockCoffinResources<Identifier> IRRITATED = createDefaultTextures("irritated");
	public static final MultiblockCoffinResources<Identifier> AGGRESSIVE = createDefaultTextures("aggressive");
	public static final MultiblockCoffinResources<Identifier> OMINOUS = createDefaultTextures("ominous");
	private final CoffinRenderer coffinRenderer;
	private final Identifier texture;
	private final CoffinPart part;
	private final float openness;

	public CoffinSpecialRenderer(CoffinRenderer coffinRenderer, Identifier texture, CoffinPart part, float openness) {
		this.coffinRenderer = coffinRenderer;
		this.texture = texture;
		this.part = part;
		this.openness = openness;
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, int overlay, boolean hasFoil, int outlineColor) {
		this.coffinRenderer.renderInHand(poseStack, collector, light, overlay, this.texture, this.part, this.openness, outlineColor);
	}

	@Override
	public void getExtents(Consumer<Vector3fc> consumer) {
		this.coffinRenderer.getExtents(this.part, consumer);
	}

	private static MultiblockCoffinResources<Identifier> createDefaultTextures(String suffix) {
		return new MultiblockCoffinResources<>(
			TTConstants.id("textures/entity/coffin/coffin_head_" + suffix + ".png"),
			TTConstants.id("textures/entity/coffin/coffin_foot_" + suffix + ".png")
		);
	}

	public record Unbaked(Identifier texture, CoffinPart part, float openness) implements NoDataSpecialModelRenderer.Unbaked {
		public static final MapCodec<CoffinSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				Identifier.CODEC.fieldOf("texture").forGetter(CoffinSpecialRenderer.Unbaked::texture),
				CoffinPart.CODEC.fieldOf("part").forGetter(CoffinSpecialRenderer.Unbaked::part),
				Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(CoffinSpecialRenderer.Unbaked::openness)
			).apply(instance, CoffinSpecialRenderer.Unbaked::new)
		);

		public Unbaked(Identifier texture, CoffinPart part) {
			this(texture, part, 0F);
		}

		@Override
		public MapCodec<CoffinSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public CoffinSpecialRenderer bake(BakingContext context) {
			return new CoffinSpecialRenderer(new CoffinRenderer(context.entityModelSet()), this.texture, this.part, this.openness);
		}
	}
}
