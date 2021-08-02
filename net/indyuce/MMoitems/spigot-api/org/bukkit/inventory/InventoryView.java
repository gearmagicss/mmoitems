// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

import org.bukkit.GameMode;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.entity.HumanEntity;

public abstract class InventoryView
{
    public static final int OUTSIDE = -999;
    
    public abstract Inventory getTopInventory();
    
    public abstract Inventory getBottomInventory();
    
    public abstract HumanEntity getPlayer();
    
    public abstract InventoryType getType();
    
    public void setItem(final int slot, final ItemStack item) {
        if (slot != -999) {
            if (slot < this.getTopInventory().getSize()) {
                this.getTopInventory().setItem(this.convertSlot(slot), item);
            }
            else {
                this.getBottomInventory().setItem(this.convertSlot(slot), item);
            }
        }
        else {
            this.getPlayer().getWorld().dropItemNaturally(this.getPlayer().getLocation(), item);
        }
    }
    
    public ItemStack getItem(final int slot) {
        if (slot == -999) {
            return null;
        }
        if (slot < this.getTopInventory().getSize()) {
            return this.getTopInventory().getItem(this.convertSlot(slot));
        }
        return this.getBottomInventory().getItem(this.convertSlot(slot));
    }
    
    public final void setCursor(final ItemStack item) {
        this.getPlayer().setItemOnCursor(item);
    }
    
    public final ItemStack getCursor() {
        return this.getPlayer().getItemOnCursor();
    }
    
    public final int convertSlot(final int rawSlot) {
        final int numInTop = this.getTopInventory().getSize();
        if (rawSlot < numInTop) {
            return rawSlot;
        }
        int slot = rawSlot - numInTop;
        if (this.getPlayer().getGameMode() == GameMode.CREATIVE && this.getType() == InventoryType.PLAYER) {
            return slot;
        }
        if (this.getType() == InventoryType.CRAFTING) {
            if (slot < 4) {
                return 39 - slot;
            }
            if (slot > 39) {
                return slot;
            }
            slot -= 4;
        }
        if (slot >= 27) {
            slot -= 27;
        }
        else {
            slot += 9;
        }
        return slot;
    }
    
    public final void close() {
        this.getPlayer().closeInventory();
    }
    
    public final int countSlots() {
        return this.getTopInventory().getSize() + this.getBottomInventory().getSize();
    }
    
    public final boolean setProperty(final Property prop, final int value) {
        return this.getPlayer().setWindowProperty(prop, value);
    }
    
    public final String getTitle() {
        return this.getTopInventory().getTitle();
    }
    
    public enum Property
    {
        BREW_TIME("BREW_TIME", 0, 0, InventoryType.BREWING), 
        COOK_TIME("COOK_TIME", 1, 0, InventoryType.FURNACE), 
        BURN_TIME("BURN_TIME", 2, 1, InventoryType.FURNACE), 
        TICKS_FOR_CURRENT_FUEL("TICKS_FOR_CURRENT_FUEL", 3, 2, InventoryType.FURNACE), 
        ENCHANT_BUTTON1("ENCHANT_BUTTON1", 4, 0, InventoryType.ENCHANTING), 
        ENCHANT_BUTTON2("ENCHANT_BUTTON2", 5, 1, InventoryType.ENCHANTING), 
        ENCHANT_BUTTON3("ENCHANT_BUTTON3", 6, 2, InventoryType.ENCHANTING);
        
        int id;
        InventoryType style;
        
        private Property(final String name, final int ordinal, final int id, final InventoryType appliesTo) {
            this.id = id;
            this.style = appliesTo;
        }
        
        public InventoryType getType() {
            return this.style;
        }
        
        @Deprecated
        public int getId() {
            return this.id;
        }
    }
}
