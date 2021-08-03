// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench.ingredients;

import org.bukkit.inventory.RecipeChoice;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class VanillaIngredient extends WorkbenchIngredient
{
    private final Material material;
    
    public VanillaIngredient(final Material material, final int n) {
        super(n);
        this.material = material;
    }
    
    public boolean corresponds(final ItemStack itemStack) {
        return !NBTItem.get(itemStack).hasType() && itemStack.getType() == this.material;
    }
    
    @Override
    public ItemStack generateItem() {
        return new ItemStack(this.material);
    }
    
    @Override
    public RecipeChoice toBukkit() {
        return (RecipeChoice)new RecipeChoice.MaterialChoice(this.material);
    }
}
