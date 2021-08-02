// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.Material;

public interface FallingBlock extends Entity
{
    Material getMaterial();
    
    @Deprecated
    int getBlockId();
    
    @Deprecated
    byte getBlockData();
    
    boolean getDropItem();
    
    void setDropItem(final boolean p0);
    
    boolean canHurtEntities();
    
    void setHurtEntities(final boolean p0);
}
