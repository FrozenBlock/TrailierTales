package net.frozenblock.trailiertales.networking.packet;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record CoffinRemoveDebugPacket(Integer entityId) implements CustomPacketPayload {
	public static final Type<CoffinRemoveDebugPacket> PACKET_TYPE = new Type<>(
		TrailierConstants.id("debug_coffin_remove")
	);

	public static final StreamCodec<FriendlyByteBuf, CoffinRemoveDebugPacket> CODEC = StreamCodec.ofMember(CoffinRemoveDebugPacket::write, CoffinRemoveDebugPacket::new);

	public CoffinRemoveDebugPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readVarInt());
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeVarInt(this.entityId);
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
