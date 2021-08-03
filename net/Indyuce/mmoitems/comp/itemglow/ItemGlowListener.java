// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.itemglow;

import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.api.ItemTier;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import org.bukkit.entity.Entity;
import org.inventivetalent.glow.GlowAPI;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.Listener;

public class ItemGlowListener implements Listener
{
    @EventHandler
    public void a(final ItemSpawnEvent itemSpawnEvent) {
        final ItemStack itemStack = itemSpawnEvent.getEntity().getItemStack();
        final String string = NBTItem.get(itemStack).getString("MMOITEMS_TIER");
        if (MMOItems.plugin.getTiers().has(string)) {
            final ItemTier value = MMOItems.plugin.getTiers().get(string);
            if (value.isHintEnabled()) {
                itemSpawnEvent.getEntity().setCustomNameVisible(true);
                itemSpawnEvent.getEntity().setCustomName(itemStack.getItemMeta().getDisplayName());
            }
            if (value.hasColor()) {
                GlowAPI.setGlowing((Entity)itemSpawnEvent.getEntity(), value.getColor().toGlow().get(), (Collection)itemSpawnEvent.getEntity().getWorld().getPlayers());
            }
        }
    }
    
    @EventHandler
    public void b(final PlayerLoginEvent playerLoginEvent) {
        final Iterator<Item> iterator = playerLoginEvent.getPlayer().getWorld().getEntitiesByClass((Class)Item.class).iterator();
        while (iterator.hasNext()) {
            final String string = NBTItem.get(iterator.next().getItemStack()).getString("MMOITEMS_TIER");
            if (MMOItems.plugin.getTiers().has(string) && MMOItems.plugin.getTiers().get(string).hasColor()) {
                final Entity entity;
                final ItemTier itemTier;
                final Player player;
                Bukkit.getScheduler().runTaskAsynchronously((Plugin)MMOItems.plugin, () -> GlowAPI.setGlowing(entity, itemTier.getColor().toGlow().get(), player));
            }
        }
    }
    
    @EventHandler
    public void c(final PlayerTeleportEvent playerTeleportEvent) {
        if (playerTeleportEvent.getFrom().getWorld().equals(playerTeleportEvent.getTo().getWorld())) {
            return;
        }
        final Iterator<Item> iterator = playerTeleportEvent.getPlayer().getWorld().getEntitiesByClass((Class)Item.class).iterator();
        while (iterator.hasNext()) {
            final String string = NBTItem.get(iterator.next().getItemStack()).getString("MMOITEMS_TIER");
            if (MMOItems.plugin.getTiers().has(string) && MMOItems.plugin.getTiers().get(string).hasColor()) {
                final Entity entity;
                final ItemTier itemTier;
                final Player player;
                Bukkit.getScheduler().runTaskAsynchronously((Plugin)MMOItems.plugin, () -> GlowAPI.setGlowing(entity, itemTier.getColor().toGlow().get(), player));
            }
        }
    }
}
