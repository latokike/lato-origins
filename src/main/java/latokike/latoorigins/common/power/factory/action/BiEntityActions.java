package latokike.latoorigins.common.power.factory.action;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import latokike.latoorigins.common.LatoOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class BiEntityActions {

    @SuppressWarnings("unchecked")
    public static void init() {
        register(new ActionFactory<>(LatoOrigins.identifier("parrot_tame"), new SerializableData(),
                (data, entities) -> {
                    if(entities.getRight() instanceof TameableShoulderEntity && entities.getLeft() instanceof PlayerEntity) {
                        if(!((TameableShoulderEntity)entities.getRight()).isTamed()) {
                            ((TameableShoulderEntity)entities.getRight()).setOwner((PlayerEntity)entities.getLeft());
                        }
                    }
                }));
    }

    private static void register(ActionFactory<Pair<Entity, Entity>> actionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}