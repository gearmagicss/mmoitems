// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe;

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
import net.Indyuce.mmoitems.api.recipe.CraftingType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;

public class RecipeListEdition extends EditionInventory
{
    public RecipeListEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Crafting Recipes: " + this.template.getId());
        for (final CraftingType craftingType : CraftingType.values()) {
            if (craftingType.shouldAdd()) {
                final ItemStack item = craftingType.getItem();
                final ItemMeta itemMeta = item.getItemMeta();
                itemMeta.addItemFlags(ItemFlag.values());
                itemMeta.setDisplayName(ChatColor.GREEN + craftingType.getName());
                final ArrayList<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GRAY + craftingType.getLore());
                lore.add("");
                lore.add(this.getEditedSection().contains("crafting." + craftingType.name().toLowerCase()) ? (ChatColor.GREEN + "Found one or more recipe(s).") : (ChatColor.RED + "No recipes found."));
                lore.add("");
                lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this recipe.");
                lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove recipe.");
                itemMeta.setLore((List)lore);
                item.setItemMeta(itemMeta);
                inventory.setItem(craftingType.getSlot(), item);
            }
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
        final CraftingType bySlot = CraftingType.getBySlot(inventoryClickEvent.getSlot());
        if (bySlot == null) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            if (bySlot == CraftingType.SHAPELESS || bySlot == CraftingType.SHAPED) {
                new RecipeEdition(this.player, this.template, bySlot == CraftingType.SHAPELESS).open(this.getPreviousPage());
            }
            else if (bySlot == CraftingType.SMITHING) {
                new StatEdition(this, ItemStats.CRAFTING, new Object[] { "smithing" }).enable("Write in the chat the items required to craft this.", "Format: '[ITEM] [ITEM]'", "[ITEM] = '[MATERIAL]' or '[MATERIAL]:[DURABILITY]' or '[TYPE].[ID]'");
            }
            else {
                new StatEdition(this, ItemStats.CRAFTING, new Object[] { "item", bySlot.name().toLowerCase() }).enable("Write in the chat the item, tickspeed and exp you want.", "Format: '[ITEM] [TICKS] [EXP]'", "[ITEM] = '[MATERIAL]' or '[MATERIAL]:[DURABILITY]' or '[TYPE].[ID]'");
            }
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("crafting." + bySlot.name().toLowerCase())) {
            this.getEditedSection().set("crafting." + bySlot.name().toLowerCase(), (Object)null);
            this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + bySlot.getName() + " recipe.");
            if (this.getEditedSection().getConfigurationSection("crafting").getKeys(false).size() == 0) {
                this.getEditedSection().set("crafting", (Object)null);
            }
            this.registerTemplateEdition();
        }
    }
}
