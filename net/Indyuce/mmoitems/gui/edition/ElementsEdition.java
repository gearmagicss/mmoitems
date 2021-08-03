// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import org.bukkit.event.inventory.InventoryAction;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.random.RandomElementListData;
import net.Indyuce.mmoitems.ItemStats;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.Element;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;

public class ElementsEdition extends EditionInventory
{
    private static final int[] slots;
    
    public ElementsEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Elements E.: " + this.template.getId());
        int n = 0;
        for (final Element element : Element.values()) {
            final ItemStack clone = element.getItem().clone();
            final ItemMeta itemMeta = clone.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + element.getName() + " Damage");
            final ArrayList<String> lore = new ArrayList<String>();
            final Optional<RandomStatData> eventualStatData = this.getEventualStatData(ItemStats.ELEMENTS);
            lore.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + ((eventualStatData.isPresent() && eventualStatData.get().hasDamage(element)) ? (eventualStatData.get().getDamage(element) + " (%)") : "---"));
            lore.add("");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
            itemMeta.setLore((List)lore);
            clone.setItemMeta(itemMeta);
            final ItemStack clone2 = element.getItem().clone();
            final ItemMeta itemMeta2 = clone2.getItemMeta();
            itemMeta2.setDisplayName(ChatColor.GREEN + element.getName() + " Defense");
            final ArrayList<String> lore2 = new ArrayList<String>();
            lore2.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + ((eventualStatData.isPresent() && eventualStatData.get().hasDefense(element)) ? (eventualStatData.get().getDefense(element) + " (%)") : "---"));
            lore2.add("");
            lore2.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
            lore2.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
            itemMeta2.setLore((List)lore2);
            clone2.setItemMeta(itemMeta2);
            inventory.setItem(ElementsEdition.slots[n], clone);
            inventory.setItem(ElementsEdition.slots[n + 1], clone2);
            n += 2;
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
        final String elementPath = this.getElementPath(inventoryClickEvent.getSlot());
        if (elementPath == null) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(this, ItemStats.ELEMENTS, new Object[] { elementPath }).enable("Write in the value you want.");
        }
        else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            this.getEditedSection().set("element." + elementPath, (Object)null);
            final String str = elementPath.split("\\.")[0];
            if (this.getEditedSection().contains("element." + str) && this.getEditedSection().getConfigurationSection("element." + str).getKeys(false).isEmpty()) {
                this.getEditedSection().set("element." + str, (Object)null);
                if (this.getEditedSection().getConfigurationSection("element").getKeys(false).isEmpty()) {
                    this.getEditedSection().set("element", (Object)null);
                }
            }
            this.registerTemplateEdition();
            new ElementsEdition(this.player, this.template).open(this.getPreviousPage());
            this.player.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + MMOUtils.caseOnWords(elementPath.replace(".", " ")) + ChatColor.GRAY + " successfully removed.");
        }
    }
    
    public String getElementPath(final int n) {
        for (final Element element : Element.values()) {
            if (element.getDamageGuiSlot() == n) {
                return element.name().toLowerCase() + ".damage";
            }
            if (element.getDefenseGuiSlot() == n) {
                return element.name().toLowerCase() + ".defense";
            }
        }
        return null;
    }
    
    static {
        slots = new int[] { 19, 25, 20, 24, 28, 34, 29, 33, 30, 32, 37, 43, 38, 42, 39, 41 };
    }
}
