package latokike.latoorigins.common.registry;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.common.power.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LOPowers {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();
	
	public static final PowerFactory<Power> MODIFY_BEHAVIOUR = create(
			new PowerFactory<>(
					new Identifier(LatoOrigins.MODID, "modify_behavior"),
					new SerializableData()
							.add("entity_condition", ApoliDataTypes.ENTITY_CONDITION, null),
					(data) -> (type, player) -> {
						ModifyBehaviorPower power = new ModifyBehaviorPower(type, player,
								(ConditionFactory<LivingEntity>.Instance)data.get("entity_condition"));
							return power;})
					.allowCondition());

	public static final PowerFactory<Power> EXPLODE = create(
			new PowerFactory<>(
					new Identifier(LatoOrigins.MODID, "explode"),
					new SerializableData()
							.add("cooldown", SerializableDataTypes.INT)
							.add("strength", SerializableDataTypes.FLOAT, 1.0f)
							.add("break_blocks", SerializableDataTypes.BOOLEAN, true)
							.add("self_damage", SerializableDataTypes.FLOAT, 20.0f)
							.add("hud_render", ApoliDataTypes.HUD_RENDER)
							.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY)
							.add("ignitable", SerializableDataTypes.BOOLEAN, true),
					(data) -> (type, player) -> {
						ExplodePower power = new ExplodePower(type, player,
								data.getInt("cooldown"),
								(HudRender) data.get("hud_render"),
								data.getFloat("strength"),
								data.getBoolean("break_blocks"),
								data.getFloat("self_damage"),
								data.getBoolean("ignitable"));
								power.setKey((Active.Key)
								data.get("key"));
								return power;})
					.allowCondition());

	public static final PowerFactory<Power> MODIFY_SIZE = create(
			new PowerFactory<>(
					new Identifier(LatoOrigins.MODID, "modify_size"),
					new SerializableData()
							.add("scale", SerializableDataTypes.FLOAT),
					data -> (type, player) ->
							new ModifySizePower(type, player,
									data.getFloat("scale")))
					.allowCondition());

	public static final PowerFactory<Power> SPIKED = create(
			new PowerFactory<>(
					new Identifier(LatoOrigins.MODID, "spiked"),
					new SerializableData()
							.add("spike_damage", SerializableDataTypes.INT, 2),
					data -> (type, player) ->
							new SpikedPower(
									type, player,
									data.getInt("spike_damage")))
					.allowCondition());

	public static final PowerType<Power> RIDEABLE_CREATURE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "rideable_creature"));
	public static final PowerType<Power> IRON_DIET = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "iron_diet"));
	public static final PowerType<Power> URANIUM_BUILT = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "uranium_built"));
	public static final PowerType<Power> COBBLESTONE_SKIN = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "cobblestone_skin"));
	public static final PowerType<Power> NONE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "none"));
	public static final PowerType<Power> TRASHLIKE_APPETITE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "trashlike_appetite"));

	private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
		POWER_FACTORIES.put(factory, factory.getSerializerId());
		return factory;
	}
	
	public static void init() {
		POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ApoliRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}
}
