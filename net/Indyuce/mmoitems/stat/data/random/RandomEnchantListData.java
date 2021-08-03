// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.EnchantListData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Set;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.Enchants;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.bukkit.enchantments.Enchantment;
import java.util.Map;

public class RandomEnchantListData implements RandomStatData
{
    private final Map<Enchantment, NumericStatFormula> enchants;
    
    public RandomEnchantListData(final ConfigurationSection configurationSection) {
        this.enchants = new HashMap<Enchantment, NumericStatFormula>();
        Validate.notNull((Object)configurationSection, "Config cannot be null");
        for (final String str : configurationSection.getKeys(false)) {
            final Enchantment enchant = Enchants.getEnchant(str);
            Validate.notNull((Object)enchant, "Could not find enchant with key '" + str + "'");
            this.addEnchant(enchant, new NumericStatFormula(configurationSection.get(str)));
        }
    }
    
    public Set<Enchantment> getEnchants() {
        return this.enchants.keySet();
    }
    
    public NumericStatFormula getLevel(final Enchantment enchantment) {
        return this.enchants.get(enchantment);
    }
    
    public void addEnchant(final Enchantment enchantment, final NumericStatFormula numericStatFormula) {
        this.enchants.put(enchantment, numericStatFormula);
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        final EnchantListData enchantListData = new EnchantListData();
        this.enchants.forEach((enchantment, numericStatFormula) -> enchantListData.addEnchant(enchantment, (int)Math.max(numericStatFormula.calculate(mmoItemBuilder.getLevel()), enchantment.getStartLevel())));
        return enchantListData;
    }
}
