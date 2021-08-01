// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Keyed;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class DisableInteractions implements Listener
{
    @EventHandler
    public void a(final InventoryClickEvent inventoryClickEvent) {
        final Inventory clickedInventory = inventoryClickEvent.getClickedInventory();
        if (clickedInventory == null || clickedInventory.getType() != InventoryType.ANVIL || inventoryClickEvent.getSlotType() != InventoryType.SlotType.RESULT) {
            return;
        }
        if (this.isDisabled(NBTItem.get(inventoryClickEvent.getCurrentItem()), "repair")) {
            inventoryClickEvent.setCancelled(true);
        }
        else if (clickedInventory.getItem(1) != null && this.isDisabled(NBTItem.get(clickedInventory.getItem(1)), "repair")) {
            inventoryClickEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void b(final InventoryClickEvent inventoryClickEvent) {
        if (MythicLib.plugin.getVersion().isBelowOrEqual(new int[] { 1, 13 })) {
            return;
        }
        final Inventory clickedInventory = inventoryClickEvent.getClickedInventory();
        if (clickedInventory == null || clickedInventory.getType() != InventoryType.GRINDSTONE || inventoryClickEvent.getSlotType() != InventoryType.SlotType.RESULT) {
            return;
        }
        if (this.isDisabled(NBTItem.get(clickedInventory.getItem(0)), "repair") || this.isDisabled(NBTItem.get(clickedInventory.getItem(1)), "repair")) {
            inventoryClickEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void c(final InventoryClickEvent inventoryClickEvent) {
        if (MythicLib.plugin.getVersion().isBelowOrEqual(new int[] { 1, 15 })) {
            return;
        }
        final Inventory clickedInventory = inventoryClickEvent.getClickedInventory();
        if (clickedInventory == null || clickedInventory.getType() != InventoryType.SMITHING || inventoryClickEvent.getSlotType() != InventoryType.SlotType.RESULT) {
            return;
        }
        if (this.isDisabled(NBTItem.get(clickedInventory.getItem(0)), "smith") || this.isDisabled(NBTItem.get(clickedInventory.getItem(1)), "smith")) {
            inventoryClickEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void d(final EnchantItemEvent enchantItemEvent) {
        if (this.isDisabled(NBTItem.get(enchantItemEvent.getItem()), "enchant")) {
            enchantItemEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void e(final FurnaceSmeltEvent furnaceSmeltEvent) {
        if (this.isDisabled(NBTItem.get(furnaceSmeltEvent.getSource()), "smelt")) {
            furnaceSmeltEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void f(final PlayerInteractEvent playerInteractEvent) {
        if (!playerInteractEvent.hasItem()) {
            return;
        }
        if (NBTItem.get(playerInteractEvent.getItem()).getBoolean("MMOITEMS_DISABLE_INTERACTION")) {
            playerInteractEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void g(final PlayerInteractEntityEvent playerInteractEntityEvent) {
        if (playerInteractEntityEvent.getRightClicked() instanceof ArmorStand) {
            return;
        }
        if (NBTItem.get((playerInteractEntityEvent.getHand() == EquipmentSlot.OFF_HAND) ? playerInteractEntityEvent.getPlayer().getInventory().getItemInOffHand() : playerInteractEntityEvent.getPlayer().getInventory().getItemInMainHand()).getBoolean("MMOITEMS_DISABLE_INTERACTION")) {
            playerInteractEntityEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void h(final PlayerItemConsumeEvent playerItemConsumeEvent) {
        if (NBTItem.get(playerItemConsumeEvent.getItem()).getBoolean("MMOITEMS_DISABLE_INTERACTION")) {
            playerItemConsumeEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void i(final CraftItemEvent craftItemEvent) {
        if (craftItemEvent.getRecipe() instanceof Keyed && ((Keyed)craftItemEvent.getRecipe()).getKey().getNamespace().equals("mmoitems")) {
            final String string = NBTItem.get(craftItemEvent.getCurrentItem()).getString("MMOITEMS_CRAFT_PERMISSION");
            if (!string.isEmpty() && !craftItemEvent.getWhoClicked().hasPermission(string)) {
                craftItemEvent.setCancelled(true);
            }
            return;
        }
        final ItemStack[] matrix = craftItemEvent.getInventory().getMatrix();
        for (int length = matrix.length, i = 0; i < length; ++i) {
            if (this.isDisabled(NBTItem.get(matrix[i]), "craft")) {
                craftItemEvent.setCancelled(true);
                return;
            }
        }
        if (MMOItems.plugin.getConfig().getStringList("disable-vanilla-recipes").contains(craftItemEvent.getCurrentItem().getType().name())) {
            craftItemEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void j(final EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getEntity() instanceof Player)) {
            return;
        }
        if (new DurabilityItem(((Player)entityShootBowEvent.getEntity()).getPlayer(), entityShootBowEvent.getBow()).isBroken()) {
            entityShootBowEvent.setCancelled(true);
        }
        final Player player = (Player)entityShootBowEvent.getEntity();
        final int firstArrow = this.firstArrow(player);
        if (firstArrow < 0) {
            return;
        }
        final ItemStack item = player.getInventory().getItem(firstArrow);
        if (item == null) {
            return;
        }
        final NBTItem value = NBTItem.get(item);
        if ((value.hasType() && MMOItems.plugin.getConfig().getBoolean("disable-interactions.arrow-shooting")) || value.getBoolean("MMOITEMS_DISABLE_ARROW_SHOOTING")) {
            entityShootBowEvent.setCancelled(true);
        }
    }
    
    private int firstArrow(final Player player) {
        if (player.getInventory().getItemInOffHand() != null && player.getInventory().getItemInOffHand().getType().name().contains("ARROW")) {
            return 40;
        }
        final ItemStack[] storageContents = player.getInventory().getStorageContents();
        for (int i = 0; i < storageContents.length; ++i) {
            final ItemStack itemStack = storageContents[i];
            if (itemStack != null && itemStack.getType().name().contains("ARROW")) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isDisabled(final NBTItem nbtItem, final String str) {
        return (nbtItem.hasType() && MMOItems.plugin.getConfig().getBoolean("disable-interactions." + str)) || nbtItem.getBoolean("MMOITEMS_DISABLE_" + str.toUpperCase().replace("-", "_") + "ING");
    }
    
    @EventHandler
    public void playerAttack(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamage() == 0.0 || entityDamageByEntityEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || !(entityDamageByEntityEvent.getEntity() instanceof LivingEntity) || !(entityDamageByEntityEvent.getDamager() instanceof Player) || entityDamageByEntityEvent.getEntity().hasMetadata("NPC") || entityDamageByEntityEvent.getDamager().hasMetadata("NPC")) {
            return;
        }
        final Player player = (Player)entityDamageByEntityEvent.getDamager();
        if (new DurabilityItem(player, player.getInventory().getItemInMainHand()).isBroken()) {
            entityDamageByEntityEvent.setCancelled(true);
        }
    }
}
