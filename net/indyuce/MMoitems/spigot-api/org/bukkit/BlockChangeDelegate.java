// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

public interface BlockChangeDelegate
{
    @Deprecated
    boolean setRawTypeId(final int p0, final int p1, final int p2, final int p3);
    
    @Deprecated
    boolean setRawTypeIdAndData(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    @Deprecated
    boolean setTypeId(final int p0, final int p1, final int p2, final int p3);
    
    @Deprecated
    boolean setTypeIdAndData(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    @Deprecated
    int getTypeId(final int p0, final int p1, final int p2);
    
    int getHeight();
    
    boolean isEmpty(final int p0, final int p1, final int p2);
}
