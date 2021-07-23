// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.holograms;

import org.bukkit.plugin.Plugin;
import me.arasple.mc.trhologram.api.TrHologramAPI;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class TrHologramPlugin extends HologramSupport
{
    @Override
    public void displayIndicator(final Location location, final String s, final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)MMOItems.plugin, TrHologramAPI.builder(location).append(s).build()::destroy, 20L);
    }
}
