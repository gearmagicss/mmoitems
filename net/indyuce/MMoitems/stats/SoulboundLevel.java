// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class SoulboundLevel extends DoubleStat
{
    public SoulboundLevel() {
        super("SOULBOUND_LEVEL", VersionMaterial.ENDER_EYE.toMaterial(), "Soulbinding Level", new String[] { "The soulbound level defines how much", "damage players will take when trying", "to use a soulbound item. It also determines", "how hard it is to break the binding." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final int i = (int)((DoubleData)statData).getValue();
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_SOULBOUND_LEVEL", (Object)i));
        itemStackBuilder.getLore().insert("soulbound-level", this.formatNumericStat(i, "#", MMOUtils.intToRoman(i)));
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        this.whenApplied(itemStackBuilder, statData);
    }
}
