// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.MerchantRecipe;
import java.util.List;
import org.bukkit.inventory.InventoryHolder;

public interface Villager extends Ageable, NPC, InventoryHolder
{
    Profession getProfession();
    
    void setProfession(final Profession p0);
    
    List<MerchantRecipe> getRecipes();
    
    void setRecipes(final List<MerchantRecipe> p0);
    
    MerchantRecipe getRecipe(final int p0) throws IndexOutOfBoundsException;
    
    void setRecipe(final int p0, final MerchantRecipe p1) throws IndexOutOfBoundsException;
    
    int getRecipeCount();
    
    Inventory getInventory();
    
    boolean isTrading();
    
    HumanEntity getTrader();
    
    int getRiches();
    
    void setRiches(final int p0);
    
    public enum Profession
    {
        NORMAL("NORMAL", 0, true), 
        FARMER("FARMER", 1, false), 
        LIBRARIAN("LIBRARIAN", 2, false), 
        PRIEST("PRIEST", 3, false), 
        BLACKSMITH("BLACKSMITH", 4, false), 
        BUTCHER("BUTCHER", 5, false), 
        HUSK("HUSK", 6, true);
        
        private final boolean zombie;
        
        private Profession(final String name, final int ordinal, final boolean zombie) {
            this.zombie = zombie;
        }
        
        public boolean isZombie() {
            return this.zombie;
        }
    }
}
