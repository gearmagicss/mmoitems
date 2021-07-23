// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.inventory.InventoryAction;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;

public class CommandListEdition extends EditionInventory
{
    private static final int[] slots;
    
    public CommandListEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Command List");
        int i = 0;
        if (this.getEditedSection().contains("commands")) {
            for (final String s : this.getEditedSection().getConfigurationSection("commands").getKeys(false)) {
                final String string = this.getEditedSection().getString("commands." + s + ".format");
                final double double1 = this.getEditedSection().getDouble("commands." + s + ".delay");
                final boolean boolean1 = this.getEditedSection().getBoolean("commands." + s + ".console");
                final boolean boolean2 = this.getEditedSection().getBoolean("commands." + s + ".op");
                final ItemStack itemStack = new ItemStack(VersionMaterial.COMPARATOR.toMaterial());
                final ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName((string == null || string.equals("")) ? (ChatColor.RED + "No Format") : (ChatColor.GREEN + string));
                final ArrayList<String> lore = new ArrayList<String>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Command Delay: " + ChatColor.RED + double1);
                lore.add(ChatColor.GRAY + "Sent by Console: " + ChatColor.RED + boolean1);
                lore.add(ChatColor.GRAY + "Sent w/ OP perms: " + ChatColor.RED + boolean2);
                lore.add("");
                lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove.");
                itemMeta.setLore((List)lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(CommandListEdition.slots[i++], NBTItem.get(itemStack).addTag(new ItemTag[] { new ItemTag("configKey", (Object)s) }).toItem());
            }
        }
        final ItemStack item = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta2 = item.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.RED + "- No Command -");
        item.setItemMeta(itemMeta2);
        final ItemStack itemStack2 = new ItemStack(VersionMaterial.WRITABLE_BOOK.toMaterial());
        final ItemMeta itemMeta3 = itemStack2.getItemMeta();
        itemMeta3.setDisplayName(ChatColor.GREEN + "Register a command...");
        itemStack2.setItemMeta(itemMeta3);
        inventory.setItem(40, itemStack2);
        while (i < CommandListEdition.slots.length) {
            inventory.setItem(CommandListEdition.slots[i++], item);
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
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Register a command...")) {
            new StatEdition(this, ItemStats.COMMANDS, new Object[0]).enable("Write in the chat the command you want to add.", "", "To add a delay, use " + ChatColor.RED + "-d:<delay>", "To make the command cast itself w/ console, use " + ChatColor.RED + "-c", "To make the command cast w/ OP perms, use " + ChatColor.RED + "-op", "", ChatColor.YELLOW + "Ex: -d:10.3 -op bc Hello, this is a test command.");
            return;
        }
        final String string = MythicLib.plugin.getVersion().getWrapper().getNBTItem(currentItem).getString("configKey");
        if (string.equals("")) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("commands") && this.getEditedSection().getConfigurationSection("commands").contains(string)) {
            this.getEditedSection().set("commands." + string, (Object)null);
            this.registerTemplateEdition();
            this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + ChatColor.GOLD + string + ChatColor.DARK_GRAY + " (Internal ID)" + ChatColor.GRAY + ".");
        }
    }
    
    static {
        slots = new int[] { 19, 20, 21, 22, 23, 24, 25, 28, 29, 33, 34, 37, 38, 42, 43 };
    }
}
