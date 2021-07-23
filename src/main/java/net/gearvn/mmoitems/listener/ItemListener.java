// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import net.Indyuce.mmoitems.ItemStats;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.ReforgeOptions;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.player.PlayerJoinEvent;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.Listener;

public class ItemListener implements Listener
{
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
    private ItemStack modifyItem(@NotNull final ItemStack itemStack, @NotNull final Player player, @NotNull final String s) {
        final NBTItem value = NBTItem.get(itemStack);
        if (!value.hasType()) {
            return null;
        }
        final MMOItemReforger mmoItemReforger = new MMOItemReforger(value);
        if (this.shouldUpdate(value, s)) {
            if (MMOItems.plugin.getLanguage().rerollOnItemUpdate) {
                mmoItemReforger.reforge(player, MMOItems.plugin.getLanguage().revisionOptions);
            }
            else {
                mmoItemReforger.update(player, MMOItems.plugin.getLanguage().revisionOptions);
            }
        }
        if (this.shouldSoulbind(value, s)) {
            mmoItemReforger.applySoulbound(player);
        }
        if (!mmoItemReforger.hasChanges()) {
            return null;
        }
        final ItemStack stack = mmoItemReforger.toStack();
        if (ReforgeOptions.dropRestoredGems) {
            final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            final Iterator<MMOItem> iterator = mmoItemReforger.getDestroyedGems().iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next().newBuilder().build());
            }
            for (final ItemStack itemStack2 : player.getInventory().addItem((ItemStack[])list.toArray(new ItemStack[0])).values()) {
                if (SilentNumbers.isAir(itemStack2)) {
                    continue;
                }
                player.getWorld().dropItem(player.getLocation(), itemStack2);
            }
        }
        return stack;
    }
    
    private boolean shouldSoulbind(final NBTItem nbtItem, final String str) {
        return nbtItem.getBoolean("MMOITEMS_AUTO_SOULBIND") && !nbtItem.hasTag("MMOITEMS_SOULBOUND") && !MMOItems.plugin.getConfig().getBoolean("soulbound.auto-bind.disable-on." + str);
    }
    
    private boolean shouldUpdate(final NBTItem nbtItem, final String str) {
        return MMOItems.plugin.getTemplates().hasTemplate(nbtItem) && !MMOItems.plugin.getConfig().getBoolean("item-revision.disable-on." + str) && (MMOItems.plugin.getTemplates().getTemplate(nbtItem).getRevisionId() > (nbtItem.hasTag(ItemStats.REVISION_ID.getNBTPath()) ? nbtItem.getInteger(ItemStats.REVISION_ID.getNBTPath()) : 1) || 1 > (nbtItem.hasTag(ItemStats.INTERNAL_REVISION_ID.getNBTPath()) ? nbtItem.getInteger(ItemStats.INTERNAL_REVISION_ID.getNBTPath()) : 1));
    }
}
