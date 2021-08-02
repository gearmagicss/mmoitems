// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.Location;

public interface EndGateway extends BlockState
{
    Location getExitLocation();
    
    void setExitLocation(final Location p0);
    
    boolean isExactTeleport();
    
    void setExactTeleport(final boolean p0);
}
