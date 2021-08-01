// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.UUID;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.BooleanData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class Unstackable extends BooleanStat
{
    public Unstackable() {
        super("UNSTACKABLE", Material.CHEST_MINECART, "Unstackable", new String[] { "This will make the item unable", "to be stacked with itself." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (((BooleanData)statData).isEnabled()) {
            itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath(), (Object)true));
            itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath() + "_UUID", (Object)UUID.randomUUID().toString()));
        }
    }
}
