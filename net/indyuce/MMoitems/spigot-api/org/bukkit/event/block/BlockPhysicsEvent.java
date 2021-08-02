// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockPhysicsEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final int changed;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    @Deprecated
    public BlockPhysicsEvent(final Block block, final int changed) {
        super(block);
        this.cancel = false;
        this.changed = changed;
    }
    
    @Deprecated
    public int getChangedTypeId() {
        return this.changed;
    }
    
    public Material getChangedType() {
        return Material.getMaterial(this.changed);
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockPhysicsEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockPhysicsEvent.handlers;
    }
}
