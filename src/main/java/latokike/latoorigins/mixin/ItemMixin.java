package latokike.latoorigins.mixin;

import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.config.ConfigFoodItem;
import latokike.latoorigins.config.OriginsFood;
import latokike.latoorigins.common.registry.LOPowers;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemConvertible {

    @Shadow public abstract boolean isFood();

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private void preventEatingFood(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (this.isFood()) {
            if (!LatoOrigins.configRegistered) {
                AutoConfig.register(OriginsFood.class, Toml4jConfigSerializer::new);
                LatoOrigins.configRegistered = true;
            }
            OriginsFood config = AutoConfig.getConfigHolder(OriginsFood.class).getConfig();
            ItemStack stackInHand = user.getStackInHand(hand);
            for (int i = 0; i < config.iron_golem_food.size(); i++) {
                ConfigFoodItem currentItem = config.iron_golem_food.get(i);
                if (currentItem.itemId.equals(Registry.ITEM.getId(stackInHand.getItem()).toString())) {
                    if (!LOPowers.IRON_DIET.isActive(user) && !LOPowers.TRASHLIKE_APPETITE.isActive(user)) {
                        cir.setReturnValue(TypedActionResult.fail(stackInHand));
                    }
                }
            }

            for (int i = 0; i < config.cobblestonian_food.size(); i++) {
                ConfigFoodItem currentItem = config.cobblestonian_food.get(i);
                if (currentItem.itemId.equals(Registry.ITEM.getId(stackInHand.getItem()).toString())) {
                    if (!LOPowers.ROCKY_EATER.isActive(user) && !LOPowers.TRASHLIKE_APPETITE.isActive(user)) {
                        cir.setReturnValue(TypedActionResult.fail(stackInHand));
                    }
                }
            }

            for (int i = 0; i < config.nukelian_food.size(); i++) {
                ConfigFoodItem currentItem = config.nukelian_food.get(i);
                if (currentItem.itemId.equals(Registry.ITEM.getId(stackInHand.getItem()).toString())) {
                    if (!LOPowers.URANIUM_BUILT.isActive(user) && !LOPowers.TRASHLIKE_APPETITE.isActive(user)) {
                        cir.setReturnValue(TypedActionResult.fail(stackInHand));
                    }
                }
            }

            for (int i = 0; i < config.trashling_food.size(); i++) {
                ConfigFoodItem currentItem = config.trashling_food.get(i);
                if (currentItem.itemId.equals(Registry.ITEM.getId(stackInHand.getItem()).toString())) {
                    if (!LOPowers.TRASHLIKE_APPETITE.isActive(user)) {
                        cir.setReturnValue(TypedActionResult.fail(stackInHand));
                    }
                }
            }
        }
    }
}