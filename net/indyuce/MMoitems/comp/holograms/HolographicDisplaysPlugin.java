// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.holograms;

import org.bukkit.scheduler.BukkitScheduler;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class HolographicDisplaysPlugin extends HologramSupport
{
    @Override
    public void displayIndicator(final Location location, final String s, final Player player) {
        final Hologram hologram = HologramsAPI.createHologram((Plugin)MMOItems.plugin, location);
        hologram.appendTextLine(s);
        if (player != null) {
            hologram.getVisibilityManager().hideTo(player);
        }
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        final MMOItems plugin = MMOItems.plugin;
        final Hologram obj = hologram;
        Objects.requireNonNull(obj);
        scheduler.scheduleSyncDelayedTask((Plugin)plugin, obj::delete, 20L);
    }
}
