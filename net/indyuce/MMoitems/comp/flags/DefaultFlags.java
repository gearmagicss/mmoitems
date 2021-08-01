// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.flags;

import org.bukkit.entity.Player;
import org.bukkit.Location;

public class DefaultFlags implements FlagPlugin
{
    @Override
    public boolean isPvpAllowed(final Location location) {
        return true;
    }
    
    @Override
    public boolean isFlagAllowed(final Player player, final CustomFlag customFlag) {
        return true;
    }
    
    @Override
    public boolean isFlagAllowed(final Location location, final CustomFlag customFlag) {
        return true;
    }
}
