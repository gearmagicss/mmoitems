// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import java.util.HashMap;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import net.Indyuce.mmoitems.api.CustomSound;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import java.util.Map;

public class SoundsEdition extends EditionInventory
{
    public static final Map<Integer, String> correspondingSlot;
    
    public SoundsEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Custom Sounds: " + this.template.getId());
        final int[] array = { 19, 22, 25, 28, 31, 34, 37, 40, 43 };
        int n = 0;
        for (final CustomSound customSound : CustomSound.values()) {
            final ItemStack clone = customSound.getItem().clone();
            final ItemMeta itemMeta = clone.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.setDisplayName(ChatColor.GREEN + customSound.getName());
            final ArrayList<String> lore = new ArrayList<String>();
            final String[] lore2 = customSound.getLore();
            for (int length2 = lore2.length, j = 0; j < length2; ++j) {
                lore.add(ChatColor.GRAY + lore2[j]);
            }
            lore.add("");
            final String lowerCase = customSound.getName().replace(" ", "-").toLowerCase();
            if (this.getEditedSection().getString("sounds." + lowerCase + ".sound") != null) {
                lore.add(ChatColor.GRAY + "Current Values:");
                lore.add(ChatColor.GRAY + " - Sound Name: '" + ChatColor.GREEN + this.getEditedSection().getString("sounds." + lowerCase + ".sound") + ChatColor.GRAY + "'");
                lore.add(ChatColor.GRAY + " - Volume: " + ChatColor.GREEN + this.getEditedSection().getDouble("sounds." + lowerCase + ".volume"));
                lore.add(ChatColor.GRAY + " - Pitch: " + ChatColor.GREEN + this.getEditedSection().getDouble("sounds." + lowerCase + ".pitch"));
            }
            else {
                lore.add(ChatColor.GRAY + "Current Values: " + ChatColor.RED + "None");
            }
            lore.add("");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
            itemMeta.setLore((List)lore);
            clone.setItemMeta(itemMeta);
            inventory.setItem(array[n], clone);
            ++n;
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
        if (SoundsEdition.correspondingSlot.containsKey(inventoryClickEvent.getSlot())) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.CUSTOM_SOUNDS, new Object[] { SoundsEdition.correspondingSlot.get(inventoryClickEvent.getSlot()) }).enable("Write in the chat the custom sound you want to add.", ChatColor.AQUA + "Format: [SOUND NAME] [VOLUME] [PITCH]", ChatColor.AQUA + "Example: entity.generic.drink 1 1");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
                final String s = SoundsEdition.correspondingSlot.get(inventoryClickEvent.getSlot());
                this.getEditedSection().set("sounds." + s, (Object)null);
                if (this.getEditedSection().contains("sounds." + s) && this.getEditedSection().getConfigurationSection("sounds." + s).getKeys(false).isEmpty()) {
                    this.getEditedSection().set("sounds." + s, (Object)null);
                    if (this.getEditedSection().getConfigurationSection("sounds").getKeys(false).isEmpty()) {
                        this.getEditedSection().set("sounds", (Object)null);
                    }
                }
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + MMOUtils.caseOnWords(s.replace("-", " ")) + " Sound" + ChatColor.GRAY + " successfully removed.");
            }
        }
    }
    
    static {
        correspondingSlot = new HashMap<Integer, String>();
        for (final CustomSound customSound : CustomSound.values()) {
            SoundsEdition.correspondingSlot.put(customSound.getSlot(), customSound.getName().replace(" ", "-").toLowerCase());
        }
    }
}
