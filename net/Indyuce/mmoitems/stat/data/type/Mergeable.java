// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.type;

import org.jetbrains.annotations.NotNull;

public interface Mergeable
{
    void merge(final StatData p0);
    
    @NotNull
    StatData cloneData();
    
    boolean isClear();
}
