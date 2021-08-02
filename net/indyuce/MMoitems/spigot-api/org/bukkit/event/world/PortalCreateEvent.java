// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.world;

import org.bukkit.World;
import java.util.Collection;
import org.bukkit.block.Block;
import java.util.ArrayList;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PortalCreateEvent extends WorldEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final ArrayList<Block> blocks;
    private CreateReason reason;
    
    static {
        handlers = new HandlerList();
    }
    
    public PortalCreateEvent(final Collection<Block> blocks, final World world, final CreateReason reason) {
        super(world);
        this.cancel = false;
        this.blocks = new ArrayList<Block>();
        this.reason = CreateReason.FIRE;
        this.blocks.addAll(blocks);
        this.reason = reason;
    }
    
    public ArrayList<Block> getBlocks() {
        return this.blocks;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public CreateReason getReason() {
        return this.reason;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PortalCreateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PortalCreateEvent.handlers;
    }
    
    public enum CreateReason
    {
        FIRE("FIRE", 0), 
        OBC_DESTINATION("OBC_DESTINATION", 1);
        
        private CreateReason(final String name, final int ordinal) {
        }
    }
}
