// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe;

import org.bukkit.configuration.ConfigurationSection;
import java.util.UUID;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import java.util.Iterator;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.ArrayList;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;

public class RecipeListGUI extends EditionInventory
{
    @NotNull
    final ItemStack nextPage;
    @NotNull
    final ItemStack prevPage;
    @NotNull
    final ItemStack noRecipe;
    @NotNull
    final RecipeRegistry recipeType;
    @NotNull
    final ItemStack listedItem;
    @NotNull
    final ArrayList<String> recipeNames;
    boolean invalidRecipe;
    int currentPage;
    int createSlot;
    @NotNull
    final HashMap<Integer, String> recipeMap;
    
    @NotNull
    public RecipeRegistry getRecipeRegistry() {
        return this.recipeType;
    }
    
    @NotNull
    public ItemStack getListedItem() {
        return this.listedItem;
    }
    
    @NotNull
    public ArrayList<String> getRecipeNames() {
        return this.recipeNames;
    }
    
    public RecipeListGUI(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final RecipeRegistry recipeType) {
        super(player, mmoItemTemplate);
        this.nextPage = ItemFactory.of(Material.ARROW).name("§7Next Page").build();
        this.prevPage = ItemFactory.of(Material.ARROW).name("§7Previous Page").build();
        this.noRecipe = ItemFactory.of(Material.BLACK_STAINED_GLASS_PANE).name("§7No Recipe").build();
        this.recipeNames = new ArrayList<String>();
        this.createSlot = -1;
        this.recipeMap = new HashMap<Integer, String>();
        this.recipeType = recipeType;
        this.listedItem = this.getRecipeRegistry().getDisplayListItem();
        for (final String e : RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(this.getEditedSection(), "crafting"), recipeType.getRecipeConfigPath()).getValues(false).keySet()) {
            if (e != null) {
                if (e.isEmpty()) {
                    continue;
                }
                this.recipeNames.add(e);
            }
        }
    }
    
    @NotNull
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Choose " + this.getRecipeRegistry().getRecipeTypeName() + " Recipe");
        this.addEditionInventoryItems(inventory, true);
        this.arrangeInventory(inventory);
        return inventory;
    }
    
    void arrangeInventory(@NotNull final Inventory inventory) {
        this.recipeMap.clear();
        this.createSlot = -1;
        if (this.currentPage > 0) {
            inventory.setItem(27, this.prevPage);
        }
        if (this.recipeNames.size() >= (this.currentPage + 1) * 21) {
            inventory.setItem(36, this.nextPage);
        }
        for (int i = 21 * this.currentPage; i < 21 * (this.currentPage + 1); ++i) {
            final int page = page(i);
            if (i == this.recipeNames.size()) {
                inventory.setItem(page, RecipeMakerGUI.rename(new ItemStack(Material.NETHER_STAR), FFPMMOItems.get().getBodyFormat() + "Create new " + SilentNumbers.getItemName(this.getListedItem(), false)));
                this.createSlot = page;
            }
            else if (i > this.recipeNames.size()) {
                inventory.setItem(page, this.noRecipe);
            }
            else {
                inventory.setItem(page, RecipeMakerGUI.rename(this.getListedItem().clone(), FFPMMOItems.get().getBodyFormat() + "Edit " + FFPMMOItems.get().getInputFormat() + this.recipeNames.get(i)));
                this.recipeMap.put(page, this.recipeNames.get(i));
            }
        }
    }
    
    public static int page(int n) {
        n -= SilentNumbers.floor(n / 21.0) * 21;
        final int floor = SilentNumbers.floor(n / 7.0);
        return 18 + floor * 9 + (n - 7 * floor + 1);
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getView().getTopInventory() != inventoryClickEvent.getClickedInventory()) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        if (this.invalidRecipe) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            if (inventoryClickEvent.getSlot() == 27) {
                --this.currentPage;
                this.arrangeInventory(inventoryClickEvent.getView().getTopInventory());
            }
            else if (inventoryClickEvent.getSlot() == 36) {
                ++this.currentPage;
                this.arrangeInventory(inventoryClickEvent.getView().getTopInventory());
            }
            else if (inventoryClickEvent.getSlot() == this.createSlot) {
                String s = String.valueOf(this.recipeMap.size() + 1);
                if (this.recipeMap.containsValue(s)) {
                    s = s + "_" + UUID.randomUUID();
                }
                this.getRecipeRegistry().openForPlayer(this, s, new Object[0]);
            }
            else if (inventoryClickEvent.getSlot() > 18) {
                final String s2 = this.recipeMap.get(inventoryClickEvent.getSlot());
                if (s2 != null) {
                    this.getRecipeRegistry().openForPlayer(this, s2, new Object[0]);
                }
            }
        }
        else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            final String o = this.recipeMap.get(inventoryClickEvent.getSlot());
            if (o != null) {
                final ConfigurationSection section = RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(this.getEditedSection(), "crafting"), this.getRecipeRegistry().getRecipeConfigPath());
                this.recipeNames.remove(o);
                section.set(o, (Object)null);
                this.arrangeInventory(inventoryClickEvent.getView().getTopInventory());
                this.registerTemplateEdition();
            }
        }
    }
}
