package latokike.latoorigins.common.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class SpikedPower extends Power {
    int spikeDamage;
    public SpikedPower(PowerType<?> type, LivingEntity player, int damage) {
        super(type, player);
        this.spikeDamage = damage;
    }

    public int getSpikeDamage() {
        return spikeDamage;
    }
}