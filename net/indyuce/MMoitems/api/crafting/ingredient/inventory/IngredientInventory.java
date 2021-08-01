// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient.inventory;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.crafting.ingredient.CheckedIngredient;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import java.util.HashSet;
import java.util.Iterator;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.Material;
import java.util.HashMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import java.util.Set;
import java.util.Map;

public class IngredientInventory
{
    private final Map<String, Set<PlayerIngredient>> ingredients;
    
    public IngredientInventory(final Player player) {
        this((Inventory)player.getInventory());
    }
    
    public IngredientInventory(final Inventory inventory) {
        this.ingredients = new HashMap<String, Set<PlayerIngredient>>();
        for (final ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getAmount() > 0) {
                final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack);
                for (final IngredientType ingredientType : MMOItems.plugin.getCrafting().getIngredients()) {
                    if (ingredientType.check(nbtItem)) {
                        this.addIngredient(nbtItem, ingredientType);
                        break;
                    }
                }
            }
        }
    }
    
    public void addIngredient(final NBTItem nbtItem, final IngredientType ingredientType) {
        final String id = ingredientType.getId();
        if (this.ingredients.containsKey(id)) {
            this.ingredients.get(id).add(ingredientType.readPlayerIngredient(nbtItem));
        }
        else {
            final HashSet<PlayerIngredient> set = new HashSet<PlayerIngredient>();
            set.add(ingredientType.readPlayerIngredient(nbtItem));
            this.ingredients.put(id, set);
        }
    }
    
    @Nullable
    public CheckedIngredient findMatching(@NotNull final Ingredient ingredient) {
        final HashSet<PlayerIngredient> set = new HashSet<PlayerIngredient>();
        if (!this.ingredients.containsKey(ingredient.getId())) {
            return new CheckedIngredient(ingredient, set);
        }
        for (final PlayerIngredient playerIngredient : this.ingredients.get(ingredient.getId())) {
            if (ingredient.matches(playerIngredient)) {
                set.add(playerIngredient);
            }
        }
        return new CheckedIngredient(ingredient, set);
    }
    
    @Deprecated
    public boolean hasIngredient(final Ingredient ingredient) {
        return this.findMatching(ingredient).isHad();
    }
    
    @Deprecated
    public enum IngredientLookupMode
    {
        IGNORE_ITEM_LEVEL, 
        BASIC;
        
        private static /* synthetic */ IngredientLookupMode[] $values() {
            return new IngredientLookupMode[] { IngredientLookupMode.IGNORE_ITEM_LEVEL, IngredientLookupMode.BASIC };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
