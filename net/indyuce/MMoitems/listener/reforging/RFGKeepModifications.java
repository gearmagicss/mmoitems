// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import java.util.UUID;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepModifications implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepMods()) {
            return;
        }
        final Iterator<StatHistory> iterator = mmoItemReforgeEvent.getNewMMOItem().getStatHistories().iterator();
        while (iterator.hasNext()) {
            iterator.next().clearModifiersBonus();
        }
        for (final StatHistory statHistory : mmoItemReforgeEvent.getOldMMOItem().getStatHistories()) {
            final StatHistory from = StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), statHistory.getItemStat());
            for (final UUID uuid : statHistory.getAllModifiers()) {
                final StatData modifiersBonus = statHistory.getModifiersBonus(uuid);
                if (modifiersBonus == null) {
                    continue;
                }
                from.registerModifierBonus(uuid, ((Mergeable)modifiersBonus).cloneData());
            }
        }
    }
}
