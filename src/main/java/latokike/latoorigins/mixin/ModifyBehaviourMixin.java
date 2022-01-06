package latokike.latoorigins.mixin

import io.github.apace100.apoli.component.PowerHolderComponent;
import latokike.latoorigins.common.power.ModifyBehaviourPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TargetGoal.class)
public abstract class TargetGoalMixin extends TrackTargetGoal {
    @Shadow protected LivingEntity targetEntity;

    public TargetGoalMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    public void start(CallbackInfo ci) {
        if (PowerHolderComponent.getPowers(targetEntity, ModifyBehaviourPower.class).stream().anyMatch(modifyBehaviourPower -> modifyBehaviourPower.apply(this.mob))) {
            this.stop();
            ci.cancel();
        }
    }
}
