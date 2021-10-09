package latokike.latoorigins.common.power.factory.action;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import latokike.latoorigins.common.LatoOrigins;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

public class BlockActions {

    @SuppressWarnings("unchecked")
    public static void init() {
        register(new ActionFactory<>(LatoOrigins.identifier("example_one"), new SerializableData()
            .add("block", SerializableDataTypes.BLOCK),
            (data, block) -> {
                block.getLeft().setBlockState(block.getMiddle(), ((Block)data.get("block")).getDefaultState());
            }));
        register(new ActionFactory<>(LatoOrigins.identifier("example_two"), new SerializableData()
            .add("block", SerializableDataTypes.BLOCK),
            (data, block) -> {
                block.getLeft().setBlockState(block.getMiddle().offset(block.getRight()), ((Block)data.get("block")).getDefaultState());
            }));
    }

    private static void register(ActionFactory<Triple<World, BlockPos, Direction>> actionFactory) {
        Registry.register(ApoliRegistries.BLOCK_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
