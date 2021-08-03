// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.ItemStats;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.RequiredLevelData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.stat.data.random.RandomRequiredLevelData;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RequiredLevel extends DoubleStat implements ItemRestriction
{
    public RequiredLevel() {
        super("REQUIRED_LEVEL", VersionMaterial.EXPERIENCE_BOTTLE.toMaterial(), "Required Level", new String[] { "The level your item needs", "in order to be used." }, new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final int i = (int)((DoubleData)statData).getValue();
        itemStackBuilder.getLore().insert("required-level", this.formatNumericStat(i, "#", "" + i));
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        Validate.isTrue(statData instanceof DoubleData, "Current Data is not Double Data");
        Validate.isTrue(randomStatData instanceof NumericStatFormula, "Template Data is not Numeric Stat Formula");
        double calculate = ((NumericStatFormula)randomStatData).calculate(0.0, -2.5);
        double calculate2 = ((NumericStatFormula)randomStatData).calculate(0.0, 2.5);
        if (calculate2 < 0.0 && !this.handleNegativeStats()) {
            return;
        }
        if (calculate < 0.0 && !this.handleNegativeStats()) {
            calculate = 0.0;
        }
        if (calculate < ((NumericStatFormula)randomStatData).getBase() - ((NumericStatFormula)randomStatData).getMaxSpread()) {
            calculate = ((NumericStatFormula)randomStatData).getBase() - ((NumericStatFormula)randomStatData).getMaxSpread();
        }
        if (calculate2 > ((NumericStatFormula)randomStatData).getBase() + ((NumericStatFormula)randomStatData).getMaxSpread()) {
            calculate2 = ((NumericStatFormula)randomStatData).getBase() + ((NumericStatFormula)randomStatData).getMaxSpread();
        }
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        if (calculate != 0.0 || calculate2 != 0.0) {
            String s;
            if (SilentNumbers.round(calculate, 2) == SilentNumbers.round(calculate2, 2)) {
                s = SilentNumbers.readableRounding(calculate, 0);
            }
            else {
                s = SilentNumbers.removeDecimalZeros(String.valueOf(calculate)) + "-" + SilentNumbers.removeDecimalZeros(String.valueOf(calculate2));
            }
            itemStackBuilder.getLore().insert("required-level", this.formatNumericStat(calculate, "#", s));
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((DoubleData)statData).getValue()));
        return list;
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        return new RandomRequiredLevelData(o);
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new RequiredLevelData((double)tagAtPath.getValue());
        }
        return null;
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (rpgPlayer.getLevel() < nbtItem.getInteger(ItemStats.REQUIRED_LEVEL.getNBTPath()) && !rpgPlayer.getPlayer().hasPermission("mmoitems.bypass.level")) {
            if (b) {
                Message.NOT_ENOUGH_LEVELS.format(ChatColor.RED, new String[0]).send(rpgPlayer.getPlayer(), "cant-use-item");
                rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            }
            return false;
        }
        return true;
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new RequiredLevelData(0.0);
    }
}
