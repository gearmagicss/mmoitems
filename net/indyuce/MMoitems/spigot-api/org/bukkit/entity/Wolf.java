// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.DyeColor;

public interface Wolf extends Animals, Tameable
{
    boolean isAngry();
    
    void setAngry(final boolean p0);
    
    boolean isSitting();
    
    void setSitting(final boolean p0);
    
    DyeColor getCollarColor();
    
    void setCollarColor(final DyeColor p0);
}
