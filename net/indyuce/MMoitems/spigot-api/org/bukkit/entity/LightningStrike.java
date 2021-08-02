// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface LightningStrike extends Weather
{
    boolean isEffect();
    
    Spigot spigot();
    
    public static class Spigot extends Entity.Spigot
    {
        public boolean isSilent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
