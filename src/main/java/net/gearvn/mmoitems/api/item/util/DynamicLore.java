// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util;

import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import com.google.gson.JsonElement;
import io.lumine.mythic.utils.adventure.text.Component;
import java.util.ArrayList;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonArray;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.NBTItem;

public class DynamicLore
{
    private final NBTItem item;
    
    public DynamicLore(final NBTItem item) {
        this.item = item;
    }
    
    public ItemStack build() {
        if (this.item.hasTag("MMOITEMS_DYNAMIC_LORE")) {
            final JsonArray jsonArray = (JsonArray)MythicLib.plugin.getJson().parse(this.item.getString("MMOITEMS_DYNAMIC_LORE"), (Class)JsonArray.class);
            final ArrayList loreComponents = new ArrayList<Component>(jsonArray.size());
            final Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                final String replace = this.replace(iterator.next().getAsString());
                if (!replace.equals("!INVALID!")) {
                    loreComponents.add(LegacyComponent.parse(replace));
                }
            }
            this.item.setLoreComponents((List)loreComponents);
        }
        return this.item.toItem();
    }
    
    private String replace(final String s) {
        final String lowerCase = s.toLowerCase();
        switch (lowerCase) {
            case "%durability%": {
                if (this.item.hasTag("MMOITEMS_DURABILITY") && this.item.hasTag("MMOITEMS_MAX_DURABILITY")) {
                    return MMOItems.plugin.getLanguage().getDynLoreFormat("durability").replace("%durability%", "" + this.item.getInteger("MMOITEMS_DURABILITY")).replace("%max_durability%", "" + this.item.getInteger("MMOITEMS_MAX_DURABILITY"));
                }
                return "!INVALID!";
            }
            default: {
                return s;
            }
        }
    }
}
