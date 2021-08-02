// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.world;

import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;

public class ChunkPopulateEvent extends ChunkEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public ChunkPopulateEvent(final Chunk chunk) {
        super(chunk);
    }
    
    @Override
    public HandlerList getHandlers() {
        return ChunkPopulateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ChunkPopulateEvent.handlers;
    }
}
