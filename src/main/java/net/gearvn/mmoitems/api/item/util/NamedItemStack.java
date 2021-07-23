// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util;

import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NamedItemStack extends ItemStack
{
    public NamedItemStack(final Material material, final String s) {
        super(material);
        final ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(MythicLib.plugin.parseColors(s));
        this.setItemMeta(itemMeta);
    }
}
