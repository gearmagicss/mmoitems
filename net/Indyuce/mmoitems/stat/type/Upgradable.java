// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.data.type.UpgradeInfo;
import org.jetbrains.annotations.Nullable;

public interface Upgradable
{
    @NotNull
    UpgradeInfo loadUpgradeInfo(@Nullable final Object p0) throws IllegalArgumentException;
    
    @NotNull
    StatData apply(@NotNull final StatData p0, @NotNull final UpgradeInfo p1, final int p2);
    
    default void preprocess(@NotNull final MMOItem item) {
    }
    
    default void midprocess(@NotNull final MMOItem item) {
    }
    
    default void postprocess(@NotNull final MMOItem item) {
    }
}
