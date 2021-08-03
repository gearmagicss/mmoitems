// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.DisableStat;

public class DisableAdvancedEnchantments extends DisableStat
{
    public DisableAdvancedEnchantments() {
        super("ADVANCED_ENCHANTS", Material.ENCHANTED_BOOK, "Disable Advanced Enchants", new String[] { "all" }, new String[] { "When toggled on, prevents players", "from applying AE onto this item." });
        if (Bukkit.getPluginManager().getPlugin("AdvancedEnchantments") == null) {
            this.disable();
        }
    }
}
