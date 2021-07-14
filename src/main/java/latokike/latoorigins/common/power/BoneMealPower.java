package latokike.latoorigins.common.power;

import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import latokike.latoorigins.common.network.packet.BoneMealPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class BoneMealPower extends Power implements Active {
	private Key key;
	
	public BoneMealPower(PowerType<?> type, LivingEntity player) {
		super(type, player);
	}
	
	@Override
	public void onUse() {
		if (entity.world.isClient) {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
				BoneMealPacket.send(((BlockHitResult) client.crosshairTarget).getBlockPos());
			}
		}
	}
	
	@Override
	public Key getKey() {
		return key;
	}
	
	@Override
	public void setKey(Active.Key key) {
		this.key = key;
	}
}
