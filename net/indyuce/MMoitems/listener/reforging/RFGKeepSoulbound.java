// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepSoulbound implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        final SoulboundData soulboundData = (SoulboundData)mmoItemReforgeEvent.getOldMMOItem().getData(ItemStats.SOULBOUND);
        if (soulboundData == null) {
            if (MMOItems.plugin.getConfig().getBoolean("soulbound.auto-bind.disable-on." + mmoItemReforgeEvent.getTypeName())) {
                return;
            }
            if (mmoItemReforgeEvent.getPlayer() == null) {
                return;
            }
            if (mmoItemReforgeEvent.getNewMMOItem().hasData(ItemStats.AUTO_SOULBIND) && !mmoItemReforgeEvent.getNewMMOItem().hasData(ItemStats.SOULBOUND)) {
                mmoItemReforgeEvent.getNewMMOItem().setData(ItemStats.SOULBOUND, new SoulboundData(mmoItemReforgeEvent.getPlayer().getUniqueId(), mmoItemReforgeEvent.getPlayer().getName(), MMOItemReforger.autoSoulbindLevel));
            }
        }
        else if (mmoItemReforgeEvent.getOptions().shouldKeepSoulbind()) {
            mmoItemReforgeEvent.getNewMMOItem().setData(ItemStats.SOULBOUND, soulboundData);
        }
    }
}
