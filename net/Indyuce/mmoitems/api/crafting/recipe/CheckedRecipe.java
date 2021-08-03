// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.recipe;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import java.util.LinkedHashSet;
import net.Indyuce.mmoitems.api.crafting.IngredientInventory;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import java.util.Set;

public class CheckedRecipe
{
    private final Recipe recipe;
    private final Set<Condition.CheckedCondition> conditions;
    private final Set<Ingredient.CheckedIngredient> ingredients;
    private boolean ingredientsHad;
    private boolean conditionsMet;
    
    public CheckedRecipe(final Recipe recipe, final PlayerData playerData, final IngredientInventory ingredientInventory) {
        this.conditions = new LinkedHashSet<Condition.CheckedCondition>();
        this.ingredients = new LinkedHashSet<Ingredient.CheckedIngredient>();
        this.ingredientsHad = true;
        this.conditionsMet = true;
        this.recipe = recipe;
        final Iterator<Ingredient> iterator = recipe.getIngredients().iterator();
        while (iterator.hasNext()) {
            final Ingredient.CheckedIngredient evaluateIngredient = iterator.next().evaluateIngredient(ingredientInventory);
            this.ingredients.add(evaluateIngredient);
            if (!evaluateIngredient.isHad()) {
                this.ingredientsHad = false;
            }
        }
        final Iterator<Condition> iterator2 = recipe.getConditions().iterator();
        while (iterator2.hasNext()) {
            final Condition.CheckedCondition evaluateCondition = iterator2.next().evaluateCondition(playerData);
            this.conditions.add(evaluateCondition);
            if (!evaluateCondition.isMet()) {
                this.conditionsMet = false;
            }
        }
    }
    
    public Recipe getRecipe() {
        return this.recipe;
    }
    
    @Deprecated
    public boolean isUnlocked() {
        return this.ingredientsHad && this.conditionsMet;
    }
    
    public boolean areConditionsMet() {
        return this.conditionsMet;
    }
    
    public boolean allIngredientsHad() {
        return this.ingredientsHad;
    }
    
    public ItemStack display() {
        return this.recipe.display(this);
    }
    
    public Set<Condition.CheckedCondition> getConditions() {
        return this.conditions;
    }
    
    public Set<Condition.CheckedCondition> getDisplayableConditions() {
        return this.conditions.stream().filter(checkedCondition -> checkedCondition.getCondition().getDisplay() != null).collect((Collector<? super Object, ?, Set<Condition.CheckedCondition>>)Collectors.toSet());
    }
    
    public Set<Ingredient.CheckedIngredient> getIngredients() {
        return this.ingredients;
    }
}
