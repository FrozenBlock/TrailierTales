package net.frozenblock.trailiertales.networking.packet;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record CoffinDebugPacket(Integer entityId, Integer tickCount, Vec3 entityPos, Vec3 coffinPos) implements CustomPacketPayload {
	public static final Type<CoffinDebugPacket> PACKET_TYPE = new Type<>(
		TrailierConstants.id("debug_coffin")
	);

	public static final StreamCodec<FriendlyByteBuf, CoffinDebugPacket> CODEC = StreamCodec.ofMember(CoffinDebugPacket::write, CoffinDebugPacket::new);

	public CoffinDebugPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readVarInt(), buf.readInt(), buf.readVec3(), buf.readVec3());
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeVarInt(this.entityId);
		buf.writeInt(this.tickCount);
		buf.writeVec3(this.entityPos);
		buf.writeVec3(this.coffinPos);
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
