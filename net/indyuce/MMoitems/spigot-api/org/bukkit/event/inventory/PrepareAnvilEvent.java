// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;

public class PrepareAnvilEvent extends InventoryEvent
{
    private static final HandlerList handlers;
    private ItemStack result;
    
    static {
        handlers = new HandlerList();
    }
    
    public PrepareAnvilEvent(final InventoryView inventory, final ItemStack result) {
        super(inventory);
        this.result = result;
    }
    
    @Override
    public AnvilInventory getInventory() {
        return (AnvilInventory)super.getInventory();
    }
    
    public ItemStack getResult() {
        return this.result;
    }
    
    public void setResult(final ItemStack result) {
        this.result = result;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PrepareAnvilEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PrepareAnvilEvent.handlers;
    }
}
