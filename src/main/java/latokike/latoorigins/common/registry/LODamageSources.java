package latokike.latoorigins.common.registry;

import io.github.apace100.calio.mixin.DamageSourceAccessor;
import net.minecraft.entity.damage.DamageSource;

public class LODamageSources {

    public static final DamageSource BOIL_IN_WATER = ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("boil_in_water")).callSetBypassesArmor()).callSetUnblockable();
    public static final DamageSource HORNED = ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("horned")).callSetBypassesArmor()).callSetUnblockable();
}
