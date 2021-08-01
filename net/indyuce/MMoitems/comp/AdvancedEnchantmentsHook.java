// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import org.bukkit.event.EventHandler;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.MythicLib;
import net.advancedplugins.ae.api.EnchantApplyEvent;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.bukkit.event.Listener;

public class AdvancedEnchantmentsHook implements Listener
{
    public static final ItemStat ADVANCED_ENCHANTMENTS;
    
    @EventHandler
    public void a(final EnchantApplyEvent enchantApplyEvent) {
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(enchantApplyEvent.getItem());
        if (nbtItem.getType() != null && nbtItem.getBoolean("MMOITEMS_DISABLE_ADVANCED_ENCHANTS")) {
            enchantApplyEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onReforge(final MMOItemReforgeEvent mmoItemReforgeEvent) {
        if (!mmoItemReforgeEvent.getOptions().shouldKeepAdvancedEnchants()) {
            return;
        }
        final StatData data = mmoItemReforgeEvent.getOldMMOItem().getData(AdvancedEnchantmentsHook.ADVANCED_ENCHANTMENTS);
        if (data == null) {
            return;
        }
        mmoItemReforgeEvent.getNewMMOItem().setData(AdvancedEnchantmentsHook.ADVANCED_ENCHANTMENTS, data);
    }
    
    static {
        ADVANCED_ENCHANTMENTS = new AdvancedEnchantsStat();
    }
}
