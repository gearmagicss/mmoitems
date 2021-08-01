// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient.inventory;

import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;

public abstract class PlayerIngredient
{
    private final ItemStack item;
    
    public PlayerIngredient(final NBTItem nbtItem) {
        this.item = nbtItem.getItem();
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public int getAmount() {
        return this.item.getAmount();
    }
}
