// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import org.bukkit.inventory.meta.Damageable;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class ItemDamage extends DoubleStat implements GemStoneStat
{
    public ItemDamage() {
        super("ITEM_DAMAGE", Material.FISHING_ROD, "Item Damage", new String[] { "Default item damage. This does &cNOT", "impact the item's max durability." }, new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (itemStackBuilder.getMeta() instanceof Damageable) {
            ((Damageable)itemStackBuilder.getMeta()).setDamage((int)((DoubleData)statData).getValue());
        }
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        this.whenApplied(itemStackBuilder, statData);
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        if (readMMOItem.getNBT().getItem().getItemMeta() instanceof Damageable) {
            readMMOItem.setData(ItemStats.ITEM_DAMAGE, new DoubleData(((Damageable)readMMOItem.getNBT().getItem().getItemMeta()).getDamage()));
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
}
