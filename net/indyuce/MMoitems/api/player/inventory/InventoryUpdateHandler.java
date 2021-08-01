// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player.inventory;

import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.plugin.Plugin;
import java.util.Objects;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.PlayerInventory;
import java.util.Iterator;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.Indyuce.mmoitems.api.player.PlayerData;

public class InventoryUpdateHandler
{
    private final PlayerData player;
    private final List<EquippedPlayerItem> items;
    @Deprecated
    public ItemStack helmet;
    @Deprecated
    public ItemStack chestplate;
    @Deprecated
    public ItemStack leggings;
    @Deprecated
    public ItemStack boots;
    @Deprecated
    public ItemStack hand;
    @Deprecated
    public ItemStack offhand;
    
    public InventoryUpdateHandler(final PlayerData player) {
        this.items = new ArrayList<EquippedPlayerItem>();
        this.helmet = null;
        this.chestplate = null;
        this.leggings = null;
        this.boots = null;
        this.hand = null;
        this.offhand = null;
        this.player = player;
    }
    
    public List<EquippedPlayerItem> getEquipped() {
        return this.items;
    }
    
    @Deprecated
    public boolean hasItemWithType(final EquipmentSlot equipmentSlot) {
        final Iterator<EquippedPlayerItem> iterator = this.items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getSlot() == equipmentSlot) {
                return true;
            }
        }
        return false;
    }
    
    public void updateCheck() {
        if (!this.player.isOnline()) {
            return;
        }
        final PlayerInventory inventory = this.player.getPlayer().getInventory();
        if (this.isNotSame(this.helmet, inventory.getHelmet()) || this.isNotSame(this.chestplate, inventory.getChestplate()) || this.isNotSame(this.leggings, inventory.getLeggings()) || this.isNotSame(this.boots, inventory.getBoots()) || this.isNotSame(this.hand, inventory.getItemInMainHand()) || this.isNotSame(this.offhand, inventory.getItemInOffHand())) {
            this.player.updateInventory();
        }
    }
    
    public void scheduleUpdate() {
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        final MMOItems plugin = MMOItems.plugin;
        final PlayerData player = this.player;
        Objects.requireNonNull(player);
        scheduler.scheduleSyncDelayedTask((Plugin)plugin, player::updateInventory);
    }
    
    private boolean isNotSame(final ItemStack a, final ItemStack b) {
        return !Objects.equals(a, b);
    }
}
