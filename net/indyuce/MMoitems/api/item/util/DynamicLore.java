// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util;

import io.lumine.mythic.lib.api.util.LegacyComponent;
import com.google.gson.JsonElement;
import io.lumine.mythic.utils.adventure.text.Component;
import java.util.ArrayList;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonArray;
import java.util.Iterator;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.DynamicLoreStat;
import java.util.List;
import io.lumine.mythic.lib.api.item.NBTItem;

@Deprecated
public class DynamicLore
{
    private final NBTItem item;
    
    public DynamicLore(final NBTItem item) {
        this.item = item;
    }
    
    public void update(final List<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, this.applyDynamicLore(list.get(i)));
        }
    }
    
    private DynamicLoreStat getCorrespondingStat(final String anObject) {
        for (final DynamicLoreStat dynamicLoreStat : MMOItems.plugin.getStats().getDynamicLores()) {
            if (("%" + dynamicLoreStat.getDynamicLoreId() + "%").equals(anObject)) {
                return dynamicLoreStat;
            }
        }
        return null;
    }
    
    private String applyDynamicLore(final String s) {
        if (!s.startsWith("%") || !s.endsWith("%")) {
            return s;
        }
        final DynamicLoreStat correspondingStat = this.getCorrespondingStat(s);
        if (correspondingStat == null) {
            return "<StatNotFound>";
        }
        return correspondingStat.calculatePlaceholder(this.item);
    }
    
    public static void update(final NBTItem nbtItem) {
        if (!nbtItem.hasTag("MMOITEMS_DYNAMIC_LORE")) {
            return;
        }
        final DynamicLore dynamicLore = new DynamicLore(nbtItem);
        final JsonArray jsonArray = (JsonArray)MythicLib.plugin.getJson().parse(nbtItem.getString("MMOITEMS_DYNAMIC_LORE"), (Class)JsonArray.class);
        final ArrayList loreComponents = new ArrayList<Component>(jsonArray.size());
        for (final JsonElement jsonElement : jsonArray) {
            if (jsonElement == null) {
                continue;
            }
            final String asString = jsonElement.getAsString();
            if (asString == null) {
                continue;
            }
            loreComponents.add(LegacyComponent.parse(dynamicLore.applyDynamicLore(asString)));
        }
        nbtItem.setLoreComponents((List)loreComponents);
    }
}
