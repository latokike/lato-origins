package latokike.latoorigins.common.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class ModifyBehavior extends Power {

	List<EntityType<?>> affectedEntities;
	EntityBehavior desiredBehavior;

	public ModifyBehavior(PowerType<?> type, LivingEntity entity, EntityBehavior desiredBehavior, List<EntityType<?>> affectedEntities) {
		super(type, entity);
		this.affectedEntities = affectedEntities;
		this.desiredBehavior = desiredBehavior;
	}

	public boolean checkEntity(EntityType<?> type) {
		return affectedEntities.contains(type);
	}

	public EntityBehavior getDesiredBehavior() {
		return this.desiredBehavior;
	}

	public enum EntityBehavior {
		HOSTILE,
		NEUTRAL,
		PASSIVE
	}
}