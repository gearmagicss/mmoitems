// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.block;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class BlockID extends DoubleStat
{
    public BlockID() {
        super("BLOCK_ID", Material.STONE, "Block ID", new String[] { "This value determines which", "custom block will get placed." }, new String[] { "block" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        super.whenApplied(itemStackBuilder, statData);
        itemStackBuilder.addItemTag(new ItemTag("CustomModelData", (Object)((int)((DoubleData)statData).getValue() + 1000)));
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        this.whenApplied(itemStackBuilder, statData);
    }
}
