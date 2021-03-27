package com.github.originsplus.power;

import java.util.function.Consumer;

import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class ExplodePower extends ActiveCooldownPower {

	float explosionStrength;
	boolean shouldBreakBlocks;
	float selfDamage;
	boolean ignitable;

	public ExplodePower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender,
			float explosionStrength, boolean shouldBreakBlocks, float selfDamage, boolean ignitable) {
		super(type, player, cooldownDuration, hudRender, null);
		
		this.explosionStrength = explosionStrength;
		this.shouldBreakBlocks = shouldBreakBlocks;
		this.selfDamage = selfDamage;
		this.ignitable = ignitable;
	}
	
	@Override
	public void onUse() {
		if (!player.world.isClient) {
			if (canUse()) {
				explode();
				use();
			}
		}
	}
	
	private void explode() {
		DestructionType type = shouldBreakBlocks ? DestructionType.BREAK : DestructionType.NONE;
		
		player.world.createExplosion(player, player.getX(), player.getY(), player.getZ(), explosionStrength, type);
	
		player.damage(DamageSource.explosion(player), selfDamage);
	}
	
	public boolean isIgnitable() {
		return ignitable;
	}
}
