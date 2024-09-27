package net.frozenblock.trailiertales.debug.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.debug.client.impl.DebugRenderManager;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CoffinDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
	private static final int CONNECTION_COLOR = ARGB.color(255, 50, 125, 90);
	private static final int SELECTED_CONNECTION_COLOR = ARGB.color(255, 255, 50, 255);
	private static final int TEXT_COLOR = ARGB.color(255, 255, 255, 255);
	private final Minecraft minecraft;
	private final IntArrayList scheduledRemovals = new IntArrayList();
	private final Map<Integer, EntityCoffinData> connections = Maps.newHashMap();
	@Nullable
	private Integer lastLookedAtId;
	private long gameTime;

	public CoffinDebugRenderer(Minecraft client) {
		this.minecraft = client;
	}

	private void clearRemovedEntities() {
		this.connections.entrySet().removeIf(entry -> {
			int id = entry.getKey();
			if (scheduledRemovals.contains(id)) return true;
			Entity entity = this.minecraft.level.getEntity(id);
			return entity == null || entity.isRemoved();
		});
	}

	public void addConnection(int entityId, BlockPos coffinPos, long lastInteractionTime, long gameTime) {
		this.connections.put(entityId, new EntityCoffinData(coffinPos, UUID.randomUUID(), lastInteractionTime));
		this.gameTime = gameTime;
	}

	public void scheduleRemoval(int entityId) {
		scheduledRemovals.add(entityId);
	}

	@Override
	public void clear() {
		this.connections.clear();
		this.scheduledRemovals.clear();
		this.lastLookedAtId = null;
	}

	@Override
	public void render(PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ) {
		this.clearRemovedEntities();
		this.scheduledRemovals.removeIf(id -> true);

		if (!this.minecraft.player.isSpectator()) {
			this.updateLastLookedAtUuid();
		}

		for (Map.Entry<Integer, EntityCoffinData> connectionInfo : this.connections.entrySet()) {
			EntityCoffinData coffinData = connectionInfo.getValue();
			boolean selected = false;
			Integer id = connectionInfo.getKey();
			if (id != null) {
				Entity entity = this.minecraft.level.getEntity(id);
				if (entity != null) {
					if (id.equals(this.lastLookedAtId)) {
						selected = true;
						highlightPos(matrices, vertexConsumers, coffinData.getPos());
						Vec3 entityTextPos = entity.getEyePosition(DebugRenderManager.PARTIAL_TICK);
						renderTextOverPos(matrices, vertexConsumers, entity.getDisplayName().getString(), entityTextPos, 3, TEXT_COLOR);
						renderTextOverPos(
							matrices,
							vertexConsumers,
							"Last Interaction: " + (this.gameTime - coffinData.lastInteraction()),
							entityTextPos,
							2,
							TEXT_COLOR
						);
					}
					drawLine(
						matrices,
						vertexConsumers,
						cameraX, cameraY, cameraZ,
						entity.getEyePosition(DebugRenderManager.PARTIAL_TICK), Vec3.atCenterOf(coffinData.getPos()),
						selected ? SELECTED_CONNECTION_COLOR : CONNECTION_COLOR
					);
				}
			}
		}
	}

	private void updateLastLookedAtUuid() {
		DebugRenderer.getTargetedEntity(this.minecraft.getCameraEntity(), 8).ifPresent(entity -> this.lastLookedAtId = entity.getId());
	}

	private static void highlightPos(PoseStack matrices, MultiBufferSource vertexConsumers, BlockPos pos) {
		DebugRenderer.renderFilledBox(matrices, vertexConsumers, pos, 0.05F, 0.2F, 0.2F, 1.0F, 0.3F);
		renderTextOverPos(matrices, vertexConsumers, "Coffin", pos.getCenter(), 0, TEXT_COLOR);
	}

	private static void drawLine(
		@NotNull PoseStack matrices,
		@NotNull MultiBufferSource vertexConsumers,
		double cameraX,
		double cameraY,
		double cameraZ,
		@NotNull Vec3 start,
		@NotNull Vec3 target,
		int color
	) {
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.debugLineStrip(4D));
		vertexConsumer.addVertex(matrices.last(), (float)(start.x - cameraX), (float)(start.y - cameraY), (float)(start.z - cameraZ)).setColor(color);
		vertexConsumer.addVertex(matrices.last(), (float)(target.x - cameraX), (float)(target.y - cameraY), (float)(target.z - cameraZ)).setColor(color);
	}

	private static void renderTextOverPos(PoseStack matrices, MultiBufferSource vertexConsumers, String string, @NotNull Vec3 pos, int offsetY, int color) {
		double g = pos.y + (double)offsetY * 0.2;
		DebugRenderer.renderFloatingText(matrices, vertexConsumers, string, pos.x, g, pos.z, color, 0.02F, true, 0.0F, true);
	}
}
