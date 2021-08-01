// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.stat.data.ElementListData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.api.Element;
import java.util.Map;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class RandomElementListData implements StatData, RandomStatData, UpdatableRandomStatData
{
    private final Map<Element, NumericStatFormula> damage;
    private final Map<Element, NumericStatFormula> defense;
    
    public RandomElementListData(final ConfigurationSection configurationSection) {
        this.damage = new HashMap<Element, NumericStatFormula>();
        this.defense = new HashMap<Element, NumericStatFormula>();
        Validate.notNull((Object)configurationSection, "Config cannot be null");
        for (final String s : configurationSection.getKeys(false)) {
            final Element value = Element.valueOf(s.toUpperCase());
            if (configurationSection.contains(s + ".damage")) {
                this.damage.put(value, new NumericStatFormula(configurationSection.get(s + ".damage")));
            }
            if (configurationSection.contains(s + ".defense")) {
                this.defense.put(value, new NumericStatFormula(configurationSection.get(s + ".defense")));
            }
        }
    }
    
    public boolean hasDamage(final Element element) {
        return this.damage.containsKey(element);
    }
    
    public boolean hasDefense(final Element element) {
        return this.defense.containsKey(element);
    }
    
    @NotNull
    public NumericStatFormula getDefense(@NotNull final Element key) {
        return this.defense.getOrDefault(key, NumericStatFormula.ZERO);
    }
    
    @NotNull
    public NumericStatFormula getDamage(@NotNull final Element key) {
        return this.damage.getOrDefault(key, NumericStatFormula.ZERO);
    }
    
    public Set<Element> getDefenseElements() {
        return this.defense.keySet();
    }
    
    public Set<Element> getDamageElements() {
        return this.damage.keySet();
    }
    
    public void setDamage(final Element element, final NumericStatFormula numericStatFormula) {
        this.damage.put(element, numericStatFormula);
    }
    
    public void setDefense(final Element element, final NumericStatFormula numericStatFormula) {
        this.defense.put(element, numericStatFormula);
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        final ElementListData elementListData = new ElementListData();
        this.damage.forEach((element, numericStatFormula) -> elementListData.setDamage(element, numericStatFormula.calculate(mmoItemBuilder.getLevel())));
        this.defense.forEach((element2, numericStatFormula2) -> elementListData.setDefense(element2, numericStatFormula2.calculate(mmoItemBuilder.getLevel())));
        return elementListData;
    }
    
    @NotNull
    @Override
    public <T extends StatData> T reroll(@NotNull final ItemStat itemStat, @NotNull final T t, final int n) {
        final ElementListData elementListData = new ElementListData();
        final ElementListData elementListData2 = (ElementListData)t;
        for (final Element element : Element.values()) {
            final NumericStatFormula damage = this.getDamage(element);
            final NumericStatFormula defense = this.getDefense(element);
            final DoubleData doubleData = new DoubleData(elementListData2.getDamage(element));
            final DoubleData doubleData2 = new DoubleData(elementListData2.getDefense(element));
            final DoubleData doubleData3 = damage.reroll(itemStat, doubleData, n);
            final DoubleData doubleData4 = defense.reroll(itemStat, doubleData2, n);
            elementListData.setDamage(element, doubleData3.getValue());
            elementListData.setDefense(element, doubleData4.getValue());
        }
        return (T)elementListData;
    }
}
