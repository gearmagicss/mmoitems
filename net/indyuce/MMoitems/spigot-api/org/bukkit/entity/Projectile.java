// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.projectiles.ProjectileSource;

public interface Projectile extends Entity
{
    @Deprecated
    LivingEntity _INVALID_getShooter();
    
    ProjectileSource getShooter();
    
    @Deprecated
    void _INVALID_setShooter(final LivingEntity p0);
    
    void setShooter(final ProjectileSource p0);
    
    boolean doesBounce();
    
    void setBounce(final boolean p0);
}
