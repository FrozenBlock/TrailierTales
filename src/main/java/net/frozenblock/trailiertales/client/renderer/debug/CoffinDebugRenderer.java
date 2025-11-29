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

package net.frozenblock.trailiertales.client.renderer.debug;

import java.util.Objects;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.registry.TTDebugSubscriptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CoffinDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
	private static final int CONNECTION_COLOR = ARGB.color(255, 50, 125, 90);
	private static final int SELECTED_CONNECTION_COLOR = ARGB.color(255, 255, 50, 255);
	private static final int COFFIN_HIGHLIGHT_COLOR = ARGB.colorFromFloat(0.2F, 0.2F, 1F, 0.3F);
	private static final int TEXT_COLOR = ARGB.color(255, 255, 255, 255);
	private final Minecraft minecraft;
	@Nullable
	private UUID lastLookedAtUuid;

	public CoffinDebugRenderer(Minecraft client) {
		this.minecraft = client;
	}

	@Override
	public void emitGizmos(double cameraX, double cameraY, double cameraZ, DebugValueAccess debugValueAccess, Frustum frustum, float partialTick) {
		debugValueAccess.forEachEntity(TTDebugSubscriptions.COFFINS, (entity, debugInfo) -> {
			final boolean selected = isEntitySelected(entity);
			final BlockPos coffinPos = debugInfo.coffinPos();

			if (selected) {
				Gizmos.cuboid(coffinPos, 0.05F, GizmoStyle.fill(COFFIN_HIGHLIGHT_COLOR));
				Gizmos.billboardTextOverMob(entity, 3, entity.getDisplayName().getString(), TEXT_COLOR, 0.48F);
				Gizmos.billboardTextOverMob(entity, 2, "Last Interaction: " + debugInfo.lastInteractionDifference(), TEXT_COLOR, 0.48F);
			}

			Gizmos.arrow(entity.getPosition(partialTick), coffinPos.getCenter(), selected ? SELECTED_CONNECTION_COLOR : CONNECTION_COLOR);
		});

		if (!this.minecraft.player.isSpectator()) this.updateLastLookedAtUuid();
	}

	private boolean isEntitySelected(Entity entity) {
		return Objects.equals(this.lastLookedAtUuid, entity.getUUID());
	}

	private void updateLastLookedAtUuid() {
		DebugRenderer.getTargetedEntity(this.minecraft.getCameraEntity(), 8).ifPresent(entity -> this.lastLookedAtUuid = entity.getUUID());
	}

}
