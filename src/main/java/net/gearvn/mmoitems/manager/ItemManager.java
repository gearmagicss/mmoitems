// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.Type;

public class ItemManager
{
    @Deprecated
    public MMOItem getMMOItem(final Type type, final String s) {
        return MMOItems.plugin.getMMOItem(type, s);
    }
    
    @Deprecated
    public ItemStack getItem(final Type type, final String s) {
        return MMOItems.plugin.getItem(type, s);
    }
}
