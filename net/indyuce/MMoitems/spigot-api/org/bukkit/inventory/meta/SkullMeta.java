// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory.meta;

public interface SkullMeta extends ItemMeta
{
    String getOwner();
    
    boolean hasOwner();
    
    boolean setOwner(final String p0);
    
    SkullMeta clone();
}
