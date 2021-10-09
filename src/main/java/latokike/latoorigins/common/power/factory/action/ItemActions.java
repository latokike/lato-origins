package latokike.latoorigins.common.power.factory.action;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.Origins;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class ItemActions {

    public static void init() {
        register(new ActionFactory<>(Origins.identifier("example"), new SerializableData()
            .add("amount", SerializableDataTypes.INT),
            (data, stack) -> {
                stack.decrement(data.getInt("amount"));
            }));
    }

    private static void register(ActionFactory<ItemStack> actionFactory) {
        Registry.register(ApoliRegistries.ITEM_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
