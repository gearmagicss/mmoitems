// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.bukkit.Material;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import java.util.HashMap;
import org.bukkit.enchantments.Enchantment;
import java.util.Map;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class EnchantListData implements StatData, Mergeable
{
    private final Map<Enchantment, Integer> enchants;
    
    public EnchantListData() {
        this.enchants = new HashMap<Enchantment, Integer>();
    }
    
    public Set<Enchantment> getEnchants() {
        return this.enchants.keySet();
    }
    
    public int getLevel(@NotNull final Enchantment enchantment) {
        if (!this.enchants.containsKey(enchantment)) {
            return 0;
        }
        return this.enchants.get(enchantment);
    }
    
    public void addEnchant(final Enchantment enchantment, final int i) {
        this.enchants.put(enchantment, i);
    }
    
    public void clear() {
        this.enchants.clear();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof EnchantListData)) {
            return false;
        }
        if (((EnchantListData)o).enchants.size() != this.enchants.size()) {
            return false;
        }
        for (final Enchantment enchantment : this.getEnchants()) {
            if (this.getLevel(enchantment) != ((EnchantListData)o).getLevel(enchantment)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof EnchantListData, "Cannot merge two different stat data types");
        final boolean boolean1 = MMOItems.plugin.getConfig().getBoolean("stat-merging.additive-enchantments", false);
        for (final Enchantment enchantment : ((EnchantListData)statData).getEnchants()) {
            if (boolean1) {
                this.enchants.put(enchantment, ((EnchantListData)statData).getLevel(enchantment) + this.enchants.get(enchantment));
            }
            else {
                this.addEnchant(enchantment, this.enchants.containsKey(enchantment) ? Math.max(((EnchantListData)statData).getLevel(enchantment), this.enchants.get(enchantment)) : ((EnchantListData)statData).getLevel(enchantment));
            }
        }
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        final EnchantListData enchantListData = new EnchantListData();
        for (final Enchantment key : this.enchants.keySet()) {
            enchantListData.addEnchant(key, this.enchants.getOrDefault(key, 0));
        }
        return enchantListData;
    }
    
    @Override
    public boolean isClear() {
        final Iterator<Enchantment> iterator = this.getEnchants().iterator();
        while (iterator.hasNext()) {
            if (this.getLevel(iterator.next()) != 0) {
                return false;
            }
        }
        return true;
    }
    
    public void identifyTrueOriginalEnchantments(@NotNull final MMOItem mmoItem, @NotNull final EnchantListData enchantListData) {
        if (mmoItem.hasData(ItemStats.DISABLE_ENCHANTING) && mmoItem.hasData(ItemStats.DISABLE_REPAIRING)) {
            this.clear();
            return;
        }
        if (!mmoItem.hasData(ItemStats.ENCHANTS)) {
            mmoItem.setData(ItemStats.ENCHANTS, new EnchantListData());
        }
        if (((EnchantListData)mmoItem.getData(ItemStats.ENCHANTS)).getEnchants().size() == 0) {
            enchantListData.merge(this);
            return;
        }
        final EnchantListData enchantListData2 = new EnchantListData();
        mmoItem.hasData(ItemStats.MATERIAL);
        final Material material = ((MaterialData)mmoItem.getData(ItemStats.MATERIAL)).getMaterial();
        for (final Enchantment enchantment : this.getEnchants()) {
            final int level = this.getLevel(enchantment);
            final int level2 = ((EnchantListData)mmoItem.getData(ItemStats.ENCHANTS)).getLevel(enchantment);
            if (level2 <= enchantment.getMaxLevel()) {
                if (!enchantment.getItemTarget().includes(material)) {
                    continue;
                }
                if (level2 >= level) {
                    continue;
                }
                enchantListData2.addEnchant(enchantment, level);
            }
        }
        enchantListData.merge(enchantListData2);
    }
}
