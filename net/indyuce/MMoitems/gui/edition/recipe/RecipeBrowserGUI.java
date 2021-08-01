// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe;

import java.util.Set;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.RMGRR_LBCampfire;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.RMGRR_LBSmoker;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.RMGRR_LBBlast;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.RMGRR_LBFurnace;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RMGRR_Shaped;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RMGRR_Shapeless;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RMGRR_Smithing;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.Iterator;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;

public class RecipeBrowserGUI extends EditionInventory
{
    @NotNull
    final ItemStack nextPage;
    @NotNull
    final ItemStack prevPage;
    @NotNull
    final ItemStack noRecipe;
    int currentPage;
    @NotNull
    final HashMap<Integer, RecipeRegistry> recipeTypeMap;
    @NotNull
    static final HashMap<String, RecipeRegistry> registeredRecipes;
    
    public RecipeBrowserGUI(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
        this.nextPage = ItemFactory.of(Material.ARROW).name(FFPMMOItems.get().getExampleFormat() + "Next Page").build();
        this.prevPage = ItemFactory.of(Material.ARROW).name(FFPMMOItems.get().getExampleFormat() + "Previous Page").build();
        this.noRecipe = ItemFactory.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).name("").build();
        this.recipeTypeMap = new HashMap<Integer, RecipeRegistry>();
        this.currentPage = 0;
    }
    
    @NotNull
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Choose Recipe Type");
        this.addEditionInventoryItems(inventory, true);
        this.arrangeInventory(inventory);
        return inventory;
    }
    
    void arrangeInventory(@NotNull final Inventory inventory) {
        this.recipeTypeMap.clear();
        if (this.currentPage > 0) {
            inventory.setItem(27, this.prevPage);
        }
        if (RecipeBrowserGUI.registeredRecipes.size() >= (this.currentPage + 1) * 21) {
            inventory.setItem(36, this.nextPage);
        }
        final HashMap<Integer, RecipeRegistry> hashMap = new HashMap<Integer, RecipeRegistry>();
        int i = 0;
        final Iterator<RecipeRegistry> iterator = RecipeBrowserGUI.registeredRecipes.values().iterator();
        while (iterator.hasNext()) {
            hashMap.put(i, iterator.next());
            ++i;
        }
        for (int j = 21 * this.currentPage; j < 21 * (this.currentPage + 1); ++j) {
            final int page = page(j);
            if (j >= RecipeBrowserGUI.registeredRecipes.size()) {
                inventory.setItem(page, this.noRecipe);
            }
            else {
                final RecipeRegistry value = hashMap.get(j);
                inventory.setItem(page, value.getDisplayListItem());
                this.recipeTypeMap.put(page, value);
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
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            if (inventoryClickEvent.getSlot() == 27) {
                --this.currentPage;
                this.arrangeInventory(inventoryClickEvent.getView().getTopInventory());
            }
            else if (inventoryClickEvent.getSlot() == 36) {
                ++this.currentPage;
                this.arrangeInventory(inventoryClickEvent.getView().getTopInventory());
            }
            else if (inventoryClickEvent.getSlot() > 18) {
                final RecipeRegistry recipeRegistry = this.recipeTypeMap.get(inventoryClickEvent.getSlot());
                if (recipeRegistry != null) {
                    new RecipeListGUI(this.player, this.template, recipeRegistry).open(this.getPreviousPage());
                }
            }
        }
    }
    
    public static void registerNativeRecipes() {
        registerRecipe(new RMGRR_Smithing());
        registerRecipe(new RMGRR_Shapeless());
        registerRecipe(new RMGRR_Shaped());
        registerRecipe(new RMGRR_LBFurnace());
        registerRecipe(new RMGRR_LBBlast());
        registerRecipe(new RMGRR_LBSmoker());
        registerRecipe(new RMGRR_LBCampfire());
    }
    
    public static void registerRecipe(@NotNull final RecipeRegistry value) {
        RecipeBrowserGUI.registeredRecipes.put(value.getRecipeConfigPath(), value);
    }
    
    @NotNull
    public static Set<String> getRegisteredRecipes() {
        return RecipeBrowserGUI.registeredRecipes.keySet();
    }
    
    @NotNull
    public static RecipeRegistry getRegisteredRecipe(@NotNull final String key) {
        return RecipeBrowserGUI.registeredRecipes.get(key);
    }
    
    static {
        registeredRecipes = new HashMap<String, RecipeRegistry>();
    }
}
