package latokike.latoorigins.common.registry;

import latokike.latoorigins.common.LatoOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.*;

import java.util.Map;

public class LOScaleTypes {
	private static final ScaleType[] MODIFY_SIZE_TYPES = {ScaleType.WIDTH, ScaleType.HEIGHT, ScaleType.DROPS};

	public static final ScaleType MODIFY_SIZE_TYPE = register(ScaleRegistries.SCALE_TYPES, "modify_size", ScaleType.Builder.create().build());

	public static final ScaleModifier MODIFY_SIZE_MODIFIER = register(ScaleRegistries.SCALE_MODIFIERS, "modify_size", new ScaleModifier() {
		@Override
		public float modifyScale(final ScaleData scaleData, float modifiedScale, final float delta) {
			return MODIFY_SIZE_TYPE.getScaleData(scaleData.getEntity()).getScale(delta) * modifiedScale;
		}
	});

	private static <T> T register(Map<Identifier, T> registry, String name, T entry) {
		return ScaleRegistries.register(registry, new Identifier(LatoOrigins.MODID, name), entry);
	}

	public static void init() {
		MODIFY_SIZE_TYPE.getScaleChangedEvent().register(event -> {
			Entity entity = event.getEntity();
			if (entity != null) {
				boolean onGround = entity.isOnGround();
				entity.calculateDimensions();
				entity.setOnGround(onGround);
				for (ScaleType type : ScaleRegistries.SCALE_TYPES.values()) {
					ScaleData data = type.getScaleData(entity);
					if (data.getBaseValueModifiers().contains(MODIFY_SIZE_MODIFIER)) {
						data.markForSync(true);
					}
				}
			}
		});
		for (ScaleType type : MODIFY_SIZE_TYPES) {
			type.getDefaultBaseValueModifiers().add(MODIFY_SIZE_MODIFIER);
		}
	}
}
