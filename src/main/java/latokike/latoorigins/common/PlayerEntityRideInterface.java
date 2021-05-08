package latokike.latoorigins.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface PlayerEntityRideInterface {
    ActionResult ride(PlayerEntity player, Hand hand);
}

// Original Code by UltrusBot