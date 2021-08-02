// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.Location;

public interface EnderCrystal extends Entity
{
    boolean isShowingBottom();
    
    void setShowingBottom(final boolean p0);
    
    Location getBeamTarget();
    
    void setBeamTarget(final Location p0);
}
