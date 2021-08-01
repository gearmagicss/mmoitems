// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.itemglow;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.Listener;

public class NoGlowListener implements Listener
{
    @EventHandler
    public void a(final ItemSpawnEvent itemSpawnEvent) {
        final ItemStack itemStack = itemSpawnEvent.getEntity().getItemStack();
        final String string = NBTItem.get(itemStack).getString("MMOITEMS_TIER");
        if (MMOItems.plugin.getTiers().has(string) && MMOItems.plugin.getTiers().get(string).isHintEnabled()) {
            itemSpawnEvent.getEntity().setCustomNameVisible(true);
            itemSpawnEvent.getEntity().setCustomName(itemStack.getItemMeta().getDisplayName());
        }
    }
}
