// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.build;

import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.type.NameData;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.api.item.template.TemplateModifier;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.item.template.NameModifier;
import java.util.UUID;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;

public class MMOItemBuilder
{
    private final MMOItem mmoitem;
    private final int level;
    private final ItemTier tier;
    private final HashMap<UUID, NameModifier> nameModifiers;
    
    public MMOItemBuilder(final MMOItemTemplate mmoItemTemplate, final int level, final ItemTier tier) {
        this.nameModifiers = new HashMap<UUID, NameModifier>();
        this.level = level;
        this.tier = tier;
        double calculate = ((tier != null && tier.hasCapacity()) ? tier.getModifierCapacity() : MMOItems.plugin.getLanguage().defaultItemCapacity).calculate(level);
        this.mmoitem = new MMOItem(mmoItemTemplate.getType(), mmoItemTemplate.getId());
        mmoItemTemplate.getBaseItemData().forEach((itemStat, randomStatData) -> this.applyData(itemStat, randomStatData.randomize(this)));
        if (tier != null) {
            this.mmoitem.setData(ItemStats.TIER, new StringData(tier.getId()));
        }
        if (level > 0) {
            this.mmoitem.setData(ItemStats.ITEM_LEVEL, new DoubleData(level));
        }
        for (final TemplateModifier templateModifier : this.rollModifiers(mmoItemTemplate)) {
            if (templateModifier.rollChance()) {
                if (templateModifier.getWeight() > calculate) {
                    continue;
                }
                final UUID randomUUID = UUID.randomUUID();
                calculate -= templateModifier.getWeight();
                if (templateModifier.hasNameModifier()) {
                    this.addModifier(templateModifier.getNameModifier(), randomUUID);
                }
                for (final ItemStat itemStat2 : templateModifier.getItemData().keySet()) {
                    this.addModifierData(itemStat2, templateModifier.getItemData().get(itemStat2).randomize(this), randomUUID);
                }
            }
        }
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public ItemTier getTier() {
        return this.tier;
    }
    
    public MMOItem build() {
        if (!this.nameModifiers.isEmpty()) {
            final StatHistory from = StatHistory.from(this.mmoitem, ItemStats.NAME);
            if (!this.mmoitem.hasData(ItemStats.NAME)) {
                this.mmoitem.setData(ItemStats.NAME, new NameData("Item"));
            }
            for (final UUID key : this.nameModifiers.keySet()) {
                final NameModifier nameModifier = this.nameModifiers.get(key);
                final NameData nameData = new NameData("");
                if (nameModifier.getType() == NameModifier.ModifierType.PREFIX) {
                    nameData.addPrefix(nameModifier.getFormat());
                }
                if (nameModifier.getType() == NameModifier.ModifierType.SUFFIX) {
                    nameData.addSuffix(nameModifier.getFormat());
                }
                from.registerModifierBonus(key, nameData);
            }
            this.mmoitem.setData(ItemStats.NAME, from.recalculate(this.mmoitem.getUpgradeLevel()));
        }
        return this.mmoitem;
    }
    
    public void applyData(final ItemStat itemStat, final StatData statData) {
        if (this.mmoitem.hasData(itemStat) && statData instanceof Mergeable) {
            ((Mergeable)this.mmoitem.getData(itemStat)).merge(statData);
        }
        else {
            this.mmoitem.setData(itemStat, statData);
        }
    }
    
    public void addModifierData(@NotNull final ItemStat itemStat, @NotNull final StatData statData, @NotNull final UUID uuid) {
        StatHistory.from(this.mmoitem, itemStat).registerModifierBonus(uuid, statData);
    }
    
    public void addModifier(@NotNull final NameModifier value, @NotNull final UUID key) {
        final ArrayList<UUID> list = new ArrayList<UUID>();
        for (final UUID uuid : this.nameModifiers.keySet()) {
            final NameModifier nameModifier = this.nameModifiers.get(uuid);
            if (nameModifier.getType() == value.getType()) {
                if (nameModifier.getPriority() > value.getPriority()) {
                    return;
                }
                if (nameModifier.getPriority() >= value.getPriority()) {
                    continue;
                }
                list.add(uuid);
            }
        }
        final Iterator<UUID> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            this.nameModifiers.remove(iterator2.next());
        }
        this.nameModifiers.put(key, value);
    }
    
    private Collection<TemplateModifier> rollModifiers(final MMOItemTemplate mmoItemTemplate) {
        if (!mmoItemTemplate.hasOption(MMOItemTemplate.TemplateOption.ROLL_MODIFIER_CHECK_ORDER)) {
            return mmoItemTemplate.getModifiers().values();
        }
        final ArrayList<Object> list = new ArrayList<Object>(mmoItemTemplate.getModifiers().values());
        Collections.shuffle(list);
        return (Collection<TemplateModifier>)list;
    }
}
