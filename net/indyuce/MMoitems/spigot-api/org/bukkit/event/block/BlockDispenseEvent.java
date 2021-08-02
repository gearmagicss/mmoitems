// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockDispenseEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private ItemStack item;
    private Vector velocity;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockDispenseEvent(final Block block, final ItemStack dispensed, final Vector velocity) {
        super(block);
        this.cancelled = false;
        this.item = dispensed;
        this.velocity = velocity;
    }
    
    public ItemStack getItem() {
        return this.item.clone();
    }
    
    public void setItem(final ItemStack item) {
        this.item = item;
    }
    
    public Vector getVelocity() {
        return this.velocity.clone();
    }
    
    public void setVelocity(final Vector vel) {
        this.velocity = vel;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockDispenseEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockDispenseEvent.handlers;
    }
}
