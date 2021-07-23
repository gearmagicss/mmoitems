// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.PotionEffectListData;
import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.apache.commons.lang.Validate;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import java.util.List;

public class RandomPotionEffectListData implements RandomStatData
{
    private final List<RandomPotionEffectData> effects;
    
    public RandomPotionEffectListData(final ConfigurationSection configurationSection) {
        this.effects = new ArrayList<RandomPotionEffectData>();
        Validate.notNull((Object)configurationSection, "Config cannot be null");
        for (final String s : configurationSection.getKeys(false)) {
            final ConfigurationSection configurationSection2 = configurationSection.getConfigurationSection(s);
            if (configurationSection2 != null) {
                this.effects.add(new RandomPotionEffectData(configurationSection2));
            }
            else {
                final String string = configurationSection.getString(s);
                if (string == null) {
                    throw new IllegalArgumentException("Config cannot be null");
                }
                final String[] split = string.split(",");
                if (split.length < 1) {
                    throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Incorrect format, expected $e{Effect}: {Duration},{Amplifier}$b instead of $i{0} {1}$b.", new String[] { s, string }));
                }
                final Double doubleParse = SilentNumbers.DoubleParse(split[0]);
                final Integer integerParse = SilentNumbers.IntegerParse(split[1]);
                final PotionEffectType byName = PotionEffectType.getByName(s.toUpperCase().replace("-", "_").replace(" ", "_"));
                if (doubleParse == null || integerParse == null || byName == null) {
                    throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Incorrect format, expected $e{Effect}: {Duration},{Amplifier}$b instead of $i{0} {1}$b.", new String[] { s, string }));
                }
                this.effects.add(new RandomPotionEffectData(byName, new NumericStatFormula(doubleParse), new NumericStatFormula(integerParse)));
            }
        }
    }
    
    public RandomPotionEffectListData(final RandomPotionEffectData... array) {
        this.effects = new ArrayList<RandomPotionEffectData>();
        this.add(array);
    }
    
    public void add(final RandomPotionEffectData... a) {
        this.effects.addAll(Arrays.asList(a));
    }
    
    public List<RandomPotionEffectData> getEffects() {
        return this.effects;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        final PotionEffectListData potionEffectListData = new PotionEffectListData(new PotionEffectData[0]);
        this.effects.forEach(randomPotionEffectData -> potionEffectListData.add(randomPotionEffectData.randomize(mmoItemBuilder)));
        return potionEffectListData;
    }
}
