// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.holograms;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.TextLine;
import com.sainttx.holograms.api.Hologram;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.api.HologramManager;

public class HologramsPlugin extends HologramSupport
{
    private final HologramManager hologramManager;
    
    public HologramsPlugin() {
        this.hologramManager = ((HologramPlugin)JavaPlugin.getPlugin((Class)HologramPlugin.class)).getHologramManager();
    }
    
    @Override
    public void displayIndicator(final Location location, final String s, final Player player) {
        final Hologram hologram = new Hologram("MMOItems_" + UUID.randomUUID().toString(), location);
        this.hologramManager.addActiveHologram(hologram);
        hologram.addLine((HologramLine)new TextLine(hologram, s));
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)MMOItems.plugin, () -> this.hologramManager.deleteHologram(hologram), 20L);
    }
}
