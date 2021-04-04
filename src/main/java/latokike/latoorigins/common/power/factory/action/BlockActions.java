package latokike.latoorigins.common.power.factory.action;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.FilterableWeightedList;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.Block;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import latokike.latoorigins.common.LatoOrigins;

import java.util.List;
import java.util.Random;

public class BlockActions {

    @SuppressWarnings("unchecked")
    public static void init() {
        register(new ActionFactory<>(LatoOrigins.identifier("example_one"), new SerializableData()
            .add("block", SerializableDataType.BLOCK),
            (data, block) -> {
                block.getLeft().setBlockState(block.getMiddle(), ((Block)data.get("block")).getDefaultState());
            }));
        register(new ActionFactory<>(LatoOrigins.identifier("example_two"), new SerializableData()
            .add("block", SerializableDataType.BLOCK),
            (data, block) -> {
                block.getLeft().setBlockState(block.getMiddle().offset(block.getRight()), ((Block)data.get("block")).getDefaultState());
            }));
    }

    private static void register(ActionFactory<Triple<World, BlockPos, Direction>> actionFactory) {
        Registry.register(ModRegistries.BLOCK_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
