// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.stat.type.NameData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepName implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepName()) {
            return;
        }
        NameData nameData;
        if (mmoItemReforgeEvent.getOldMMOItem().hasData(ItemStats.NAME)) {
            nameData = new NameData(((NameData)mmoItemReforgeEvent.getOldMMOItem().getData(ItemStats.NAME)).getMainName());
        }
        else {
            if (!mmoItemReforgeEvent.getReforger().getMeta().hasDisplayName()) {
                return;
            }
            nameData = new NameData(mmoItemReforgeEvent.getReforger().getMeta().getDisplayName());
        }
        ((NameData)StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), ItemStats.NAME).getOriginalData()).setString(nameData.getMainName());
    }
}
