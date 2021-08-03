// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.data.type.UpgradeInfo;
import org.jetbrains.annotations.Nullable;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.stat.data.MaterialData;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
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
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        super.whenApplied(itemStackBuilder, statData);
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        this.whenApplied(itemStackBuilder, statData);
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((DoubleData)statData).getValue()));
        list.add(new ItemTag(ItemStats.DURABILITY.getNBTPath(), (Object)((DoubleData)statData).getValue()));
        return list;
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
            mmoItem.setData(ItemStats.DURABILITY, new DoubleData(maxDurability));
        }
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (!nbtItem.hasTag(ItemStats.MAX_DURABILITY.getNBTPath())) {
            return true;
        }
        if (!nbtItem.hasTag(ItemStats.DURABILITY.getNBTPath())) {
            nbtItem.addTag(new ItemTag[] { new ItemTag(ItemStats.DURABILITY.getNBTPath(), (Object)nbtItem.getDouble(ItemStats.MAX_DURABILITY.getNBTPath())) });
        }
        if (nbtItem.getDouble(ItemStats.DURABILITY.getNBTPath()) <= 0.0) {
            if (b) {
                Message.ZERO_DURABILITY.format(ChatColor.RED, new String[0]).send(rpgPlayer.getPlayer(), "cant-use-item");
                rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            }
            return false;
        }
        return true;
    }
    
    @NotNull
    @Override
    public UpgradeInfo loadUpgradeInfo(@Nullable final Object o) {
        return DoubleUpgradeInfo.GetFrom(o);
    }
}
