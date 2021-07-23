// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.Element;
import java.util.Map;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class ElementListData implements StatData, Mergeable
{
    @NotNull
    private final Map<Element, Double> damage;
    @NotNull
    private final Map<Element, Double> defense;
    
    public double getDefense(final Element key) {
        return this.defense.getOrDefault(key, 0.0);
    }
    
    public double getDamage(final Element key) {
        return this.damage.getOrDefault(key, 0.0);
    }
    
    public Set<Element> getDefenseElements() {
        return this.defense.keySet();
    }
    
    public Set<Element> getDamageElements() {
        return this.damage.keySet();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ElementListData)) {
            return false;
        }
        if (((ElementListData)o).damage.size() != this.damage.size() || ((ElementListData)o).defense.size() != this.defense.size()) {
            return false;
        }
        for (final Element element : Element.values()) {
            final double damage = this.getDamage(element);
            final double defense = this.getDefense(element);
            final double damage2 = ((ElementListData)o).getDamage(element);
            final double defense2 = ((ElementListData)o).getDefense(element);
            if (damage != damage2 || defense != defense2) {
                return false;
            }
        }
        return true;
    }
    
    public ElementListData() {
        this.damage = new HashMap<Element, Double>();
        this.defense = new HashMap<Element, Double>();
    }
    
    public void setDamage(final Element element, final double d) {
        if (d == 0.0) {
            this.damage.remove(element);
        }
        else {
            this.damage.put(element, d);
        }
    }
    
    public void setDefense(final Element element, final double d) {
        if (d == 0.0) {
            this.defense.remove(element);
        }
        else {
            this.defense.put(element, d);
        }
    }
    
    public int total() {
        return this.damage.size() + this.defense.size();
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof ElementListData, "Cannot merge two different stat data types");
        final ElementListData elementListData = (ElementListData)statData;
        for (final Element key : elementListData.damage.keySet()) {
            this.damage.put(key, elementListData.damage.get(key) + this.damage.getOrDefault(key, 0.0));
        }
        for (final Element key2 : elementListData.defense.keySet()) {
            this.defense.put(key2, elementListData.defense.get(key2) + this.defense.getOrDefault(key2, 0.0));
        }
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        final ElementListData elementListData = new ElementListData();
        for (final Element key : this.damage.keySet()) {
            elementListData.setDamage(key, this.damage.getOrDefault(key, 0.0));
        }
        for (final Element key2 : this.defense.keySet()) {
            elementListData.setDefense(key2, this.defense.getOrDefault(key2, 0.0));
        }
        return elementListData;
    }
    
    @Override
    public boolean isClear() {
        return this.getDamageElements().size() == 0 && this.getDefenseElements().size() == 0;
    }
}
