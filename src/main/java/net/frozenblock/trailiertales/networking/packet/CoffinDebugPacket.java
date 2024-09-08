package net.frozenblock.trailiertales.networking.packet;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record CoffinDebugPacket(int entityId, long lastInteractionTime, BlockPos coffinPos, long gameTime) implements CustomPacketPayload {
	public static final Type<CoffinDebugPacket> PACKET_TYPE = new Type<>(
		TTConstants.id("debug_coffin")
	);

	public static final StreamCodec<FriendlyByteBuf, CoffinDebugPacket> CODEC = StreamCodec.ofMember(CoffinDebugPacket::write, CoffinDebugPacket::new);

	public CoffinDebugPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readVarInt(), buf.readLong(), buf.readBlockPos(), buf.readLong());
	}

	public void write(@NotNull FriendlyByteBuf buf) {
		buf.writeVarInt(this.entityId);
		buf.writeLong(this.lastInteractionTime);
		buf.writeBlockPos(this.coffinPos);
		buf.writeLong(this.gameTime);
	}

	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
