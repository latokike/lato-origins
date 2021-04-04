package latokike.latoorigins.common.power.factory.action;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.*;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import latokike.latoorigins.common.LatoOrigins;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EntityActions {

    @SuppressWarnings("unchecked")
    public static void init() {
        register(new ActionFactory<>(LatoOrigins.identifier("sound"), new SerializableData()
            .add("namespace", SerializableDataType.STRING, "minecraft")
            .add("sound", SerializableDataType.STRING)
            .add("volume", SerializableDataType.FLOAT, 1F)
            .add("pitch", SerializableDataType.FLOAT, 1F),
            (data, entity) -> {
                if(entity instanceof PlayerEntity) {
                    (entity).world.playSound((PlayerEntity) null, (entity).getX(), (entity).getY(), (entity).getZ(), new SoundEvent(new Identifier(data.getString("namespace"), data.getString("sound"))),
                	SoundCategory.PLAYERS, data.getFloat("volume"), data.getFloat("pitch"));
                }
            }));
    }
    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
