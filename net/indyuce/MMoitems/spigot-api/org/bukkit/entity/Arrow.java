// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface Arrow extends Projectile
{
    int getKnockbackStrength();
    
    void setKnockbackStrength(final int p0);
    
    boolean isCritical();
    
    void setCritical(final boolean p0);
    
    Spigot spigot();
    
    public static class Spigot extends Entity.Spigot
    {
        public double getDamage() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void setDamage(final double damage) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
