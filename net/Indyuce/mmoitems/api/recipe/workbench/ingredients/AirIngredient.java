// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench.ingredients;

import org.bukkit.inventory.RecipeChoice;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AirIngredient extends WorkbenchIngredient
{
    public AirIngredient() {
        super(0);
    }
    
    @Override
    public boolean matches(final ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR;
    }
    
    public boolean corresponds(final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public ItemStack generateItem() {
        return new ItemStack(Material.AIR);
    }
    
    @Override
    public RecipeChoice toBukkit() {
        return (RecipeChoice)new RecipeChoice.MaterialChoice(Material.AIR);
    }
}
