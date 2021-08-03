// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.plugin.java.JavaPlugin;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.comp.Metrics;

public class MMOItemsMetrics extends Metrics
{
    public MMOItemsMetrics() {
        super((JavaPlugin)MMOItems.plugin);
        final Iterator<Type> iterator;
        int i = 0;
        this.addCustomChart((Metrics.CustomChart)new Metrics.SingleLineChart("items", () -> {
            MMOItems.plugin.getTypes().getAll().iterator();
            while (iterator.hasNext()) {
                i += iterator.next().getConfigFile().getConfig().getKeys(false).size();
            }
            return Integer.valueOf(i);
        }));
    }
}
