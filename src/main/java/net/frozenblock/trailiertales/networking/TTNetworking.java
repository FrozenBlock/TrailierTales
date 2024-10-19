package net.frozenblock.trailiertales.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.frozenblock.trailiertales.networking.packet.CoffinDebugPacket;
import net.frozenblock.trailiertales.networking.packet.CoffinRemoveDebugPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class TTNetworking {
	public static void init() {
		PayloadTypeRegistry<RegistryFriendlyByteBuf> registry = PayloadTypeRegistry.playS2C();
		registry.register(CoffinDebugPacket.PACKET_TYPE, CoffinDebugPacket.CODEC);
		registry.register(CoffinRemoveDebugPacket.PACKET_TYPE, CoffinRemoveDebugPacket.CODEC);
	}
}
