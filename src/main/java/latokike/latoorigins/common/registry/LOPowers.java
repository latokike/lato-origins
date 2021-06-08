package latokike.latoorigins.common.registry;

import java.util.List;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.PowerTypeReference;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.apace100.origins.util.HudRender;
import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.common.power.BoneMealPower;
import latokike.latoorigins.common.power.ModifySizePower;
import latokike.latoorigins.common.power.SpikedPower;
import latokike.latoorigins.common.power.ModifyBehavior;
import latokike.latoorigins.common.power.ModifyBehavior.EntityBehavior;
import latokike.latoorigins.common.power.ExplodePower;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.entity.EntityType;

import java.util.LinkedHashMap;
import java.util.Map;

public class LOPowers {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

	public static void register() {
		register(
				new PowerFactory<>(LatoOrigins.identifier("modify_behavior"),
						new SerializableData()
								.add("behavior", SerializableDataType.enumValue(ModifyBehavior.EntityBehavior.class))
								.add("entities", SerializableDataType.list(SerializableDataType.ENTITY_TYPE)),
						(data) -> (type, player) -> {
							return new ModifyBehavior(type, player, (EntityBehavior) data.get("behavior"),
									(List<EntityType<?>>) data.get("entities"));
						}));
	}

	public static final PowerFactory<Power> BONE_MEAL = create(
			new PowerFactory<>(
				new Identifier(LatoOrigins.MODID, "bone_meal"),
				new SerializableData()
						.add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
				data -> (type, player) -> {
					BoneMealPower power = new BoneMealPower(type, player);
					power.setKey((Active.Key)
							data.get("key"));
					return power; })
				.allowCondition());

	public static final PowerFactory<Power> EXPLODE = create(
			new PowerFactory<>(
					new Identifier(LatoOrigins.MODID, "explode"),
					new SerializableData()
							.add("cooldown", SerializableDataType.INT)
							.add("strength", SerializableDataType.FLOAT, 1.0f)
							.add("break_blocks", SerializableDataType.BOOLEAN, true)
							.add("self_damage", SerializableDataType.FLOAT, 20.0f)
							.add("hud_render", SerializableDataType.HUD_RENDER)
							.add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY)
							.add("ignitable", SerializableDataType.BOOLEAN, true),
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
							.add("scale", SerializableDataType.FLOAT),
					data -> (type, player) ->
							new ModifySizePower(type, player,
									data.getFloat("scale")))
					.allowCondition());

	public static final PowerFactory<Power> SPIKED = create(
			new PowerFactory<>(
					new Identifier(LatoOrigins.MODID, "spiked"),
					new SerializableData()
							.add("spike_damage", SerializableDataType.INT, 2),
					data -> (type, player) ->
							new SpikedPower(
									type, player,
									data.getInt("spike_damage")))
					.allowCondition());

	public static final PowerType<Power> RIDEABLE_CREATURE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "rideable_creature"));
	public static final PowerType<Power> CROSSBOW_MASTER = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "crossbow_master"));
	public static final PowerType<Power> ROCKY_EATER = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "rocky_eater"));
	public static final PowerType<Power> IRON_DIET = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "iron_diet"));
	public static final PowerType<Power> URANIUM_BUILT = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "uranium_built"));
	public static final PowerType<Power> COBBLESTONE_SKIN = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "cobblestone_skin"));
	public static final PowerType<Power> TRASHLIKE_APPETITE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "trashlike_appetite"));
	public static final PowerType<Power> LAVA_BREATHING = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "lava_breathing"));

	private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
		POWER_FACTORIES.put(factory, factory.getSerializerId());
		return factory;
	}
	
	public static void init() {
		POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}
}
