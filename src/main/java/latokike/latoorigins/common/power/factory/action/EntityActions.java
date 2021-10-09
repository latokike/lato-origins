package latokike.latoorigins.common.power.factory.action;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import latokike.latoorigins.common.LatoOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityActions {

    @SuppressWarnings("unchecked")
    public static void init() {
        register(new ActionFactory<>(LatoOrigins.identifier("sound"), new SerializableData()
            .add("namespace", SerializableDataTypes.STRING, "minecraft")
            .add("sound", SerializableDataTypes.STRING)
            .add("volume", SerializableDataTypes.FLOAT, 1F)
            .add("pitch", SerializableDataTypes.FLOAT, 1F),
            (data, entity) -> {
                if(entity instanceof PlayerEntity) {
                    (entity).world.playSound(null, (entity).getX(), (entity).getY(), (entity).getZ(), new SoundEvent(new Identifier(data.getString("namespace"), data.getString("sound"))),
                	SoundCategory.PLAYERS, data.getFloat("volume"), data.getFloat("pitch"));
                }
            }));

        register(new ActionFactory<>(LatoOrigins.identifier("give_item"), new SerializableData()
                .add("item", SerializableDataTypes.ITEM_STACK),
                (data, entity) -> {
                    if(!entity.world.isClient()) {
                        ItemStack item = (ItemStack)data.get("item");
                        item = item.copy();
                        if(entity instanceof PlayerEntity player) {
                            player.getInventory().offerOrDrop(item);
                        } else {
                            entity.world.spawnEntity(new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), item));
                        }
                    }
                }));
    }
    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
