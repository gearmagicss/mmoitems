// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.projectiles;

import org.bukkit.util.Vector;
import org.bukkit.entity.Projectile;

public interface ProjectileSource
{
     <T extends Projectile> T launchProjectile(final Class<? extends T> p0);
    
     <T extends Projectile> T launchProjectile(final Class<? extends T> p0, final Vector p1);
}
