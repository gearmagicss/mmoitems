// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.holograms;

import org.bukkit.scheduler.BukkitScheduler;
import me.arasple.mc.trhologram.module.display.Hologram;
import org.bukkit.plugin.Plugin;
import java.util.Objects;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import me.arasple.mc.trhologram.api.TrHologramAPI;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class TrHologramPlugin extends HologramSupport
{
    @Override
    public void displayIndicator(final Location location, final String s, final Player player) {
        final Hologram build = TrHologramAPI.builder(location).append(s).build();
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        final MMOItems plugin = MMOItems.plugin;
        final Hologram obj = build;
        Objects.requireNonNull(obj);
        scheduler.scheduleSyncDelayedTask((Plugin)plugin, obj::destroy, 20L);
    }
}
