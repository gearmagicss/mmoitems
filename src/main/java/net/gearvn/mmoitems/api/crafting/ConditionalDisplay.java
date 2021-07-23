// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import org.bukkit.configuration.ConfigurationSection;

public class ConditionalDisplay
{
    private final String positive;
    private final String negative;
    
    public ConditionalDisplay(final String positive, final String negative) {
        this.positive = positive;
        this.negative = negative;
    }
    
    public ConditionalDisplay(final ConfigurationSection configurationSection) {
        this(configurationSection.getString("positive"), configurationSection.getString("negative"));
    }
    
    public String getPositive() {
        return this.positive;
    }
    
    public String getNegative() {
        return this.negative;
    }
    
    public void setup(final ConfigurationSection configurationSection) {
        configurationSection.set("positive", (Object)this.positive);
        configurationSection.set("negative", (Object)this.negative);
    }
}
