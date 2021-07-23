// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import org.bukkit.event.EventHandler;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.MythicLib;
import n3kas.ae.api.EnchantApplyEvent;
import org.bukkit.event.Listener;

public class AdvancedEnchantmentsHook implements Listener
{
    @EventHandler
    public void a(final EnchantApplyEvent enchantApplyEvent) {
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(enchantApplyEvent.getItem());
        if (nbtItem.getType() != null && nbtItem.getBoolean("MMOITEMS_DISABLE_ADVANCED_ENCHANTS")) {
            enchantApplyEvent.setCancelled(true);
        }
    }
}
