// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.mmoitem;

import java.util.Iterator;
import org.bukkit.ChatColor;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.inventory.ItemStack;

public class LiveMMOItem extends ReadMMOItem
{
    public LiveMMOItem(final ItemStack itemStack) {
        this(MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack));
    }
    
    public LiveMMOItem(final NBTItem nbtItem) {
        super(nbtItem);
        for (final ItemStat itemStat : this.getType().getAvailableStats()) {
            try {
                itemStat.whenLoaded(this);
                if (this.getStatHistory(itemStat) != null) {
                    continue;
                }
                final ItemTag tagAtPath = ItemTag.getTagAtPath("HSTRY_" + itemStat.getId(), this.getNBT(), SupportedNBTTagValues.STRING);
                if (tagAtPath == null) {
                    continue;
                }
                final StatHistory fromNBTString = StatHistory.fromNBTString(this, (String)tagAtPath.getValue());
                if (fromNBTString == null) {
                    continue;
                }
                this.setStatHistory(itemStat, fromNBTString);
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, ChatColor.GRAY + "Could not load stat '" + ChatColor.GOLD + itemStat.getId() + ChatColor.GRAY + "'item data from '" + ChatColor.RED + this.getId() + ChatColor.GRAY + "': " + ChatColor.YELLOW + ex.getMessage());
            }
        }
    }
}
