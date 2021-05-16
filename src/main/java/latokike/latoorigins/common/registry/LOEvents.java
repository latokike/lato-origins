package latokike.latoorigins.common.registry;

import java.util.Optional;
import java.util.function.Consumer;

import latokike.latoorigins.common.registry.events.EntityInteractCallback;
import io.github.apace100.origins.component.OriginComponent;
import latokike.latoorigins.common.power.ExplodePower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class LOEvents {
    public static void init() {
        EntityInteractCallback.EVENT.register((player, hand, entity) -> {
            if (entity instanceof PlayerEntity) {
                PlayerEntity affectedPlayer = (PlayerEntity) entity;
                if (OriginComponent.hasPower(affectedPlayer, ExplodePower.class)) {
                    ExplodePower power = OriginComponent.getPowers(affectedPlayer, ExplodePower.class).get(0);

                    if (power.isIgnitable()) {
                        ItemStack stack = player.getStackInHand(hand);
                        if (stack.getItem() == Items.FLINT_AND_STEEL) {
                            if (!player.isCreative()) {
                                player.world.playSound(player, player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F,
                                        player.world.random.nextFloat() * 0.4F + 0.8F);
                                if (!player.world.isClient) {
                                    power.onUse();
                                    stack.damage(1, (LivingEntity) player, (Consumer) ((playerEntity) -> {
                                        player.sendToolBreakStatus(hand);
                                    }));
                                }
                            }

                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }

            return ActionResult.PASS;
        });
    }
}
