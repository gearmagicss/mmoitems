// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.random.UpdatableRandomStatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepRNG implements Listener
{
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (mmoItemReforgeEvent.getOptions().shouldReroll()) {
            return;
        }
        for (final ItemStat itemStat : mmoItemReforgeEvent.getOldMMOItem().getStats()) {
            if (!(itemStat.getClearStatData() instanceof Mergeable)) {
                continue;
            }
            if (ItemStats.LORE.equals(itemStat) || ItemStats.NAME.equals(itemStat) || ItemStats.UPGRADE.equals(itemStat) || ItemStats.ENCHANTS.equals(itemStat) || ItemStats.SOULBOUND.equals(itemStat)) {
                continue;
            }
            if (ItemStats.GEM_SOCKETS.equals(itemStat)) {
                continue;
            }
            final StatData shouldRerollRegardless = this.shouldRerollRegardless(itemStat, mmoItemReforgeEvent.getReforger().getTemplate().getBaseItemData().get(itemStat), StatHistory.from(mmoItemReforgeEvent.getOldMMOItem(), itemStat).getOriginalData(), mmoItemReforgeEvent.getReforger().getGenerationItemLevel());
            if (shouldRerollRegardless == null) {
                return;
            }
            StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), itemStat).setOriginalData(shouldRerollRegardless);
        }
    }
    
    @Nullable
    StatData shouldRerollRegardless(@NotNull final ItemStat itemStat, @NotNull final RandomStatData randomStatData, @NotNull final StatData statData, final int n) {
        if (!(randomStatData instanceof UpdatableRandomStatData)) {
            return null;
        }
        return ((UpdatableRandomStatData)randomStatData).reroll(itemStat, statData, n);
    }
}
