// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.RestoreData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;

public class RandomRestoreData implements RandomStatData
{
    private final NumericStatFormula health;
    private final NumericStatFormula food;
    private final NumericStatFormula saturation;
    
    public RandomRestoreData(final ConfigurationSection configurationSection) {
        Validate.notNull((Object)configurationSection, "Could not load restore config");
        this.health = (configurationSection.contains("health") ? new NumericStatFormula(configurationSection) : NumericStatFormula.ZERO);
        this.food = (configurationSection.contains("food") ? new NumericStatFormula(configurationSection) : NumericStatFormula.ZERO);
        this.saturation = (configurationSection.contains("saturation") ? new NumericStatFormula(configurationSection) : NumericStatFormula.ZERO);
    }
    
    public NumericStatFormula getHealth() {
        return this.health;
    }
    
    public NumericStatFormula getFood() {
        return this.food;
    }
    
    public NumericStatFormula getSaturation() {
        return this.saturation;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new RestoreData(this.health.calculate(mmoItemBuilder.getLevel()), this.food.calculate(mmoItemBuilder.getLevel()), this.saturation.calculate(mmoItemBuilder.getLevel()));
    }
}
