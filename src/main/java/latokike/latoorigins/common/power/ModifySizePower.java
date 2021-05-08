package latokike.latoorigins.common.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import latokike.latoorigins.common.registry.LOScaleTypes;
import net.minecraft.entity.player.PlayerEntity;
import virtuoel.pehkui.api.ScaleData;

public class ModifySizePower extends Power {
	public final float scale;
	
	public ModifySizePower(PowerType<?> type, PlayerEntity player, float scale) {
		super(type, player);
		this.scale = scale;
	}
	
	@Override
	public void onAdded() {
		super.onAdded();
		ScaleData data = LOScaleTypes.MODIFY_SIZE_TYPE.getScaleData(player);
		data.setScale(data.getBaseScale() * scale);
	}
	
	@Override
	public void onRemoved() {
		super.onRemoved();
		ScaleData data = LOScaleTypes.MODIFY_SIZE_TYPE.getScaleData(player);
		data.setScale(data.getBaseScale() / scale);
	}
}

// Original code by MoriyaShiine
