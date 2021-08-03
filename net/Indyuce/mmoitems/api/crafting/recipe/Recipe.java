// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.recipe;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;
import net.Indyuce.mmoitems.api.crafting.IngredientInventory;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import java.util.Set;
import java.util.Map;

public abstract class Recipe
{
    private final String id;
    private final Map<RecipeOption, Boolean> options;
    private final Set<Ingredient> ingredients;
    private final Set<Condition> conditions;
    private final Set<Trigger> triggers;
    
    public Recipe(final ConfigurationSection configurationSection) {
        this(configurationSection.getName());
        if (configurationSection.contains("options")) {
            for (final RecipeOption recipeOption : RecipeOption.values()) {
                if (configurationSection.getConfigurationSection("options").contains(recipeOption.getConfigPath())) {
                    this.options.put(recipeOption, configurationSection.getBoolean("options." + recipeOption.getConfigPath()));
                }
            }
        }
        for (final String str : configurationSection.getStringList("ingredients")) {
            try {
                final Ingredient ingredient = MMOItems.plugin.getCrafting().getIngredient(new MMOLineConfig(str));
                Validate.notNull((Object)ingredient, "Could not match ingredient");
                this.ingredients.add(ingredient);
            }
            catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Could not load ingredient '" + str + "': " + ex.getMessage());
            }
        }
        for (final String str2 : configurationSection.getStringList("conditions")) {
            try {
                final Condition condition = MMOItems.plugin.getCrafting().getCondition(new MMOLineConfig(str2));
                Validate.notNull((Object)condition, "Could not match condition");
                this.conditions.add(condition);
            }
            catch (IllegalArgumentException ex2) {
                throw new IllegalArgumentException("Could not load condition '" + str2 + "': " + ex2.getMessage());
            }
        }
        if (this.conditions.isEmpty() && this.ingredients.isEmpty()) {
            throw new IllegalArgumentException("No conditions or ingredients set.");
        }
        for (final String str3 : configurationSection.getStringList("triggers")) {
            try {
                final Trigger trigger = MMOItems.plugin.getCrafting().getTrigger(new MMOLineConfig(str3));
                Validate.notNull((Object)trigger, "Could not match trigger");
                this.triggers.add(trigger);
            }
            catch (IllegalArgumentException ex3) {
                throw new IllegalArgumentException("Could not load trigger '" + str3 + "': " + ex3.getMessage());
            }
        }
    }
    
    private Recipe(final String id) {
        this.options = new HashMap<RecipeOption, Boolean>();
        this.ingredients = new LinkedHashSet<Ingredient>();
        this.conditions = new LinkedHashSet<Condition>();
        this.triggers = new LinkedHashSet<Trigger>();
        Validate.notNull((Object)id, "Recipe ID must not be null");
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public Set<Ingredient> getIngredients() {
        return this.ingredients;
    }
    
    public Set<Condition> getConditions() {
        return this.conditions;
    }
    
    public Set<Trigger> getTriggers() {
        return this.triggers;
    }
    
    public Condition getCondition(final String anObject) {
        for (final Condition condition : this.conditions) {
            if (condition.getId().equals(anObject)) {
                return condition;
            }
        }
        return null;
    }
    
    public boolean hasOption(final RecipeOption key) {
        return this.options.getOrDefault(key, key.getDefault());
    }
    
    public void addIngredient(final Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    
    public void registerCondition(final Condition condition) {
        this.conditions.add(condition);
    }
    
    public void setOption(final RecipeOption recipeOption, final boolean b) {
        this.options.put(recipeOption, b);
    }
    
    public CheckedRecipe evaluateRecipe(final PlayerData playerData, final IngredientInventory ingredientInventory) {
        return new CheckedRecipe(this, playerData, ingredientInventory);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Recipe && ((Recipe)o).id.equals(this.id);
    }
    
    public abstract void whenUsed(final PlayerData p0, final IngredientInventory p1, final CheckedRecipe p2, final CraftingStation p3);
    
    public abstract boolean canUse(final PlayerData p0, final IngredientInventory p1, final CheckedRecipe p2, final CraftingStation p3);
    
    public abstract ItemStack display(final CheckedRecipe p0);
    
    public enum RecipeOption
    {
        HIDE_WHEN_LOCKED(false), 
        HIDE_WHEN_NO_INGREDIENTS(false), 
        OUTPUT_ITEM(true), 
        SILENT_CRAFT(false);
        
        private final boolean def;
        
        private RecipeOption(final boolean def) {
            this.def = def;
        }
        
        public boolean getDefault() {
            return this.def;
        }
        
        public String getConfigPath() {
            return this.name().toLowerCase().replace("_", "-");
        }
    }
}
