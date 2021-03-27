package latokike.latoorigins.common.registry;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.PowerTypeReference;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import latokike.latoorigins.common.LatoOrigins;
import latokike.latoorigins.common.power.BoneMealPower;
import latokike.latoorigins.common.power.ModifySizePower;
import latokike.latoorigins.common.power.SpikedPower;
import latokike.latoorigins.common.power.ModifyPlayerSpawnPower;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class LOPowers {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();
	
	public static final PowerFactory<Power> BONE_MEAL = create(new PowerFactory<>(new Identifier(LatoOrigins.MODID, "bone_meal"), new SerializableData().add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()), data -> (type, player) -> {
		BoneMealPower power = new BoneMealPower(type, player);
		power.setKey((Active.Key) data.get("key"));
		return power;
	}).allowCondition());
	public static final PowerType<Power> PHOTOSYNTHESIS = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "photosynthesis"));
	public static final PowerType<Power> DELICATE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "delicate"));
	
	public static final PowerFactory<Power> MODIFY_SIZE = create(new PowerFactory<>(new Identifier(LatoOrigins.MODID, "modify_size"), new SerializableData().add("scale", SerializableDataType.FLOAT), data -> (type, player) -> new ModifySizePower(type, player, data.getFloat("scale"))).allowCondition());

	public static final PowerFactory<Power> SPIKED = create(new PowerFactory<>(new Identifier(LatoOrigins.MODID, "spiked"), new SerializableData().add("spike_damage", SerializableDataType.INT, 2), data -> (type, player) -> new SpikedPower(type, player, data.getInt("spike_damage"))).allowCondition());
	
	public static final PowerFactory<Power> MODIFY_PLAYER_SPAWN = create(new PowerFactory<>(LatoOrigins.MODID, "modify_player_spawn"), new SerializableData().add("dimension", SerializableDataType.DIMENSION).add("dimension_distance_multiplier", SerializableDataType.FLOAT, 0F).add("spawn_strategy", SerializableDataType.STRING, "default").add("structure", SerializableDataType.registry(ClassUtil.castClass(StructureFeature.class), Registry.STRUCTURE_FEATURE), null).add("respawn_sound", SerializableDataType.SOUND_EVENT, null), data -> (type, player) -> new ModifyPlayerSpawnPower(type, player, (RegistryKey<World>)data.get("dimension"), (int)data.getFloat("dimension_distance_multiplier"), data.getString("spawn_strategy"), data.isPresent("structure") ? (StructureFeature<?>)data.get("structure") : null, (SoundEvent)data.get("respawn_sound"))).allowCondition());
	
	register(new PowerFactory<>(OriginsPlus.identifier("explode"), new SerializableData().add("cooldown", SerializableDataType.INT).add("strength", SerializableDataType.FLOAT, 1.0f).add("break_blocks", SerializableDataType.BOOLEAN, true).add("self_damage", SerializableDataType.FLOAT, 20.0f).add("hud_render", SerializableDataType.HUD_RENDER).add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY).add("ignitable", SerializableDataType.BOOLEAN, true), (data) -> (type, player) -> {ExplodePower power = new ExplodePower(type, player, data.getInt("cooldown"), (HudRender) data.get("hud_render"), data.getFloat("strength"), data.getBoolean("break_blocks"), data.getFloat("self_damage"), data.getBoolean("ignitable")); power.setKey((Active.Key) data.get("key")); return power;}).allowCondition());
	
	public static final PowerType<Power> RIDEABLE_CREATURE = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "rideable_creature"));
	public static final PowerType<Power> ALL_THAT_GLITTERS = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "all_that_glitters"));
	public static final PowerType<Power> PIGLIN_NEUTRALITY = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "piglin_neutrality"));
	public static final PowerType<Power> CROSSBOW_MASTER = new PowerTypeReference<>(new Identifier(LatoOrigins.MODID, "crossbow_master"));
	
	private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
		POWER_FACTORIES.put(factory, factory.getSerializerId());
		return factory;
	}
	
	public static void init() {
		POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
	}
}
