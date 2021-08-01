// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.type.ItemStat;

@FunctionalInterface
public interface UpdatableRandomStatData
{
    @NotNull
     <T extends StatData> T reroll(@NotNull final ItemStat p0, @NotNull final T p1, final int p2);
}
