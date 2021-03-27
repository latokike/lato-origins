package com.github.originsplus.power;

import java.util.List;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class ModifyBehavior extends Power {

	List<EntityType<?>> affectedEntities;
	EntityBehavior desiredBehavior;
	
	public ModifyBehavior(PowerType<?> type, PlayerEntity player, EntityBehavior desiredBehavior, List<EntityType<?>> affectedEntities) {
		super(type, player);
		this.affectedEntities = affectedEntities;
		this.desiredBehavior = desiredBehavior;
	}
	
	public boolean checkEntity(EntityType<?> type) {
		return affectedEntities.contains(type);
	}
	
	public EntityBehavior getDesiredBehavior() {
		return this.desiredBehavior;
	}
	
	public static enum EntityBehavior {
		HOSTILE,
		NEUTRAL,
		PASSIVE
	}

}
