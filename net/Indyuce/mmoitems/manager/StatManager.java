// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import java.util.Collection;
import java.lang.reflect.Field;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import java.lang.reflect.Modifier;
import net.Indyuce.mmoitems.ItemStats;
import java.util.HashSet;
import java.util.LinkedHashMap;
import net.Indyuce.mmoitems.stat.type.SelfConsumable;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.AttributeStat;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import java.util.Set;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;

public class StatManager
{
    private final Map<String, ItemStat> stats;
    private final Set<DoubleStat> numeric;
    private final Set<AttributeStat> attributeBased;
    private final Set<ItemRestriction> itemRestriction;
    private final Set<ConsumableItemInteraction> consumableActions;
    private final Set<SelfConsumable> selfConsumables;
    
    public StatManager() {
        this.stats = new LinkedHashMap<String, ItemStat>();
        this.numeric = new HashSet<DoubleStat>();
        this.attributeBased = new HashSet<AttributeStat>();
        this.itemRestriction = new HashSet<ItemRestriction>();
        this.consumableActions = new HashSet<ConsumableItemInteraction>();
        this.selfConsumables = new HashSet<SelfConsumable>();
        for (final Field field : ItemStats.class.getFields()) {
            try {
                if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.get(null) instanceof ItemStat) {
                    this.register((ItemStat)field.get(null));
                }
            }
            catch (IllegalArgumentException | IllegalAccessException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Couldn't register stat called " + field.getName());
            }
        }
    }
    
    public Collection<ItemStat> getAll() {
        return this.stats.values();
    }
    
    public Set<AttributeStat> getAttributeStats() {
        return this.attributeBased;
    }
    
    public Set<DoubleStat> getNumericStats() {
        return this.numeric;
    }
    
    public Set<ItemRestriction> getItemRestrictionStats() {
        return this.itemRestriction;
    }
    
    public Set<ConsumableItemInteraction> getConsumableActions() {
        return this.consumableActions;
    }
    
    public Set<SelfConsumable> getSelfConsumables() {
        return this.selfConsumables;
    }
    
    public boolean has(final String s) {
        return this.stats.containsKey(s);
    }
    
    public ItemStat get(final String key) {
        return this.stats.getOrDefault(key, null);
    }
    
    @Deprecated
    public void register(final String s, final ItemStat itemStat) {
        this.register(itemStat);
    }
    
    public void register(final ItemStat itemStat) {
        if (!itemStat.isEnabled()) {
            return;
        }
        this.stats.put(itemStat.getId(), itemStat);
        if (itemStat instanceof DoubleStat && !(itemStat instanceof GemStoneStat) && itemStat.isCompatible(Type.GEM_STONE)) {
            this.numeric.add((DoubleStat)itemStat);
        }
        if (itemStat instanceof AttributeStat) {
            this.attributeBased.add((AttributeStat)itemStat);
        }
        if (itemStat instanceof ItemRestriction) {
            this.itemRestriction.add((ItemRestriction)itemStat);
        }
        if (itemStat instanceof ConsumableItemInteraction) {
            this.consumableActions.add((ConsumableItemInteraction)itemStat);
        }
        if (itemStat instanceof SelfConsumable) {
            this.selfConsumables.add((SelfConsumable)itemStat);
        }
        if (MMOItems.plugin.getTypes() != null) {
            for (final Type type : MMOItems.plugin.getTypes().getAll()) {
                if (itemStat.isCompatible(type)) {
                    type.getAvailableStats().add(itemStat);
                }
            }
        }
    }
}
