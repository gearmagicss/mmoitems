// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepUpgrades implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepUpgrades()) {
            return;
        }
        final UpgradeData upgradeData = (UpgradeData)mmoItemReforgeEvent.getOldMMOItem().getData(ItemStats.UPGRADE);
        final UpgradeData upgradeData2 = (UpgradeData)mmoItemReforgeEvent.getNewMMOItem().getData(ItemStats.UPGRADE);
        if (upgradeData == null || upgradeData2 == null) {
            return;
        }
        final UpgradeData upgradeData3 = new UpgradeData(upgradeData2.getReference(), upgradeData2.getTemplateName(), upgradeData2.isWorkbench(), upgradeData2.isDestroy(), upgradeData2.getMax(), upgradeData2.getSuccess());
        upgradeData3.setLevel(Math.min(upgradeData.getLevel(), upgradeData2.getMaxUpgrades()));
        mmoItemReforgeEvent.getNewMMOItem().setData(ItemStats.UPGRADE, upgradeData3);
    }
}
