// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.mmoitem;

import net.Indyuce.mmoitems.MMOItems;
import java.util.HashSet;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.api.UpgradeTemplate;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import net.Indyuce.mmoitems.ItemStats;
import java.util.Collection;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.Enchants;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Set;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import java.util.HashMap;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.ItemReference;

public class MMOItem implements ItemReference
{
    private final Type type;
    private final String id;
    @NotNull
    private final Map<ItemStat, StatData> stats;
    @NotNull
    final Map<String, StatHistory> mergeableStatHistory;
    @Nullable
    String asGemColor;
    @NotNull
    UUID asGemUUID;
    
    public MMOItem(final Type type, final String id) {
        this.stats = new HashMap<ItemStat, StatData>();
        this.mergeableStatHistory = new HashMap<String, StatHistory>();
        this.asGemUUID = UUID.randomUUID();
        this.type = type;
        this.id = id;
    }
    
    @Override
    public Type getType() {
        return this.type;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    public void mergeData(@NotNull final ItemStat itemStat, @NotNull final StatData statData, @Nullable final UUID uuid) {
        if (statData instanceof Mergeable) {
            final StatHistory from = StatHistory.from(this, itemStat);
            if (uuid != null) {
                from.registerGemstoneData(uuid, statData);
            }
            else {
                from.registerExternalData(statData);
            }
            this.setData(itemStat, from.recalculate(this.getUpgradeLevel()));
        }
        else {
            this.setData(itemStat, statData);
        }
    }
    
    public void setData(@NotNull final ItemStat itemStat, @NotNull final StatData statData) {
        this.stats.put(itemStat, statData);
    }
    
    public void replaceData(@NotNull final ItemStat key, @NotNull final StatData value) {
        this.stats.replace(key, value);
    }
    
    public void removeData(@NotNull final ItemStat itemStat) {
        this.stats.remove(itemStat);
    }
    
    public StatData getData(@NotNull final ItemStat itemStat) {
        return this.stats.get(itemStat);
    }
    
    public boolean hasData(@NotNull final ItemStat itemStat) {
        return this.stats.get(itemStat) != null;
    }
    
    @NotNull
    public Set<ItemStat> getStats() {
        return this.stats.keySet();
    }
    
    @NotNull
    public ItemStackBuilder newBuilder() {
        return new ItemStackBuilder(this);
    }
    
    public MMOItem clone() {
        final MMOItem mmoItem = new MMOItem(this.type, this.id);
        for (final ItemStat itemStat : this.stats.keySet()) {
            mmoItem.stats.put(itemStat, this.stats.get(itemStat));
            final StatHistory statHistory = this.getStatHistory(itemStat);
            if (statHistory != null) {
                mmoItem.setStatHistory(itemStat, statHistory.clone(mmoItem));
            }
        }
        return mmoItem;
    }
    
    @Nullable
    public StatHistory getStatHistory(@NotNull final ItemStat itemStat) {
        if (itemStat instanceof Enchants) {
            return this.mergeableStatHistory.getOrDefault(itemStat.getNBTPath(), StatHistory.from(this, itemStat, true));
        }
        try {
            return this.mergeableStatHistory.get(itemStat.getNBTPath());
        }
        catch (ClassCastException ex) {
            return null;
        }
    }
    
    @NotNull
    public ArrayList<StatHistory> getStatHistories() {
        return new ArrayList<StatHistory>(this.mergeableStatHistory.values());
    }
    
    public void setStatHistory(@NotNull final ItemStat itemStat, @NotNull final StatHistory statHistory) {
        this.mergeableStatHistory.put(itemStat.getNBTPath(), statHistory);
    }
    
    public void upgrade() {
        this.getUpgradeTemplate().upgrade(this);
    }
    
    public boolean hasUpgradeTemplate() {
        return this.hasData(ItemStats.UPGRADE) && ((UpgradeData)this.getData(ItemStats.UPGRADE)).getTemplate() != null;
    }
    
    public int getUpgradeLevel() {
        if (this.hasData(ItemStats.UPGRADE)) {
            return ((UpgradeData)this.getData(ItemStats.UPGRADE)).getLevel();
        }
        return 0;
    }
    
    public int getMaxUpgradeLevel() {
        if (this.hasData(ItemStats.UPGRADE)) {
            return ((UpgradeData)this.getData(ItemStats.UPGRADE)).getMax();
        }
        return 0;
    }
    
    @NotNull
    public UpgradeTemplate getUpgradeTemplate() {
        Validate.isTrue(this.hasUpgradeTemplate(), "This item has no Upgrade Information, do not call this method without checking first!");
        return ((UpgradeData)this.getData(ItemStats.UPGRADE)).getTemplate();
    }
    
    @NotNull
    public Set<GemstoneData> getGemStones() {
        if (this.hasData(ItemStats.GEM_SOCKETS)) {
            return ((GemSocketsData)this.getData(ItemStats.GEM_SOCKETS)).getGemstones();
        }
        return new HashSet<GemstoneData>();
    }
    
    @NotNull
    public ArrayList<MMOItem> extractGemstones() {
        final GemSocketsData gemSocketsData = (GemSocketsData)this.getData(ItemStats.GEM_SOCKETS);
        if (gemSocketsData == null) {
            return new ArrayList<MMOItem>();
        }
        final HashMap<UUID, MMOItem> hashMap = new HashMap<UUID, MMOItem>();
        for (final GemstoneData gemstoneData : gemSocketsData.getGemstones()) {
            final MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getType(gemstoneData.getMMOItemType()), gemstoneData.getMMOItemID());
            if (mmoItem != null) {
                mmoItem.asGemColor = gemstoneData.getSocketColor();
                mmoItem.asGemUUID = gemstoneData.getHistoricUUID();
                hashMap.put(gemstoneData.getHistoricUUID(), mmoItem);
            }
        }
        for (final ItemStat itemStat : this.getStats()) {
            if (!(itemStat.getClearStatData() instanceof Mergeable)) {
                continue;
            }
            final StatHistory statHistory = this.getStatHistory(itemStat);
            if (statHistory == null) {
                continue;
            }
            for (final Map.Entry<UUID, MMOItem> entry : hashMap.entrySet()) {
                final StatData gemstoneData2 = statHistory.getGemstoneData(entry.getKey());
                if (gemstoneData2 == null) {
                    continue;
                }
                entry.getValue().setData(itemStat, gemstoneData2);
            }
        }
        return new ArrayList<MMOItem>(hashMap.values());
    }
    
    @Nullable
    public MMOItem extractGemstone(@NotNull final GemstoneData gemstoneData) {
        final MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getType(gemstoneData.getMMOItemType()), gemstoneData.getMMOItemID());
        if (mmoItem != null) {
            mmoItem.asGemColor = gemstoneData.getSocketColor();
            mmoItem.asGemUUID = gemstoneData.getHistoricUUID();
            for (final ItemStat itemStat : this.getStats()) {
                if (!(itemStat.getClearStatData() instanceof Mergeable)) {
                    continue;
                }
                final StatHistory statHistory = this.getStatHistory(itemStat);
                if (statHistory == null) {
                    continue;
                }
                final StatData gemstoneData2 = statHistory.getGemstoneData(gemstoneData.getHistoricUUID());
                if (gemstoneData2 == null) {
                    continue;
                }
                mmoItem.setData(itemStat, gemstoneData2);
            }
            return mmoItem;
        }
        return null;
    }
    
    @Nullable
    public String getAsGemColor() {
        return this.asGemColor;
    }
    
    @NotNull
    public UUID getAsGemUUID() {
        return this.asGemUUID;
    }
    
    public void removeGemStone(@NotNull final UUID uuid, @Nullable final String s) {
        if (!this.hasData(ItemStats.GEM_SOCKETS)) {
            return;
        }
        final StatHistory from = StatHistory.from(this, ItemStats.GEM_SOCKETS);
        if (GemSocketsData.removeGemFrom((GemSocketsData)from.getOriginalData(), uuid, s)) {
            return;
        }
        final Iterator<UUID> iterator = from.getAllGemstones().iterator();
        while (iterator.hasNext()) {
            if (GemSocketsData.removeGemFrom((GemSocketsData)from.getGemstoneData(iterator.next()), uuid, s)) {
                return;
            }
        }
        final Iterator<StatData> iterator2 = from.getExternalData().iterator();
        while (iterator2.hasNext()) {
            if (GemSocketsData.removeGemFrom((GemSocketsData)iterator2.next(), uuid, s)) {
                return;
            }
        }
        final Iterator<UUID> iterator3 = from.getAllModifiers().iterator();
        while (iterator3.hasNext()) {
            if (GemSocketsData.removeGemFrom((GemSocketsData)from.getModifiersBonus(iterator3.next()), uuid, s)) {
                return;
            }
        }
    }
}
