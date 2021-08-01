// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepLore implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepLore()) {
            return;
        }
        final StringListData stringListData = (StringListData)mmoItemReforgeEvent.getOldMMOItem().getData(ItemStats.LORE);
        if (stringListData == null) {
            return;
        }
        final ArrayList<String> lore = this.extractLore(stringListData.getList(), mmoItemReforgeEvent.getOptions().getKeepCase());
        if (lore.size() == 0) {
            return;
        }
        final StatHistory from = StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), ItemStats.LORE);
        final StringListData originalData = (StringListData)from.getOriginalData();
        final Iterator<String> iterator = lore.iterator();
        while (iterator.hasNext()) {
            originalData.getList().add(iterator.next());
        }
        from.setOriginalData(originalData);
    }
    
    @NotNull
    ArrayList<String> extractLore(@NotNull final List<String> list, @NotNull final String prefix) {
        final ArrayList<String> list2 = new ArrayList<String>();
        for (final String e : list) {
            if (e.startsWith(prefix)) {
                list2.add(e);
            }
        }
        return list2;
    }
}
