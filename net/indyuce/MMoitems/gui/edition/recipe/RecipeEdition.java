// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe;

import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import net.Indyuce.mmoitems.manager.RecipeManager;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Arrays;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;

public class RecipeEdition extends EditionInventory
{
    private final boolean shapeless;
    
    public RecipeEdition(final Player player, final MMOItemTemplate mmoItemTemplate, final boolean shapeless) {
        super(player, mmoItemTemplate);
        this.shapeless = shapeless;
    }
    
    @Override
    public Inventory getInventory() {
        return this.shapeless ? this.setupShapelessInventory() : this.setupShapedInventory();
    }
    
    private Inventory setupShapedInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Recipe Editor: " + this.template.getId());
        if (!this.getEditedSection().contains("crafting.shaped.1")) {
            this.getEditedSection().set("crafting.shaped.1", (Object)new String[] { "AIR AIR AIR", "AIR AIR AIR", "AIR AIR AIR" });
            this.registerTemplateEdition();
        }
        final List stringList = this.getEditedSection().getStringList("crafting.shaped.1");
        if (stringList.size() < 3) {
            while (stringList.size() < 3) {
                stringList.add("AIR AIR AIR");
            }
            this.getEditedSection().set("crafting.shaped.1", (Object)stringList);
            this.registerTemplateEdition();
        }
        for (int i = 0; i < 9; ++i) {
            final int intToSlot = this.intToSlot(i);
            final List<String> list = Arrays.asList(stringList.get(i / 3).split(" "));
            while (list.size() < 3) {
                list.add("AIR");
            }
            ItemStack generateItem;
            try {
                MMOItems.plugin.getRecipes();
                final WorkbenchIngredient workbenchIngredient = RecipeManager.getWorkbenchIngredient(list.get(i % 3));
                generateItem = workbenchIngredient.generateItem();
                generateItem.setAmount(workbenchIngredient.getAmount());
                Validate.isTrue(generateItem != null && generateItem.getType() != Material.AIR);
            }
            catch (IllegalArgumentException ex) {
                generateItem = new ItemStack(Material.BARRIER);
            }
            final ItemMeta itemMeta = generateItem.getItemMeta();
            if (generateItem.getType() == Material.BARRIER) {
                itemMeta.setDisplayName(ChatColor.RED + "Empty");
            }
            final ArrayList<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this ingredient.");
            lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this ingredient.");
            itemMeta.setLore((List)lore);
            generateItem.setItemMeta(itemMeta);
            inventory.setItem(intToSlot, generateItem);
        }
        this.addEditionInventoryItems(inventory, true);
        return inventory;
    }
    
    private Inventory setupShapelessInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Recipe Editor: " + this.template.getId());
        if (!this.getEditedSection().contains("crafting.shapeless.1")) {
            this.getEditedSection().set("crafting.shapeless.1", (Object)Arrays.asList("AIR", "AIR", "AIR", "AIR", "AIR", "AIR", "AIR", "AIR", "AIR"));
            this.registerTemplateEdition();
        }
        final List stringList = this.getEditedSection().getStringList("crafting.shapeless.1");
        if (stringList.size() == 9) {
            for (int i = 0; i < 9; ++i) {
                final int intToSlot = this.intToSlot(i);
                ItemStack generateItem;
                try {
                    MMOItems.plugin.getRecipes();
                    final WorkbenchIngredient workbenchIngredient = RecipeManager.getWorkbenchIngredient(stringList.get(i));
                    generateItem = workbenchIngredient.generateItem();
                    generateItem.setAmount(workbenchIngredient.getAmount());
                    Validate.isTrue(generateItem != null && generateItem.getType() != Material.AIR);
                }
                catch (IllegalArgumentException ex) {
                    generateItem = new ItemStack(Material.BARRIER);
                }
                final ItemMeta itemMeta = generateItem.getItemMeta();
                if (generateItem.getType() == Material.BARRIER) {
                    itemMeta.setDisplayName(ChatColor.RED + "Empty");
                }
                final ArrayList<String> lore = new ArrayList<String>();
                lore.add("");
                lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this ingredient.");
                lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this ingredient.");
                itemMeta.setLore((List)lore);
                generateItem.setItemMeta(itemMeta);
                inventory.setItem(intToSlot, generateItem);
            }
        }
        else {
            MMOItems.plugin.getLogger().warning("Couldn't load shapeless recipe for '" + this.template.getId() + "'!");
        }
        this.addEditionInventoryItems(inventory, true);
        return inventory;
    }
    
    private int intToSlot(final int n) {
        return (n >= 0 && n <= 2) ? (21 + n) : ((n >= 3 && n <= 5) ? (27 + n) : ((n >= 6 && n <= 8) ? (33 + n) : 0));
    }
    
    private int slotToInt(final int n) {
        return (n >= 21 && n <= 23) ? (n - 21) : ((n >= 30 && n <= 32) ? (n - 27) : ((n >= 39 && n <= 41) ? (n - 33) : -1));
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory()) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            if (this.slotToInt(inventoryClickEvent.getRawSlot()) >= 0) {
                new StatEdition(this, ItemStats.CRAFTING, new Object[] { "recipe", this.shapeless ? "shapeless" : "shaped", this.slotToInt(inventoryClickEvent.getRawSlot()) }).enable("Write in the chat the item you want.", "Format: '[MATERIAL]' or '[TYPE].[ID]'");
            }
        }
        else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            if (this.shapeless) {
                this.deleteShapeless(this.slotToInt(inventoryClickEvent.getRawSlot()));
            }
            else {
                this.deleteShaped(this.slotToInt(inventoryClickEvent.getRawSlot()));
            }
        }
    }
    
    private void deleteShaped(final int n) {
        final List stringList = this.getEditedSection().getStringList("crafting.shaped.1");
        final String[] split = stringList.get(n / 3).split(" ");
        split[n % 3] = "AIR";
        stringList.set(n / 3, split[0] + " " + split[1] + " " + split[2]);
        this.getEditedSection().set("crafting.shaped.1", (Object)stringList);
        this.registerTemplateEdition();
    }
    
    private void deleteShapeless(final int n) {
        if (this.getEditedSection().contains("crafting.shapeless.1")) {
            final List stringList = this.getEditedSection().getStringList("crafting.shapeless.1");
            stringList.set(n, "AIR");
            this.getEditedSection().set("crafting.shapeless.1", (Object)stringList);
            this.registerTemplateEdition();
        }
    }
}
