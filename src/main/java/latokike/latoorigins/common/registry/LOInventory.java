package latokike.latoorigins.common.registry;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.common.power.CustomInventoryPower;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class LOInventory {
    private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();
    public static final PowerFactory<Power> CUSTOM_INVENTORY = create(new PowerFactory<>(new Identifier(LatoOrigins.MODID, "custom_inventory"), new SerializableData()
            .add("name", SerializableDataTypes.STRING, "container.inventory")
            .add("drop_on_death", SerializableDataTypes.BOOLEAN, false)
            .add("drop_on_death_filter", ApoliDataTypes.ITEM_CONDITION, null)
            .add("size", SerializableDataTypes.INT, 9)
            .add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data ->
                    (type, player) -> {
                        CustomInventoryPower power = new CustomInventoryPower(type,
                                player,
                                data.getString("name"),
                                data.getInt("size"),
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
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ApoliRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}