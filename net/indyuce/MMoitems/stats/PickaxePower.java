// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.api.util.StatFormat;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class PickaxePower extends DoubleStat
{
    public PickaxePower() {
        super("PICKAXE_POWER", Material.IRON_PICKAXE, "Pickaxe Power", new String[] { "The breaking strength of the", "item when mining custom blocks." }, new String[] { "tool" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final int n = (int)((DoubleData)statData).getValue();
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_PICKAXE_POWER", (Object)n));
        itemStackBuilder.getLore().insert("pickaxe-power", this.formatNumericStat(n, "#", "" + n));
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
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_PICKAXE_POWER", (Object)((DoubleData)statData).getValue()));
        if (calculate != 0.0 || calculate2 != 0.0) {
            String s;
            if (SilentNumbers.round(calculate, 2) == SilentNumbers.round(calculate2, 2)) {
                s = new StatFormat("##").format(calculate);
            }
            else {
                s = new StatFormat("##").format(calculate) + "-" + new StatFormat("##").format(calculate2);
            }
            itemStackBuilder.getLore().insert("pickaxe-power", this.formatNumericStat(calculate, "#", s));
        }
    }
}
