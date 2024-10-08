package net.frozenblock.trailiertales.networking.packet;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record CoffinRemoveDebugPacket(Integer entityId) implements CustomPacketPayload {
	public static final Type<CoffinRemoveDebugPacket> PACKET_TYPE = new Type<>(
		TTConstants.id("debug_coffin_remove")
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
