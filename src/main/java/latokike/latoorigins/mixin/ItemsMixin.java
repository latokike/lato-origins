package latokike.latoorigins.mixin;

import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.config.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(at = @At("HEAD"), method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;")
    private static void modifyRegister(Identifier id, Item item, CallbackInfoReturnable<Item> cir) {
        if(!LatoOrigins.configRegistered) {
            AutoConfig.register(OriginsFood.class, Toml4jConfigSerializer::new);
            LatoOrigins.configRegistered = true;
        }
        OriginsFood config = AutoConfig.getConfigHolder(OriginsFood.class).getConfig();
        for (int i = 0; i < config.iron_golem_food.size(); i++) {
            ConfigFoodItem currentItem = config.iron_golem_food.get(i);
            if (id.equals(new Identifier(currentItem.itemId))) {
                ((ItemAccessor) item).setFoodComponent(new FoodComponent.Builder()
                        .hunger(currentItem.hungerShanks)
                        .saturationModifier(currentItem.saturation)
                        .build());
            }
        }

        for (int i = 0; i < config.cobblestonian_food.size(); i++) {
            ConfigFoodItem currentItem = config.cobblestonian_food.get(i);
            if (id.equals(new Identifier(currentItem.itemId))) {
                ((ItemAccessor) item).setFoodComponent(new FoodComponent.Builder()
                        .hunger(currentItem.hungerShanks)
                        .saturationModifier(currentItem.saturation)
                        .build());
            }
        }

        for (int i = 0; i < config.nukelian_food.size(); i++) {
            ConfigFoodItem currentItem = config.nukelian_food.get(i);
            if (id.equals(new Identifier(currentItem.itemId))) {
                ((ItemAccessor) item).setFoodComponent(new FoodComponent.Builder()
                        .hunger(currentItem.hungerShanks)
                        .saturationModifier(currentItem.saturation)
                        .build());
            }
        }

        for (int i = 0; i < config.trashling_food.size(); i++) {
            ConfigFoodItem currentItem = config.trashling_food.get(i);
            if (id.equals(new Identifier(currentItem.itemId))) {
                ((ItemAccessor) item).setFoodComponent(new FoodComponent.Builder()
                        .hunger(currentItem.hungerShanks)
                        .saturationModifier(currentItem.saturation)
                        .build());
            }
        }
    }
}
