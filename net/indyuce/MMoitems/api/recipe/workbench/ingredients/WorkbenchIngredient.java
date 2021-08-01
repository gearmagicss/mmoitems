// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench.ingredients;

import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ItemStack;

public abstract class WorkbenchIngredient
{
    private final int amount;
    
    public WorkbenchIngredient(final int amount) {
        this.amount = amount;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public boolean matches(final ItemStack itemStack) {
        return itemStack != null && itemStack.getAmount() >= this.amount && this.corresponds(itemStack);
    }
    
    public abstract RecipeChoice toBukkit();
    
    public abstract ItemStack generateItem();
    
    protected abstract boolean corresponds(final ItemStack p0);
}
