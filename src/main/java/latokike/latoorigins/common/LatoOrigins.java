package latokike.latoorigins.common;

import latokike.latoorigins.common.network.packet.BoneMealPacket;
import latokike.latoorigins.common.registry.LOConditions;
import latokike.latoorigins.common.registry.LOPowers;
import latokike.latoorigins.common.registry.LOScaleTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class LatoOrigins implements ModInitializer {
	public static final String MODID = "latoorigins";
	
	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(BoneMealPacket.ID, BoneMealPacket::handle);
		LOScaleTypes.init();
		LOPowers.init();
		LOConditions.init();
	}
}
