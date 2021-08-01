// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.ReforgeOptions;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import java.util.UUID;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import java.util.Collection;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import java.util.ArrayList;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.Listener;

public class RFGKeepGems implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepGemStones()) {
            return;
        }
        final GemSocketsData gemSocketsData = (GemSocketsData)mmoItemReforgeEvent.getOldMMOItem().getData(ItemStats.GEM_SOCKETS);
        if (gemSocketsData == null) {
            return;
        }
        final GemSocketsData gemSocketsData2 = (GemSocketsData)mmoItemReforgeEvent.getNewMMOItem().getData(ItemStats.GEM_SOCKETS);
        final ArrayList<GemstoneData> list = new ArrayList<GemstoneData>();
        if (gemSocketsData2 != null) {
            final ArrayList<GemstoneData> list2 = new ArrayList<GemstoneData>();
            final ArrayList list3 = new ArrayList<String>(gemSocketsData2.getEmptySlots());
            for (final GemstoneData e : new ArrayList<GemstoneData>(gemSocketsData.getGemstones())) {
                if (list3.size() <= 0) {
                    list.add(e);
                }
                else {
                    String s = e.getSocketColor();
                    if (s == null) {
                        s = GemSocketsData.getUncoloredGemSlot();
                    }
                    String s2 = null;
                    for (final String anObject : list3) {
                        if (anObject.equals(GemSocketsData.getUncoloredGemSlot()) || s.equals(anObject)) {
                            s2 = anObject;
                        }
                    }
                    if (s2 != null) {
                        list3.remove(s2);
                        e.setColour(s2);
                        list2.add(e);
                        for (final StatHistory statHistory : mmoItemReforgeEvent.getOldMMOItem().getStatHistories()) {
                            for (final UUID uuid : statHistory.getAllGemstones()) {
                                if (uuid.equals(e.getHistoricUUID())) {
                                    final StatData gemstoneData = statHistory.getGemstoneData(uuid);
                                    if (!(gemstoneData instanceof Mergeable)) {
                                        continue;
                                    }
                                    StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), statHistory.getItemStat()).registerGemstoneData(uuid, ((Mergeable)gemstoneData).cloneData());
                                }
                            }
                        }
                    }
                    else {
                        list.add(e);
                    }
                }
            }
            final GemSocketsData originalData = new GemSocketsData((List<String>)list3);
            for (final GemstoneData gemstoneData2 : list2) {
                if (gemstoneData2 == null) {
                    continue;
                }
                originalData.add(gemstoneData2);
            }
            StatHistory.from(mmoItemReforgeEvent.getNewMMOItem(), ItemStats.GEM_SOCKETS).setOriginalData(originalData);
        }
        else {
            list.addAll(gemSocketsData.getGemstones());
        }
        if (ReforgeOptions.dropRestoredGems) {
            final Iterator<GemstoneData> iterator6 = list.iterator();
            while (iterator6.hasNext()) {
                final MMOItem gemstone = mmoItemReforgeEvent.getOldMMOItem().extractGemstone(iterator6.next());
                if (gemstone != null) {
                    mmoItemReforgeEvent.getReforger().addReforgingOutput(gemstone.newBuilder().build());
                }
            }
        }
    }
}
