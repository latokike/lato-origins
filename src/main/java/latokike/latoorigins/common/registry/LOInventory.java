package latokike.latoorigins.common.registry;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.common.power.CustomInventoryPower;


import java.util.LinkedHashMap;
import java.util.Map;

public class LOInventory {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();
	public static final PowerFactory<Power> CUSTOM_INVENTORY = create(new PowerFactory<>(new Identifier(LatoOrigins.MODID, "custom_inventory"), new SerializableData()
			.add("name", SerializableDataType.STRING, "container.inventory")
			.add("drop_on_death", SerializableDataType.BOOLEAN, false)
			.add("drop_on_death_filter", SerializableDataType.ITEM_CONDITION, null)
			.add("size", SerializableDataType.INT, 9)
			.add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
			data ->
					(type, player) -> {
						CustomInventoryPower power = new CustomInventoryPower(type, player, data.getString("name"), data.getInt("size"),
								data.getBoolean("drop_on_death"),
								data.isPresent("drop_on_death_filter") ? (ConditionFactory<ItemStack>.Instance) data.get("drop_on_death_filter") :
										itemStack -> true);
						power.setKey((Active.Key)data.get("key"));
						return power;
					})
			.allowCondition());
	
	private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
		POWER_FACTORIES.put(factory, factory.getSerializerId());
		return factory;
	}
	
	public static void init() {
		POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
	}
}
