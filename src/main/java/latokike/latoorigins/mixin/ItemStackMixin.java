package latokike.latoorigins.mixin;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import latokike.latoorigins.common.registry.LOPowers;
import latokike.latoorigins.common.registry.LOTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Environment(EnvType.CLIENT)
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER = new EntityAttributeModifier("Origin modifier", 2.5, EntityAttributeModifier.Operation.ADDITION);
	@Environment(EnvType.CLIENT)
	private static final EntityAttributeModifier ARMOR_MODIFIER_0 = new EntityAttributeModifier("Origin modifier", 1, EntityAttributeModifier.Operation.ADDITION);
	@Environment(EnvType.CLIENT)
	private static final EntityAttributeModifier ARMOR_MODIFIER_1 = new EntityAttributeModifier("Origin modifier", 2, EntityAttributeModifier.Operation.ADDITION);
	@Environment(EnvType.CLIENT)
	private static final EntityAttributeModifier ARMOR_MODIFIER_2 = new EntityAttributeModifier("Origin modifier", 0.75, EntityAttributeModifier.Operation.MULTIPLY_BASE);
	@Environment(EnvType.CLIENT)
	private static final EntityAttributeModifier ARMOR_MODIFIER_3 = new EntityAttributeModifier("Origin modifier", 0.75, EntityAttributeModifier.Operation.MULTIPLY_BASE);
	@Environment(EnvType.CLIENT)
	private static final EntityAttributeModifier MOVEMENT_SPEED_MODIFIER = new EntityAttributeModifier("Origin modifier", 0.08, EntityAttributeModifier.Operation.MULTIPLY_BASE);
	
	@Shadow
	public abstract Item getItem();
	
	@Shadow
	public abstract boolean damage(int amount, Random random, @Nullable ServerPlayerEntity player);
	
	@Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
	private <T extends LivingEntity> void damage(int amount, T entity, Consumer<T> breakCallback, CallbackInfo callbackInfo) {
		if (!entity.world.isClient && LOPowers.ALL_THAT_GLITTERS.get(entity) != null && !(entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative())) {
			if (getItem() instanceof ToolItem) {
				if (LOTags.GOLDEN_TOOLS.contains(getItem())) {
					if (entity.world.random.nextFloat() < 15 / 16f) {
						callbackInfo.cancel();
					}
				}
			}
			if (getItem() instanceof ArmorItem) {
				if (LOTags.GOLDEN_ARMOR.contains(getItem())) {
					if (entity.world.random.nextFloat() < 3 / 4f) {
						callbackInfo.cancel();
					}
				}
			}
		}
		if (!entity.world.isClient && LOPowers.WEAK_ARMOR.get(entity) != null && !(entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative())) {
			if (getItem() instanceof ArmorItem) {
				if (LOTags.ARMOR.contains(getItem())) {
					if (entity.world.random.nextFloat() < 3 / 4f) {
						callbackInfo.cancel();
					}
				}
			}
		}
	}

	@Environment(EnvType.CLIENT)
	@Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;"))
	private Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot equipmentSlot) {
		Multimap<EntityAttribute, EntityAttributeModifier> multimap = LinkedHashMultimap.create(stack.getAttributeModifiers(equipmentSlot));
		if (LOPowers.ALL_THAT_GLITTERS.get(MinecraftClient.getInstance().player) != null && !multimap.isEmpty()) {
			if (getItem() instanceof ToolItem && LOTags.GOLDEN_TOOLS.contains(getItem())) {
				multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER);
			}
			if (getItem() instanceof ArmorItem) {
				if (getItem() instanceof ArmorItem && LOTags.GOLDEN_ARMOR.contains(getItem())) {
					multimap.put(EntityAttributes.GENERIC_ARMOR, (((ArmorItem) getItem()).getSlotType() == EquipmentSlot.CHEST || ((ArmorItem) getItem()).getSlotType() == EquipmentSlot.LEGS) ? ARMOR_MODIFIER_1 : ARMOR_MODIFIER_0);
				}
			}
		}
		// WEAK_ARMOR
		if (LOPowers.WEAK_ARMOR.get(MinecraftClient.getInstance().player) != null && !multimap.isEmpty()) {
			if (getItem() instanceof ArmorItem && LOTags.ARMOR.contains(getItem())) {
				multimap.put(EntityAttributes.GENERIC_ARMOR, (((ArmorItem) getItem()).getSlotType() == EquipmentSlot.CHEST || ((ArmorItem) getItem()).getSlotType() == EquipmentSlot.LEGS) ? ARMOR_MODIFIER_2 : ARMOR_MODIFIER_3);
			}
		}
		return multimap;
	}
}

// Original code by MoriyaShiine
