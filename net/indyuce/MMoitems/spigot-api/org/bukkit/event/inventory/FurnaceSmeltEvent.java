// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

public class FurnaceSmeltEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final ItemStack source;
    private ItemStack result;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public FurnaceSmeltEvent(final Block furnace, final ItemStack source, final ItemStack result) {
        super(furnace);
        this.source = source;
        this.result = result;
        this.cancelled = false;
    }
    
    public ItemStack getSource() {
        return this.source;
    }
    
    public ItemStack getResult() {
        return this.result;
    }
    
    public void setResult(final ItemStack result) {
        this.result = result;
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
        return FurnaceSmeltEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return FurnaceSmeltEvent.handlers;
    }
}
