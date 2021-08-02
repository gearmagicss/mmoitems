// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

public interface CraftingInventory extends Inventory
{
    ItemStack getResult();
    
    ItemStack[] getMatrix();
    
    void setResult(final ItemStack p0);
    
    void setMatrix(final ItemStack[] p0);
    
    Recipe getRecipe();
}
