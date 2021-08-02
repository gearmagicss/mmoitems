// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.TreeSpecies;

public interface Boat extends Vehicle
{
    TreeSpecies getWoodType();
    
    void setWoodType(final TreeSpecies p0);
    
    @Deprecated
    double getMaxSpeed();
    
    @Deprecated
    void setMaxSpeed(final double p0);
    
    @Deprecated
    double getOccupiedDeceleration();
    
    @Deprecated
    void setOccupiedDeceleration(final double p0);
    
    @Deprecated
    double getUnoccupiedDeceleration();
    
    @Deprecated
    void setUnoccupiedDeceleration(final double p0);
    
    @Deprecated
    boolean getWorkOnLand();
    
    @Deprecated
    void setWorkOnLand(final boolean p0);
}
