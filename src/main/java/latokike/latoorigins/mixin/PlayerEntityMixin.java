package latokike.latoorigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.registry.ModDamageSources;
import latokike.latoorigins.common.registry.LOPowers;
import latokike.latoorigins.common.power.SpikedPower;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow public abstract boolean isSpectator();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	// RIDEABLE_ENTITY
	@Inject(method = "interact", at = @At(value = "HEAD"), cancellable = true)
	public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (entity instanceof PlayerEntity && LOPowers.RIDEABLE_CREATURE.isActive(entity)) {
			if (!this.hasPassengers() && !((PlayerEntity)(Object)this).shouldCancelInteraction()) {
				if (!this.world.isClient) {
					((PlayerEntity)(Object)this).startRiding(entity);
				}
				cir.setReturnValue(ActionResult.success(((PlayerEntity)(Object)this).world.isClient));
			} else {
				cir.setReturnValue(ActionResult.FAIL);
			}
		}
	}
	// SPIKED
	@Inject(method = "damage", at = @At(value = "HEAD"))
	public void damage$LatoOrigins(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		List<SpikedPower> spikedPowers = OriginComponent.getPowers(((PlayerEntity)(Object)this), SpikedPower.class);
		if (source.getSource() instanceof LivingEntity && !source.getMagic() && !source.isExplosive() && spikedPowers.size() > 0) {
			int damage = spikedPowers.stream().map(SpikedPower::getSpikeDamage).reduce(Integer::sum).get();
			System.out.println(damage);
			if (((PlayerEntity)(Object)this).getRandom().nextFloat() <= 0.75) {
				source.getSource().damage(DamageSource.thorns(((PlayerEntity)(Object)this)), damage);
			}
		}
	}

	// LAVA_BREATHING
	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo info) {
		if(LOPowers.LAVA_BREATHING.isActive(this)) {
			if(!this.isSubmergedIn(FluidTags.LAVA) && !this.hasStatusEffect(StatusEffects.WATER_BREATHING) && !this.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
				int landGain = this.getNextAirOnLand(0);
				this.setAir(this.getAir() - landGain);
			}
		else if(this.getAir() < this.getMaxAir())
			{
				this.setAir(this.getNextAirOnLand(this.getAir()));
			}
		}
	}
}

// Original Code by UltrusBot