// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class LoreUpdate
{
    private final ItemStack item;
    private final String old;
    private final String replace;
    private final List<String> lore;
    
    public LoreUpdate(final ItemStack item, final String old, final String replace) {
        this.item = item;
        this.old = old;
        this.replace = replace;
        this.lore = (List<String>)item.getItemMeta().getLore();
    }
    
    public ItemStack updateLore() {
        for (int i = 0; i < this.lore.size(); ++i) {
            if (this.lore.get(i).equals(this.old)) {
                this.lore.set(i, this.replace);
                final ItemMeta itemMeta = this.item.getItemMeta();
                itemMeta.setLore((List)this.lore);
                this.item.setItemMeta(itemMeta);
                return this.item;
            }
        }
        return this.item;
    }
}
