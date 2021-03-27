package latokike.latoorigins.common.registry;

import latokike.latoorigins.common.LatoOrigins;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class LOTags {
	public static final Tag<Item> GOLDEN_ARMOR = TagRegistry.item(new Identifier(LatoOrigins.MODID, "golden_armor"));
	public static final Tag<Item> GOLDEN_TOOLS = TagRegistry.item(new Identifier(LatoOrigins.MODID, "golden_tools"));
	public static final Tag<Item> NETHERITE_GOLD_TOOLS = TagRegistry.item(new Identifier(LatoOrigins.MODID, "netherite_gold_tools"));
	public static final Tag<Item> IRON_TOOLS = TagRegistry.item(new Identifier(LatoOrigins.MODID, "iron_tools"));
	public static final Tag<Block> EDIBLE = TagRegistry.block(new Identifier(LatoOrigins.MODID, "EDIBLE"));
}
