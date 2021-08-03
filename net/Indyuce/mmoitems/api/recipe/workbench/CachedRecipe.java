// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench;

import org.bukkit.Material;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class CachedRecipe
{
    private final Map<Integer, Integer> amounts;
    private ItemStack stack;
    
    public CachedRecipe() {
        this.amounts = new HashMap<Integer, Integer>();
    }
    
    public boolean isValid(final ItemStack[] array) {
        boolean b = true;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                if (array[i].getType() != Material.AIR) {
                    if (array[i].getAmount() < this.amounts.get(i)) {
                        b = false;
                    }
                    if (!b) {
                        break;
                    }
                }
            }
        }
        return b;
    }
    
    public ItemStack[] generateMatrix(final ItemStack[] array) {
        final ItemStack[] array2 = new ItemStack[9];
        for (int i = 0; i < array.length; ++i) {
            final ItemStack itemStack = array[i];
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                array2[i] = null;
            }
            else {
                final int amount = itemStack.getAmount() - this.amounts.get(i);
                if (amount < 1) {
                    array2[i] = null;
                }
                else {
                    itemStack.setAmount(amount);
                    array2[i] = itemStack;
                }
            }
        }
        return array2;
    }
    
    public void add(final int i, final int j) {
        this.amounts.put(i, j);
    }
    
    public void setResult(final ItemStack stack) {
        this.stack = stack;
    }
    
    public ItemStack getResult() {
        return this.stack;
    }
    
    public void clean() {
        for (int i = 0; i < 9; ++i) {
            if (!this.amounts.containsKey(i)) {
                this.amounts.put(i, 0);
            }
        }
    }
}
