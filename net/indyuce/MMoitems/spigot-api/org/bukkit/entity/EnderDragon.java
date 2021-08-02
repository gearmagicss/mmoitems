// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface EnderDragon extends ComplexLivingEntity
{
    Phase getPhase();
    
    void setPhase(final Phase p0);
    
    public enum Phase
    {
        CIRCLING("CIRCLING", 0), 
        STRAFING("STRAFING", 1), 
        FLY_TO_PORTAL("FLY_TO_PORTAL", 2), 
        LAND_ON_PORTAL("LAND_ON_PORTAL", 3), 
        LEAVE_PORTAL("LEAVE_PORTAL", 4), 
        BREATH_ATTACK("BREATH_ATTACK", 5), 
        SEARCH_FOR_BREATH_ATTACK_TARGET("SEARCH_FOR_BREATH_ATTACK_TARGET", 6), 
        ROAR_BEFORE_ATTACK("ROAR_BEFORE_ATTACK", 7), 
        CHARGE_PLAYER("CHARGE_PLAYER", 8), 
        DYING("DYING", 9), 
        HOVER("HOVER", 10);
        
        private Phase(final String name, final int ordinal) {
        }
    }
}
