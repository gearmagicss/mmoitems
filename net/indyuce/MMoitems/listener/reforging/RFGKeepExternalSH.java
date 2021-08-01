// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepExternalSH implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepExternalSH()) {
            return;
        }
        for (final StatHistory statHistory : mmoItemReforgeEvent.getOldMMOItem().getStatHistories()) {
            if (statHistory.getItemStat() == ItemStats.ENCHANTS) {
                continue;
            }
            final StatHistory from = StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), statHistory.getItemStat());
            final Iterator<StatData> iterator2 = statHistory.getExternalData().iterator();
            while (iterator2.hasNext()) {
                from.registerExternalData(iterator2.next());
            }
        }
    }
}
