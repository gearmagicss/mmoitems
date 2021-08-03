// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.edition.input;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.edition.Edition;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Listener;

public class AnvilGUI extends PlayerInputHandler implements Listener
{
    private final int containerId;
    private final Inventory inventory;
    private boolean open;
    
    public AnvilGUI(final Edition edition) {
        super(edition);
        final ItemStack itemStack = new ItemStack(Material.PAPER);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Input text..");
        itemStack.setItemMeta(itemMeta);
        MythicLib.plugin.getVersion().getWrapper().handleInventoryCloseEvent(this.getPlayer());
        MythicLib.plugin.getVersion().getWrapper().setActiveContainerDefault(this.getPlayer());
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
        final Object containerAnvil = MythicLib.plugin.getVersion().getWrapper().newContainerAnvil(this.getPlayer());
        (this.inventory = MythicLib.plugin.getVersion().getWrapper().toBukkitInventory(containerAnvil)).setItem(0, itemStack);
        this.containerId = MythicLib.plugin.getVersion().getWrapper().getNextContainerId(this.getPlayer());
        MythicLib.plugin.getVersion().getWrapper().sendPacketOpenWindow(this.getPlayer(), this.containerId);
        MythicLib.plugin.getVersion().getWrapper().setActiveContainer(this.getPlayer(), containerAnvil);
        MythicLib.plugin.getVersion().getWrapper().setActiveContainerId(containerAnvil, this.containerId);
        MythicLib.plugin.getVersion().getWrapper().addActiveContainerSlotListener(containerAnvil, this.getPlayer());
        this.open = true;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    @Override
    public void close() {
        if (!this.open) {
            return;
        }
        this.open = false;
        MythicLib.plugin.getVersion().getWrapper().handleInventoryCloseEvent(this.getPlayer());
        MythicLib.plugin.getVersion().getWrapper().setActiveContainerDefault(this.getPlayer());
        MythicLib.plugin.getVersion().getWrapper().sendPacketCloseWindow(this.getPlayer(), this.containerId);
        HandlerList.unregisterAll((Listener)this);
    }
    
    @EventHandler
    public void a(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().equals(this.inventory)) {
            inventoryClickEvent.setCancelled(true);
            if (inventoryClickEvent.getRawSlot() == 2) {
                final ItemStack item = this.inventory.getItem(inventoryClickEvent.getRawSlot());
                if (item != null && item.getType() != Material.AIR) {
                    this.registerInput(item.hasItemMeta() ? item.getItemMeta().getDisplayName() : item.getType().toString());
                }
            }
        }
    }
    
    @EventHandler
    public void b(final InventoryCloseEvent inventoryCloseEvent) {
        if (this.open && inventoryCloseEvent.getInventory().equals(this.inventory)) {
            this.close();
        }
    }
}
