// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.enchantments.Enchantment;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.stat.Enchants;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.EnchantListData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepEnchantments implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepEnchantments()) {
            return;
        }
        final MMOItem clone = mmoItemReforgeEvent.getOldMMOItem().clone();
        if (!clone.hasData(ItemStats.ENCHANTS)) {
            clone.setData(ItemStats.ENCHANTS, new EnchantListData());
        }
        Enchants.separateEnchantments(clone);
        final StatHistory from = StatHistory.from(clone, ItemStats.ENCHANTS);
        final EnchantListData enchantListData = (EnchantListData)((Mergeable)from.getOriginalData()).cloneData();
        final StatHistory from2 = StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), ItemStats.ENCHANTS);
        final Iterator<StatData> iterator = from.getExternalData().iterator();
        while (iterator.hasNext()) {
            from2.registerExternalData(iterator.next());
        }
        enchantListData.identifyTrueOriginalEnchantments(mmoItemReforgeEvent.getNewMMOItem());
    }
    
    void log(@NotNull final EnchantListData enchantListData, @NotNull final String str) {
        MMOItems.print(null, "  §3> §7" + str + ":", null, new String[0]);
        for (final Enchantment enchantment : enchantListData.getEnchants()) {
            MMOItems.print(null, "  §b * §7" + enchantment.getName() + " §f" + enchantListData.getLevel(enchantment), null, new String[0]);
        }
    }
}
