package latokike.latoorigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import latokike.latoorigins.common.power.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveTargetGoal.class)
public abstract class ModifyBehaviorMixin extends TrackTargetGoal {
    @Shadow protected LivingEntity targetEntity;

    public ModifyBehaviorMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    public void start(CallbackInfo ci) {
        if (PowerHolderComponent.getPowers(targetEntity, ModifyBehaviorPower.class).stream().anyMatch(modifyBehaviourPower -> modifyBehaviourPower.apply(this.mob))) {
            this.stop();
            ci.cancel();
        }
    }
}
