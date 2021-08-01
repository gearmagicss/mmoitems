// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.flags;

import org.bukkit.entity.Player;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Location;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class ResidenceFlags implements FlagPlugin
{
    public ResidenceFlags() {
        final CustomFlag[] values = CustomFlag.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            FlagPermissions.addFlag(values[i].getPath());
        }
    }
    
    @Override
    public boolean isPvpAllowed(final Location location) {
        final ClaimedResidence byLoc = Residence.getInstance().getResidenceManager().getByLoc(location);
        return byLoc == null || byLoc.getPermissions().has(Flags.pvp, true);
    }
    
    @Override
    public boolean isFlagAllowed(final Player player, final CustomFlag customFlag) {
        final ClaimedResidence byLoc = Residence.getInstance().getResidenceManager().getByLoc(player);
        return byLoc == null || byLoc.getPermissions().playerHas(player, customFlag.getPath(), true);
    }
    
    @Override
    public boolean isFlagAllowed(final Location location, final CustomFlag customFlag) {
        final ClaimedResidence byLoc = Residence.getInstance().getResidenceManager().getByLoc(location);
        return byLoc == null || byLoc.getPermissions().has(customFlag.getPath(), true, true);
    }
}
