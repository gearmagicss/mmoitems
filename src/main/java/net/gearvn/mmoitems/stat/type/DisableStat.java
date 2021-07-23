// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.BooleanData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;

public class DisableStat extends BooleanStat
{
    public DisableStat(final String str, final Material material, final String s, final String... array) {
        super("DISABLE_" + str, material, s, array, new String[] { "all" }, new Material[0]);
    }
    
    public DisableStat(final String str, final Material material, final String s, final Material[] array, final String... array2) {
        super("DISABLE_" + str, material, s, array2, new String[] { "all" }, array);
    }
    
    public DisableStat(final String str, final Material material, final String s, final String[] array, final String... array2) {
        super("DISABLE_" + str, material, s, array2, array, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (((BooleanData)statData).isEnabled()) {
            itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_" + this.getId(), (Object)true));
        }
    }
}
