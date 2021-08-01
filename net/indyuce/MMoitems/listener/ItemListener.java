// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import java.util.Iterator;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.event.player.PlayerJoinEvent;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityPickupItemEvent;
import net.Indyuce.mmoitems.listener.reforging.RFFKeepAmount;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepDurability;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepRNG;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepUpgrades;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepSoulbound;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepModifications;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepGems;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepExternalSH;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepEnchantments;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepLore;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.listener.reforging.RFGKeepName;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Listener;

public class ItemListener implements Listener
{
    public static final ItemStack[] A;
    
    public ItemListener() {
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepName(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepLore(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepEnchantments(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepExternalSH(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepGems(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepModifications(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepSoulbound(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepUpgrades(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepRNG(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFGKeepDurability(), (Plugin)MMOItems.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)new RFFKeepAmount(), (Plugin)MMOItems.plugin);
    }
    
    @EventHandler(ignoreCancelled = true)
    private void itemPickup(final EntityPickupItemEvent entityPickupItemEvent) {
        if (!entityPickupItemEvent.getEntity().getType().equals((Object)EntityType.PLAYER)) {
            return;
        }
        final ItemStack modifyItem = this.modifyItem(entityPickupItemEvent.getItem().getItemStack(), (Player)entityPickupItemEvent.getEntity(), "pickup");
        if (modifyItem != null) {
            entityPickupItemEvent.getItem().setItemStack(modifyItem);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    private void itemCraft(final CraftItemEvent craftItemEvent) {
        if (!(craftItemEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        final ItemStack modifyItem = this.modifyItem(craftItemEvent.getCurrentItem(), (Player)craftItemEvent.getWhoClicked(), "craft");
        if (modifyItem != null) {
            craftItemEvent.setCurrentItem(modifyItem);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    private void inventoryMove(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getType() != InventoryType.CRAFTING || !(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        final ItemStack modifyItem = this.modifyItem(inventoryClickEvent.getCurrentItem(), (Player)inventoryClickEvent.getWhoClicked(), "click");
        if (modifyItem != null) {
            inventoryClickEvent.setCurrentItem(modifyItem);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    private void dropItem(final PlayerDropItemEvent playerDropItemEvent) {
        final NBTItem value = NBTItem.get(playerDropItemEvent.getItemDrop().getItemStack());
        if (!MMOItems.plugin.getConfig().getBoolean("soulbound.can-drop") && value.hasTag("MMOITEMS_SOULBOUND")) {
            playerDropItemEvent.setCancelled(true);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerJoin(final PlayerJoinEvent playerJoinEvent) {
        final Player player = playerJoinEvent.getPlayer();
        final ItemStack modifyItem = this.modifyItem(player.getEquipment().getHelmet(), player, "join");
        if (modifyItem != null) {
            player.getEquipment().setHelmet(modifyItem);
        }
        final ItemStack modifyItem2 = this.modifyItem(player.getEquipment().getChestplate(), player, "join");
        if (modifyItem2 != null) {
            player.getEquipment().setChestplate(modifyItem2);
        }
        final ItemStack modifyItem3 = this.modifyItem(player.getEquipment().getLeggings(), player, "join");
        if (modifyItem3 != null) {
            player.getEquipment().setLeggings(modifyItem3);
        }
        final ItemStack modifyItem4 = this.modifyItem(player.getEquipment().getBoots(), player, "join");
        if (modifyItem4 != null) {
            player.getEquipment().setBoots(modifyItem4);
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack modifyItem5 = this.modifyItem(player.getInventory().getItem(i), player, "join");
            if (modifyItem5 != null) {
                player.getInventory().setItem(i, modifyItem5);
            }
        }
        final ItemStack modifyItem6 = this.modifyItem(player.getEquipment().getItemInOffHand(), player, "join");
        if (modifyItem6 != null) {
            player.getEquipment().setItemInOffHand(modifyItem6);
        }
    }
    
    @Nullable
    private ItemStack modifyItem(@Nullable final ItemStack itemStack, @NotNull final Player player, @NotNull final String s) {
        if (itemStack == null) {
            return null;
        }
        if (!itemStack.hasItemMeta()) {
            return null;
        }
        final MMOItemReforger mmoItemReforger = new MMOItemReforger(itemStack);
        if (!mmoItemReforger.shouldReforge(s)) {
            return null;
        }
        mmoItemReforger.setPlayer(player);
        if (!mmoItemReforger.reforge(MMOItems.plugin.getLanguage().revisionOptions)) {
            return null;
        }
        for (final ItemStack itemStack2 : player.getInventory().addItem((ItemStack[])mmoItemReforger.getReforgingOutput().toArray(ItemListener.A)).values()) {
            if (SilentNumbers.isAir(itemStack2)) {
                continue;
            }
            player.getWorld().dropItem(player.getLocation(), itemStack2);
        }
        return mmoItemReforger.getResult();
    }
    
    static {
        A = new ItemStack[0];
    }
}
