// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

public interface WorldBorder
{
    void reset();
    
    double getSize();
    
    void setSize(final double p0);
    
    void setSize(final double p0, final long p1);
    
    Location getCenter();
    
    void setCenter(final double p0, final double p1);
    
    void setCenter(final Location p0);
    
    double getDamageBuffer();
    
    void setDamageBuffer(final double p0);
    
    double getDamageAmount();
    
    void setDamageAmount(final double p0);
    
    int getWarningTime();
    
    void setWarningTime(final int p0);
    
    int getWarningDistance();
    
    void setWarningDistance(final int p0);
}
