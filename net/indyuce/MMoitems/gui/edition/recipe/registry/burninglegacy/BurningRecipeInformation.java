// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy;

import net.Indyuce.mmoitems.manager.RecipeManager;
import org.jetbrains.annotations.NotNull;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;

public class BurningRecipeInformation
{
    private final WorkbenchIngredient choice;
    private final float exp;
    private final int burnTime;
    
    public BurningRecipeInformation(@NotNull final ConfigurationSection configurationSection) {
        final String string = configurationSection.getString("item");
        if (string == null) {
            throw new IllegalArgumentException("Invalid input ingredient");
        }
        this.choice = RecipeManager.getWorkbenchIngredient(string);
        this.exp = (float)configurationSection.getDouble("exp", 0.35);
        this.burnTime = configurationSection.getInt("time", 200);
    }
    
    public BurningRecipeInformation(@NotNull final WorkbenchIngredient choice, final float exp, final int burnTime) {
        this.choice = choice;
        this.exp = exp;
        this.burnTime = burnTime;
    }
    
    public int getBurnTime() {
        return this.burnTime;
    }
    
    public WorkbenchIngredient getChoice() {
        return this.choice;
    }
    
    public float getExp() {
        return this.exp;
    }
}
