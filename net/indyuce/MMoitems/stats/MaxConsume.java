// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.DynamicLoreStat;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class MaxConsume extends DoubleStat implements DynamicLoreStat
{
    public MaxConsume() {
        super("MAX_CONSUME", Material.BLAZE_POWDER, "Max Consume", new String[] { "Max amount of usage before", "item disappears." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final int n = (int)((DoubleData)statData).getValue();
        itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath(), (Object)n));
        itemStackBuilder.getLore().insert("max-consume", MMOItems.plugin.getLanguage().getStatFormat("max-consume").replace("#", "" + n));
    }
    
    @Override
    public String getDynamicLoreId() {
        return "max-consume";
    }
    
    @Override
    public String calculatePlaceholder(final NBTItem nbtItem) {
        return MMOItems.plugin.getLanguage().getStatFormat("max-consume").replace("#", "" + nbtItem.getInteger("MMOITEMS_MAX_CONSUME"));
    }
}
