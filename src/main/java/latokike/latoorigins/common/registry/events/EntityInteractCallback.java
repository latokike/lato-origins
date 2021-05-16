package latokike.latoorigins.common.registry.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface EntityInteractCallback {
	Event<EntityInteractCallback> EVENT = EventFactory.createArrayBacked(EntityInteractCallback.class,
			(listeners) -> (player, hand, entity) -> {
				for(EntityInteractCallback listener : listeners) {
					ActionResult result = listener.onInteract(player, hand, entity);
					
					if(result != ActionResult.PASS) {
						return result;
					}
				}
				
				return ActionResult.PASS;
			});

	ActionResult onInteract(PlayerEntity player, Hand hand, Entity entity);
}