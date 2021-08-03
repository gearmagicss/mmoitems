// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.edition.input;

import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.edition.Edition;
import org.bukkit.event.Listener;

public class ChatEdition extends PlayerInputHandler implements Listener
{
    public ChatEdition(final Edition edition) {
        super(edition);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
    }
    
    @Override
    public void close() {
        HandlerList.unregisterAll((Listener)this);
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void a(final AsyncPlayerChatEvent asyncPlayerChatEvent) {
        if (this.getPlayer() != null && asyncPlayerChatEvent.getPlayer().equals(this.getPlayer())) {
            asyncPlayerChatEvent.setCancelled(true);
            this.registerInput(asyncPlayerChatEvent.getMessage());
        }
    }
    
    @EventHandler
    public void b(final InventoryOpenEvent inventoryOpenEvent) {
        if (inventoryOpenEvent.getPlayer().equals(this.getPlayer())) {
            this.close();
        }
    }
}
