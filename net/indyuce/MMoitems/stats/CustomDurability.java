// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.InternalStat;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class CustomDurability extends DoubleStat implements InternalStat
{
    public CustomDurability() {
        super("DURABILITY", Material.SHEARS, "Custom Durability", new String[0], new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final int i = (int)((DoubleData)statData).getValue();
        if (i != 0) {
            itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath(), (Object)i));
        }
    }
}
