// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.api.item.util.NamedItemStack;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.Material;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradingEdition extends EditionInventory
{
    private static final ItemStack notAvailable;
    
    public UpgradingEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Upgrade Setup: " + this.template.getId());
        final boolean boolean1 = this.getEditedSection().getBoolean("upgrade.workbench");
        if (!this.template.getType().corresponds(Type.CONSUMABLE)) {
            final ItemStack itemStack = new ItemStack(VersionMaterial.CRAFTING_TABLE.toMaterial());
            final ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Workbench Upgrade Only?");
            final ArrayList<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY + "When toggled on, players must");
            lore.add(ChatColor.GRAY + "use a crafting station recipe in");
            lore.add(ChatColor.GRAY + "order to upgrade their weapon.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + boolean1);
            lore.add("");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
            itemMeta.setLore((List)lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(20, itemStack);
            final String string = this.getEditedSection().getString("upgrade.template");
            final ItemStack itemStack2 = new ItemStack(VersionMaterial.OAK_SIGN.toMaterial());
            final ItemMeta itemMeta2 = itemStack2.getItemMeta();
            itemMeta2.setDisplayName(ChatColor.GREEN + "Upgrade Template");
            final ArrayList<String> lore2 = new ArrayList<String>();
            lore2.add(ChatColor.GRAY + "This option dictates what stats are improved");
            lore2.add(ChatColor.GRAY + "when your item is upgraded. More info on the wiki.");
            lore2.add("");
            lore2.add(ChatColor.GRAY + "Current Value: " + ((string == null) ? (ChatColor.RED + "No template") : (ChatColor.GOLD + string)));
            lore2.add("");
            lore2.add(ChatColor.YELLOW + "\u25ba" + " Click to input the template.");
            lore2.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
            itemMeta2.setLore((List)lore2);
            itemStack2.setItemMeta(itemMeta2);
            inventory.setItem(22, itemStack2);
            final int int1 = this.getEditedSection().getInt("upgrade.max");
            final ItemStack itemStack3 = new ItemStack(Material.BARRIER);
            final ItemMeta itemMeta3 = itemStack3.getItemMeta();
            itemMeta3.setDisplayName(ChatColor.GREEN + "Max Upgrades");
            final ArrayList<String> lore3 = new ArrayList<String>();
            lore3.add(ChatColor.GRAY + "The maximum amount of upgrades your");
            lore3.add(ChatColor.GRAY + "item may receive (recipe or consumable).");
            lore3.add("");
            lore3.add(ChatColor.GRAY + "Current Value: " + ((int1 == 0) ? (ChatColor.RED + "No limit") : (ChatColor.GOLD + "" + int1)));
            lore3.add("");
            lore3.add(ChatColor.YELLOW + "\u25ba" + " Click to chance this value.");
            lore3.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
            itemMeta3.setLore((List)lore3);
            itemStack3.setItemMeta(itemMeta3);
            inventory.setItem(40, itemStack3);
        }
        else {
            inventory.setItem(20, UpgradingEdition.notAvailable);
            inventory.setItem(22, UpgradingEdition.notAvailable);
        }
        if (!boolean1 || this.template.getType().corresponds(Type.CONSUMABLE)) {
            final String string2 = this.getEditedSection().getString("upgrade.reference");
            final ItemStack itemStack4 = new ItemStack(Material.PAPER);
            final ItemMeta itemMeta4 = itemStack4.getItemMeta();
            itemMeta4.setDisplayName(ChatColor.GREEN + "Upgrade Reference");
            final ArrayList<String> lore4 = new ArrayList<String>();
            lore4.add(ChatColor.GRAY + "This option dictates what consumables can");
            lore4.add(ChatColor.GRAY + "upgrade your item. " + ChatColor.AQUA + "The consumable upgrade");
            lore4.add(ChatColor.AQUA + "reference must match your item's reference" + ChatColor.GRAY + ",");
            lore4.add(ChatColor.GRAY + "otherwise it can't upgrade it. Leave this blank");
            lore4.add(ChatColor.GRAY + "so any consumable can upgrade this item.");
            lore4.add("");
            lore4.add(ChatColor.GRAY + "Current Value: " + ((string2 == null) ? (ChatColor.RED + "No reference") : (ChatColor.GOLD + string2)));
            lore4.add("");
            lore4.add(ChatColor.YELLOW + "\u25ba" + " Click to input the reference.");
            lore4.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
            itemMeta4.setLore((List)lore4);
            itemStack4.setItemMeta(itemMeta4);
            inventory.setItem(38, itemStack4);
        }
        else {
            inventory.setItem(38, UpgradingEdition.notAvailable);
        }
        final double double1 = this.getEditedSection().getDouble("upgrade.success");
        final ItemStack itemStack5 = new ItemStack(VersionMaterial.EXPERIENCE_BOTTLE.toMaterial());
        final ItemMeta itemMeta5 = itemStack5.getItemMeta();
        itemMeta5.setDisplayName(ChatColor.GREEN + "Success Chance");
        final ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "The chance of successfully upgrading");
        lore5.add(ChatColor.GRAY + "when using a consumable or when using");
        lore5.add(ChatColor.GRAY + "a station upgrading recipe.");
        lore5.add("");
        lore5.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + ((double1 == 0.0) ? "100" : ("" + double1)) + "%");
        lore5.add("");
        lore5.add(ChatColor.YELLOW + "\u25ba" + " Left click to change this value.");
        lore5.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
        itemMeta5.setLore((List)lore5);
        itemStack5.setItemMeta(itemMeta5);
        inventory.setItem(24, itemStack5);
        if (double1 > 0.0 && !this.template.getType().corresponds(Type.CONSUMABLE)) {
            final ItemStack itemStack6 = new ItemStack(Material.FISHING_ROD);
            final ItemMeta itemMeta6 = itemStack6.getItemMeta();
            ((Damageable)itemMeta6).setDamage(30);
            itemMeta6.setDisplayName(ChatColor.GREEN + "Destroy on fail?");
            final ArrayList<String> lore6 = new ArrayList<String>();
            lore6.add(ChatColor.GRAY + "When toggled on, the item will be");
            lore6.add(ChatColor.GRAY + "destroyed when failing at upgrading it.");
            lore6.add("");
            lore6.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + this.getEditedSection().getBoolean("upgrade.destroy"));
            lore6.add("");
            lore6.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
            itemMeta6.setLore((List)lore6);
            itemStack6.setItemMeta(itemMeta6);
            inventory.setItem(42, itemStack6);
        }
        this.addEditionInventoryItems(inventory, true);
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory() || !MMOUtils.isMetaItem(currentItem, false)) {
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Success Chance")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.UPGRADE, new Object[] { "rate" }).enable("Write in the chat the success rate you want.");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("upgrade.success")) {
                this.getEditedSection().set("upgrade.success", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset success chance.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Max Upgrades")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.UPGRADE, new Object[] { "max" }).enable("Write in the chat the number you want.");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("upgrade.max")) {
                this.getEditedSection().set("upgrade.max", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the number of max upgrades.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Upgrade Template")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.UPGRADE, new Object[] { "template" }).enable("Write in the chat the upgrade template ID you want.");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("upgrade.template")) {
                this.getEditedSection().set("upgrade.template", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset upgrade template.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Upgrade Reference")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.UPGRADE, new Object[] { "ref" }).enable("Write in the chat the upgrade reference (text) you want.");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("upgrade.reference")) {
                this.getEditedSection().set("upgrade.reference", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset upgrade reference.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Workbench Upgrade Only?")) {
            final boolean b = !this.getEditedSection().getBoolean("upgrade.workbench");
            this.getEditedSection().set("upgrade.workbench", (Object)b);
            this.registerTemplateEdition();
            this.player.sendMessage(MMOItems.plugin.getPrefix() + (b ? "Your item must now be upgraded via recipes." : "Your item can now be upgraded using consumables."));
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Destroy on fail?")) {
            final boolean b2 = !this.getEditedSection().getBoolean("upgrade.destroy");
            this.getEditedSection().set("upgrade.destroy", (Object)b2);
            this.registerTemplateEdition();
            this.player.sendMessage(MMOItems.plugin.getPrefix() + (b2 ? "Your item will be destroyed upon failing upgrade." : "Your item will not be destroyed upon failing upgrade."));
        }
    }
    
    static {
        notAvailable = new NamedItemStack(VersionMaterial.RED_STAINED_GLASS_PANE.toMaterial(), "&cNot Available");
    }
}
