// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.mmoitem;

import org.bukkit.ChatColor;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.NBTItem;

public class VolatileMMOItem extends ReadMMOItem
{
    public VolatileMMOItem(final NBTItem nbtItem) {
        super(nbtItem);
    }
    
    @Override
    public boolean hasData(@NotNull final ItemStat itemStat) {
        if (!super.hasData(itemStat)) {
            try {
                itemStat.whenLoaded(this);
                if (this.getStatHistory(itemStat) == null) {
                    final ItemTag tagAtPath = ItemTag.getTagAtPath("HSTRY_" + itemStat.getId(), this.getNBT(), SupportedNBTTagValues.STRING);
                    if (tagAtPath != null) {
                        final StatHistory fromNBTString = StatHistory.fromNBTString(this, (String)tagAtPath.getValue());
                        if (fromNBTString != null) {
                            this.setStatHistory(itemStat, fromNBTString);
                        }
                    }
                }
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, ChatColor.GRAY + "Could not load stat '" + ChatColor.GOLD + itemStat.getId() + ChatColor.GRAY + "'item data from '" + ChatColor.RED + this.getId() + ChatColor.GRAY + "': " + ChatColor.YELLOW + ex.getMessage());
            }
        }
        return super.hasData(itemStat);
    }
}
