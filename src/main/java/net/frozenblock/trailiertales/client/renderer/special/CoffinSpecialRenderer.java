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

package net.frozenblock.trailiertales.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.renderer.blockentity.CoffinRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3fc;

@Environment(EnvType.CLIENT)
public class CoffinSpecialRenderer implements NoDataSpecialModelRenderer {
	private final CoffinRenderer coffinRenderer;
	private final Identifier headTexture;
	private final Identifier footTexture;
	private final float openness;

	public CoffinSpecialRenderer(CoffinRenderer coffinRenderer, Identifier headTexture, Identifier footTexture, float f) {
		this.coffinRenderer = coffinRenderer;
		this.headTexture = headTexture;
		this.footTexture = footTexture;
		this.openness = f;
	}

	@Override
	public void submit(ItemDisplayContext context, PoseStack poseStack, SubmitNodeCollector collector, int i, int j, boolean bl, int k) {
		this.coffinRenderer.renderInHand(poseStack, collector, i, j, this.headTexture, this.footTexture, this.openness, k);
	}

	@Override
	public void getExtents(Consumer<Vector3fc> consumer) {
		this.coffinRenderer.getExtents(consumer);
	}

	public record Unbaked(Identifier headTexture, Identifier footTexture, float openness) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<CoffinSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Identifier.CODEC.fieldOf("head_texture").forGetter(CoffinSpecialRenderer.Unbaked::headTexture),
					Identifier.CODEC.fieldOf("foot_texture").forGetter(CoffinSpecialRenderer.Unbaked::footTexture),
					Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(CoffinSpecialRenderer.Unbaked::openness)
				)
				.apply(instance, CoffinSpecialRenderer.Unbaked::new)
		);

		public Unbaked(Identifier headTexture, Identifier footTexture) {
			this(headTexture, footTexture, 0F);
		}

		@Override
		public MapCodec<CoffinSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(BakingContext entityModelSet) {
			return new CoffinSpecialRenderer(new CoffinRenderer(entityModelSet.entityModelSet()), this.headTexture, this.footTexture, this.openness);
		}
	}
}
