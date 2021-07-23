// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.crafting.IngredientInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.manager.CraftingManager;
import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;
import io.lumine.mythic.lib.api.MMOLineConfig;

public abstract class Ingredient
{
    private final String id;
    private final int amount;
    
    public Ingredient(final String s, final MMOLineConfig mmoLineConfig) {
        this(s, mmoLineConfig.getInt("amount", 1));
    }
    
    public Ingredient(final String id, final int amount) {
        this.id = id;
        this.amount = amount;
    }
    
    public String getId() {
        return this.id;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public ConditionalDisplay getDisplay() {
        return MMOItems.plugin.getCrafting().getIngredients().stream().filter(ingredientType -> ingredientType.getId().equals(this.id)).findAny().orElse(null).getDisplay();
    }
    
    public abstract String getKey();
    
    public abstract String formatDisplay(final String p0);
    
    @NotNull
    public abstract ItemStack generateItemStack(@NotNull final RPGPlayer p0);
    
    public CheckedIngredient evaluateIngredient(@NotNull final IngredientInventory ingredientInventory) {
        return new CheckedIngredient(this, ingredientInventory.getIngredient(this, IngredientInventory.IngredientLookupMode.BASIC));
    }
    
    public static class CheckedIngredient
    {
        @NotNull
        private final Ingredient ingredient;
        @Nullable
        private final IngredientInventory.PlayerIngredient found;
        
        private CheckedIngredient(@NotNull final Ingredient ingredient, @Nullable final IngredientInventory.PlayerIngredient found) {
            this.ingredient = ingredient;
            this.found = found;
        }
        
        public boolean isHad() {
            return this.found != null && this.found.getAmount() >= this.ingredient.getAmount();
        }
        
        @NotNull
        public Ingredient getIngredient() {
            return this.ingredient;
        }
        
        @Nullable
        public IngredientInventory.PlayerIngredient getPlayerIngredient() {
            return this.found;
        }
        
        @NotNull
        public String format() {
            return this.ingredient.formatDisplay(this.isHad() ? this.ingredient.getDisplay().getPositive() : this.ingredient.getDisplay().getNegative());
        }
    }
}
