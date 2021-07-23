// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;

public interface Previewable
{
    void whenPreviewed(@NotNull final ItemStackBuilder p0, @NotNull final StatData p1, @NotNull final RandomStatData p2) throws IllegalArgumentException;
}
