// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public interface Minecart extends Vehicle
{
    @Deprecated
    void _INVALID_setDamage(final int p0);
    
    void setDamage(final double p0);
    
    @Deprecated
    int _INVALID_getDamage();
    
    double getDamage();
    
    double getMaxSpeed();
    
    void setMaxSpeed(final double p0);
    
    boolean isSlowWhenEmpty();
    
    void setSlowWhenEmpty(final boolean p0);
    
    Vector getFlyingVelocityMod();
    
    void setFlyingVelocityMod(final Vector p0);
    
    Vector getDerailedVelocityMod();
    
    void setDerailedVelocityMod(final Vector p0);
    
    void setDisplayBlock(final MaterialData p0);
    
    MaterialData getDisplayBlock();
    
    void setDisplayBlockOffset(final int p0);
    
    int getDisplayBlockOffset();
}
