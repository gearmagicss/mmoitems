// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.stat.data.MaterialData;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.Upgradable;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class MaximumDurability extends DoubleStat implements ItemRestriction, GemStoneStat, Upgradable
{
    public MaximumDurability() {
        super("MAX_DURABILITY", Material.SHEARS, "Maximum Durability", new String[] { "The amount of uses before your", "item becomes unusable/breaks." }, new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        this.whenApplied(itemStackBuilder, statData);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final int n = (int)((DoubleData)statData).getValue();
        final int i = itemStackBuilder.getMMOItem().hasData(ItemStats.CUSTOM_DURABILITY) ? ((int)((DoubleData)itemStackBuilder.getMMOItem().getData(ItemStats.CUSTOM_DURABILITY)).getValue()) : n;
        itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath(), (Object)n));
        itemStackBuilder.getLore().insert("durability", MMOItems.plugin.getLanguage().getStatFormat("durability").replace("#m", "" + n).replace("#c", "" + i));
    }
    
    @Override
    public void preprocess(@NotNull final MMOItem mmoItem) {
        if (!mmoItem.hasData(ItemStats.MAX_DURABILITY)) {
            int maxDurability = 400;
            if (mmoItem.hasData(ItemStats.MATERIAL)) {
                maxDurability = ((MaterialData)mmoItem.getData(ItemStats.MATERIAL)).getMaterial().getMaxDurability();
            }
            if (maxDurability < 8) {
                maxDurability = 400;
            }
            mmoItem.setData(ItemStats.MAX_DURABILITY, new DoubleData(maxDurability));
        }
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (!nbtItem.hasTag("MMOITEMS_DURABILITY")) {
            return true;
        }
        if (nbtItem.getDouble(ItemStats.CUSTOM_DURABILITY.getNBTPath()) <= 0.0) {
            if (b) {
                Message.ZERO_DURABILITY.format(ChatColor.RED, new String[0]).send(rpgPlayer.getPlayer(), "cant-use-item");
                rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            }
            return false;
        }
        return true;
    }
}
