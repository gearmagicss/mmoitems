// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.world;

import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;

public class ChunkLoadEvent extends ChunkEvent
{
    private static final HandlerList handlers;
    private final boolean newChunk;
    
    static {
        handlers = new HandlerList();
    }
    
    public ChunkLoadEvent(final Chunk chunk, final boolean newChunk) {
        super(chunk);
        this.newChunk = newChunk;
    }
    
    public boolean isNewChunk() {
        return this.newChunk;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ChunkLoadEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ChunkLoadEvent.handlers;
    }
}
