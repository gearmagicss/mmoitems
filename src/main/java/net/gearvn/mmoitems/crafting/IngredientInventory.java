// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import java.util.Iterator;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.manager.CraftingManager;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.Material;
import java.util.HashMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import java.util.Map;

public class IngredientInventory
{
    private final Map<String, PlayerIngredient> ingredients;
    
    public IngredientInventory(final Player player) {
        this((Inventory)player.getInventory());
    }
    
    public IngredientInventory(final Inventory inventory) {
        this.ingredients = new HashMap<String, PlayerIngredient>();
        for (final ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack);
                for (final CraftingManager.IngredientType ingredientType : MMOItems.plugin.getCrafting().getIngredients()) {
                    if (ingredientType.check(nbtItem)) {
                        this.addIngredient(nbtItem, ingredientType);
                        break;
                    }
                }
            }
        }
    }
    
    public void addIngredient(final NBTItem nbtItem, final CraftingManager.IngredientType ingredientType) {
        final String string = ingredientType.getId() + ":" + ingredientType.readKey(nbtItem);
        if (this.ingredients.containsKey(string)) {
            this.ingredients.get(string).add(nbtItem.getItem());
        }
        else {
            this.ingredients.put(string, new PlayerIngredient(nbtItem.getItem()));
        }
    }
    
    @Nullable
    public PlayerIngredient getIngredient(@NotNull final Ingredient ingredient, @NotNull final IngredientLookupMode ingredientLookupMode) {
        String anObject = ingredient.getKey();
        QuickNumberRange fromString = null;
        final int index = anObject.indexOf(45);
        if (index > 0) {
            final String substring = anObject.substring(index + 1);
            final String substring2 = substring.substring(0, substring.indexOf(95));
            fromString = QuickNumberRange.getFromString(substring2);
            anObject = anObject.substring(0, index) + anObject.substring(index + 1 + substring2.length());
        }
        for (final String s : this.ingredients.keySet()) {
            final int index2 = s.indexOf(45);
            Integer n = null;
            String string = s;
            if (index2 > 0) {
                final String substring3 = s.substring(index2 + 1);
                final String substring4 = substring3.substring(0, substring3.indexOf(95));
                n = SilentNumbers.IntegerParse(substring4);
                string = s.substring(0, index2) + s.substring(index2 + 1 + substring4.length());
            }
            if (string.equals(anObject)) {
                boolean inRange = true;
                if (ingredientLookupMode != IngredientLookupMode.IGNORE_ITEM_LEVEL && fromString != null) {
                    if (n == null) {
                        n = 0;
                    }
                    inRange = fromString.inRange((double)n);
                }
                if (inRange) {
                    return this.ingredients.get(s);
                }
                continue;
            }
        }
        return null;
    }
    
    @Deprecated
    public boolean hasIngredient(final Ingredient ingredient) {
        final PlayerIngredient ingredient2 = this.getIngredient(ingredient, IngredientLookupMode.IGNORE_ITEM_LEVEL);
        return ingredient2 != null && ingredient2.getAmount() >= ingredient.getAmount();
    }
    
    public static class PlayerIngredient
    {
        private final List<ItemStack> items;
        
        public PlayerIngredient(final ItemStack itemStack) {
            (this.items = new ArrayList<ItemStack>()).add(itemStack);
        }
        
        public int getAmount() {
            int n = 0;
            final Iterator<ItemStack> iterator = this.items.iterator();
            while (iterator.hasNext()) {
                n += iterator.next().getAmount();
            }
            return n;
        }
        
        public void add(final ItemStack itemStack) {
            this.items.add(itemStack);
        }
        
        public ItemStack getFirstItem() {
            return this.items.get(0);
        }
        
        public void reduceItem(int b) {
            final Iterator<ItemStack> iterator = this.items.iterator();
            while (iterator.hasNext() && b > 0) {
                final ItemStack itemStack = iterator.next();
                if (itemStack.getAmount() < 1) {
                    iterator.remove();
                }
                else {
                    final int min = Math.min(itemStack.getAmount(), b);
                    b -= min;
                    itemStack.setAmount(itemStack.getAmount() - min);
                }
            }
        }
    }
    
    public enum IngredientLookupMode
    {
        IGNORE_ITEM_LEVEL, 
        BASIC;
    }
}
