// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui;

import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.crafting.recipe.UpgradingRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.CraftingRecipe;
import net.Indyuce.mmoitems.api.item.util.ConfigItems;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.inventory.Inventory;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.recipe.CheckedRecipe;

public class CraftingStationPreview extends PluginInventory
{
    private final CraftingStationView previous;
    private final CheckedRecipe recipe;
    private final List<ItemStack> ingredients;
    private static final int[] slots;
    private static final int[] fill;
    
    public CraftingStationPreview(final CraftingStationView previous, final CheckedRecipe recipe) {
        super(previous.getPlayer());
        this.ingredients = new ArrayList<ItemStack>();
        this.previous = previous;
        this.recipe = recipe;
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 45, Message.RECIPE_PREVIEW.formatRaw(ChatColor.RESET, new String[0]));
        this.ingredients.clear();
        for (final Ingredient.CheckedIngredient checkedIngredient : this.recipe.getIngredients()) {
            if (checkedIngredient.getIngredient().getAmount() > 64) {
                final ItemStack generateItemStack = checkedIngredient.getIngredient().generateItemStack(this.playerData.getRPG());
                generateItemStack.setAmount(64);
                int i = checkedIngredient.getIngredient().getAmount();
                SilentNumbers.floor(i / 64.0);
                while (i > 0) {
                    if (i > 64) {
                        this.ingredients.add(generateItemStack.clone());
                        i -= 64;
                    }
                    else {
                        generateItemStack.setAmount(i);
                        this.ingredients.add(generateItemStack.clone());
                        i -= i;
                    }
                }
            }
            else {
                this.ingredients.add(checkedIngredient.getIngredient().generateItemStack(this.playerData.getRPG()));
            }
        }
        final int n = (this.page - 1) * CraftingStationPreview.slots.length;
        final int n2 = this.page * CraftingStationPreview.slots.length;
        for (int n3 = n; n3 < n2 && n3 < this.ingredients.size(); ++n3) {
            inventory.setItem(CraftingStationPreview.slots[n3 - n], (ItemStack)this.ingredients.get(n3));
        }
        final int[] fill = CraftingStationPreview.fill;
        for (int length = fill.length, j = 0; j < length; ++j) {
            inventory.setItem(fill[j], ConfigItems.FILL.getItem());
        }
        if (this.recipe.getRecipe() instanceof CraftingRecipe) {
            final ItemStack preview = ((CraftingRecipe)this.recipe.getRecipe()).getOutput().getPreview();
            preview.setAmount(((CraftingRecipe)this.recipe.getRecipe()).getOutput().getAmount());
            inventory.setItem(16, preview);
        }
        if (this.recipe.getRecipe() instanceof UpgradingRecipe) {
            final NBTItem value = NBTItem.get(((UpgradingRecipe)this.recipe.getRecipe()).getItem().getPreview());
            value.setDisplayNameComponent(LegacyComponent.parse(value.toItem().getItemMeta().getDisplayName() + ChatColor.GREEN + "+1!"));
            inventory.setItem(16, value.toItem());
        }
        inventory.setItem(10, ConfigItems.BACK.getItem());
        inventory.setItem(34, ConfigItems.CONFIRM.getItem());
        final ItemStack display = this.recipe.display();
        display.setType(Material.KNOWLEDGE_BOOK);
        display.setAmount(1);
        final ItemMeta itemMeta = display.getItemMeta();
        final Iterator<Enchantment> iterator2 = itemMeta.getEnchants().keySet().iterator();
        while (iterator2.hasNext()) {
            itemMeta.removeEnchant((Enchantment)iterator2.next());
        }
        display.setItemMeta(itemMeta);
        final NBTItem value2 = NBTItem.get(display);
        final List loreComponents = value2.getLoreComponents();
        value2.setLoreComponents((List)loreComponents.subList(0, loreComponents.size() - 3));
        inventory.setItem(28, value2.toItem());
        inventory.setItem(20, (this.page > 1) ? ConfigItems.PREVIOUS_PAGE.getItem() : ConfigItems.FILL.getItem());
        inventory.setItem(24, (n2 < this.ingredients.size()) ? ConfigItems.NEXT_PAGE.getItem() : ConfigItems.FILL.getItem());
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if (!MMOUtils.isMetaItem(inventoryClickEvent.getCurrentItem(), false)) {
            return;
        }
        final String string = MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCurrentItem()).getString("ItemId");
        switch (string) {
            case "CONFIRM": {
                this.previous.processRecipe(this.recipe);
                this.previous.open();
                break;
            }
            case "PREVIOUS_PAGE": {
                --this.page;
                this.open();
                break;
            }
            case "NEXT_PAGE": {
                ++this.page;
                this.open();
                break;
            }
            case "BACK": {
                this.previous.open();
                break;
            }
        }
    }
    
    static {
        slots = new int[] { 12, 13, 14, 21, 22, 23, 30, 31, 32 };
        fill = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 15, 17, 18, 19, 25, 26, 27, 29, 33, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44 };
    }
}
