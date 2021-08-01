// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.MMOUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.bukkit.potion.PotionEffectType;

public class RandomPotionEffectData
{
    private final PotionEffectType type;
    private final NumericStatFormula duration;
    private final NumericStatFormula amplifier;
    
    public RandomPotionEffectData(final ConfigurationSection configurationSection) {
        Validate.notNull((Object)configurationSection, "Potion effect config cannot be null");
        Validate.notNull((Object)(this.type = PotionEffectType.getByName(configurationSection.getName().toUpperCase().replace("-", "_").replace(" ", "_"))), "Could not find potion effect with name '" + configurationSection.getName() + "'");
        this.duration = new NumericStatFormula(configurationSection.get("duration"));
        this.amplifier = new NumericStatFormula(configurationSection.get("amplifier"));
    }
    
    public RandomPotionEffectData(final PotionEffectType potionEffectType, final NumericStatFormula numericStatFormula) {
        this(potionEffectType, new NumericStatFormula(MMOUtils.getEffectDuration(potionEffectType) / 20.0, 0.0, 0.0, 0.0), numericStatFormula);
    }
    
    public RandomPotionEffectData(final PotionEffectType type, final NumericStatFormula duration, final NumericStatFormula amplifier) {
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
    }
    
    public PotionEffectType getType() {
        return this.type;
    }
    
    public NumericStatFormula getDuration() {
        return this.duration;
    }
    
    public NumericStatFormula getAmplifier() {
        return this.amplifier;
    }
    
    public PotionEffectData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new PotionEffectData(this.type, this.duration.calculate(mmoItemBuilder.getLevel()), (int)this.amplifier.calculate(mmoItemBuilder.getLevel()));
    }
}
