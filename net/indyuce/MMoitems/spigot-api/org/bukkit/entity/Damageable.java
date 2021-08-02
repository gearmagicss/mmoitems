// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface Damageable extends Entity
{
    void damage(final double p0);
    
    @Deprecated
    void _INVALID_damage(final int p0);
    
    void damage(final double p0, final Entity p1);
    
    @Deprecated
    void _INVALID_damage(final int p0, final Entity p1);
    
    double getHealth();
    
    @Deprecated
    int _INVALID_getHealth();
    
    void setHealth(final double p0);
    
    @Deprecated
    void _INVALID_setHealth(final int p0);
    
    double getMaxHealth();
    
    @Deprecated
    int _INVALID_getMaxHealth();
    
    void setMaxHealth(final double p0);
    
    @Deprecated
    void _INVALID_setMaxHealth(final int p0);
    
    void resetMaxHealth();
}
