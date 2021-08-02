// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.world;

import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ChunkUnloadEvent extends ChunkEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public ChunkUnloadEvent(final Chunk chunk) {
        super(chunk);
        this.cancel = false;
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
        return ChunkUnloadEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ChunkUnloadEvent.handlers;
    }
}
