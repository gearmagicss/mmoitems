// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

public interface MerchantInventory extends Inventory
{
    int getSelectedRecipeIndex();
    
    MerchantRecipe getSelectedRecipe();
}
