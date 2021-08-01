// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import org.bukkit.Location;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.util.Random;

public interface StaffAttackHandler
{
    public static final Random random = new Random();
    
    void handle(final PlayerStats.CachedStats p0, final NBTItem p1, final double p2, final double p3);
    
    default Location getGround(final Location loc) {
        for (int j = 0; j < 20; ++j) {
            if (loc.getBlock().getType().isSolid()) {
                return loc;
            }
            loc.add(0.0, -1.0, 0.0);
        }
        return loc;
    }
}
