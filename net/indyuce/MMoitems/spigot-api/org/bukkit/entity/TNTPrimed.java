// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface TNTPrimed extends Explosive
{
    void setFuseTicks(final int p0);
    
    int getFuseTicks();
    
    Entity getSource();
}
