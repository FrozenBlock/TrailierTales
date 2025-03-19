/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.client.renderer.blockentity.CoffinRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class CoffinSpecialRenderer implements NoDataSpecialModelRenderer {
	private final CoffinRenderer coffinRenderer;
	private final ResourceLocation headTexture;
	private final ResourceLocation footTexture;
	private final float openness;

	public CoffinSpecialRenderer(CoffinRenderer coffinRenderer, ResourceLocation headTexture, ResourceLocation footTexture, float f) {
		this.coffinRenderer = coffinRenderer;
		this.headTexture = headTexture;
		this.footTexture = footTexture;
		this.openness = f;
	}

	@Override
	public void render(ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl) {
		this.coffinRenderer.renderInHand(poseStack, multiBufferSource, i, j, this.headTexture, this.footTexture, this.openness);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(ResourceLocation headTexture, ResourceLocation footTexture, float openness) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<CoffinSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("head_texture").forGetter(CoffinSpecialRenderer.Unbaked::headTexture),
					ResourceLocation.CODEC.fieldOf("foot_texture").forGetter(CoffinSpecialRenderer.Unbaked::footTexture),
					Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(CoffinSpecialRenderer.Unbaked::openness)
				)
				.apply(instance, CoffinSpecialRenderer.Unbaked::new)
		);

		public Unbaked(ResourceLocation headTexture, ResourceLocation footTexture) {
			this(headTexture, footTexture, 0F);
		}

		@Override
		public @NotNull MapCodec<CoffinSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public @NotNull SpecialModelRenderer<?> bake(@NotNull EntityModelSet entityModelSet) {
			return new CoffinSpecialRenderer(new CoffinRenderer(entityModelSet), this.headTexture, this.footTexture, this.openness);
		}
	}
}
