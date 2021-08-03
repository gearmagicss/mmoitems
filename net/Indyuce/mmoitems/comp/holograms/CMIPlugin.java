// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.holograms;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import com.Zrips.CMI.CMI;
import java.util.List;
import java.util.Collections;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class CMIPlugin extends HologramSupport
{
    @Override
    public void displayIndicator(final Location location, final String o, final Player player) {
        final CMIHologram cmiHologram = new CMIHologram("MMOItems_" + UUID.randomUUID().toString(), location);
        cmiHologram.setLines((List)Collections.singletonList(o));
        if (player != null) {
            cmiHologram.hide(player.getUniqueId());
        }
        CMI.getInstance().getHologramManager().addHologram(cmiHologram);
        cmiHologram.update();
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)MMOItems.plugin, () -> CMI.getInstance().getHologramManager().removeHolo(cmiHologram), 20L);
    }
}
