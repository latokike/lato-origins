package latokike.latoorigins.config;

public class ConfigFoodItem {
    public String itemId;
    public Integer hungerShanks;
    public Float saturation;

    public ConfigFoodItem(String itemId, Integer hungerShanks, Float saturation) {
        this.itemId = itemId;
        this.hungerShanks = hungerShanks;
        this.saturation = saturation;
    }
}