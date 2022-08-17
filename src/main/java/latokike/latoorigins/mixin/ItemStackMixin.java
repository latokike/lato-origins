package latokike.latoorigins.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

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
	
	public abstract boolean damage(int amount, Random random, @Nullable ServerPlayerEntity player);
}

// Original Code by MoriyaShiine