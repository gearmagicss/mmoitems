// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template;

import java.util.Iterator;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.manager.TemplateManager;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Random;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;

public class TemplateModifier
{
    private final String id;
    private final double chance;
    private final double weight;
    private final Map<ItemStat, RandomStatData> data;
    private final NameModifier nameModifier;
    private static final Random random;
    
    public TemplateModifier(final ConfigurationSection configurationSection) {
        this(null, configurationSection);
    }
    
    public TemplateModifier(final TemplateManager templateManager, final ConfigurationSection configurationSection) {
        Validate.notNull((Object)configurationSection, "Could not read config");
        this.id = configurationSection.getName().toLowerCase().replace("_", "-");
        if (!configurationSection.contains("stats")) {
            Validate.notNull((Object)templateManager, "Cannot create a private modifier outside an item template");
            Validate.isTrue(templateManager.hasModifier(this.id), "Could not find public modifier with ID '" + this.id + "'");
            final TemplateModifier modifier = templateManager.getModifier(this.id);
            this.chance = Math.max(Math.min(configurationSection.getDouble("chance", modifier.chance), 1.0), 0.0);
            this.weight = configurationSection.getDouble("weight", modifier.weight);
            this.nameModifier = modifier.nameModifier;
            this.data = modifier.data;
            return;
        }
        this.data = new HashMap<ItemStat, RandomStatData>();
        this.chance = Math.max(Math.min(configurationSection.getDouble("chance", 1.0), 1.0), 0.0);
        this.weight = configurationSection.getDouble("weight");
        Validate.isTrue(this.chance > 0.0, "Chance must be greater than 0 otherwise useless");
        this.nameModifier = (configurationSection.contains("suffix") ? new NameModifier(NameModifier.ModifierType.SUFFIX, configurationSection.get("suffix")) : (configurationSection.contains("prefix") ? new NameModifier(NameModifier.ModifierType.PREFIX, configurationSection.get("prefix")) : null));
        Validate.notNull((Object)configurationSection.getConfigurationSection("stats"), "Could not find base item data");
        for (final String str : configurationSection.getConfigurationSection("stats").getKeys(false)) {
            try {
                final String replace = str.toUpperCase().replace("-", "_");
                Validate.isTrue(MMOItems.plugin.getStats().has(replace), "Could not find stat with ID '" + replace + "'");
                final ItemStat value = MMOItems.plugin.getStats().get(replace);
                this.data.put(value, value.whenInitialized(configurationSection.get("stats." + str)));
            }
            catch (IllegalArgumentException ex) {
                if (ex.getMessage().isEmpty()) {
                    continue;
                }
                MMOItems.plugin.getLogger().log(Level.INFO, "An error occured while trying to load item gen modifier " + this.id + ": " + ex.getMessage());
            }
        }
    }
    
    public String getId() {
        return this.id;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public Map<ItemStat, RandomStatData> getItemData() {
        return this.data;
    }
    
    public NameModifier getNameModifier() {
        return this.nameModifier;
    }
    
    public boolean hasNameModifier() {
        return this.nameModifier != null;
    }
    
    public boolean rollChance() {
        return TemplateModifier.random.nextDouble() < this.chance;
    }
    
    static {
        random = new Random();
    }
}
