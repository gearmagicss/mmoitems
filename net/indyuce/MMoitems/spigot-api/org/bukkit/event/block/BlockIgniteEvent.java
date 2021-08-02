// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockIgniteEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final IgniteCause cause;
    private final Entity ignitingEntity;
    private final Block ignitingBlock;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    @Deprecated
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Player thePlayer) {
        this(theBlock, cause, (Entity)thePlayer);
    }
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Entity ignitingEntity) {
        this(theBlock, cause, ignitingEntity, null);
    }
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Block ignitingBlock) {
        this(theBlock, cause, null, ignitingBlock);
    }
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Entity ignitingEntity, final Block ignitingBlock) {
        super(theBlock);
        this.cause = cause;
        this.ignitingEntity = ignitingEntity;
        this.ignitingBlock = ignitingBlock;
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
    
    public IgniteCause getCause() {
        return this.cause;
    }
    
    public Player getPlayer() {
        if (this.ignitingEntity instanceof Player) {
            return (Player)this.ignitingEntity;
        }
        return null;
    }
    
    public Entity getIgnitingEntity() {
        return this.ignitingEntity;
    }
    
    public Block getIgnitingBlock() {
        return this.ignitingBlock;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockIgniteEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockIgniteEvent.handlers;
    }
    
    public enum IgniteCause
    {
        LAVA("LAVA", 0), 
        FLINT_AND_STEEL("FLINT_AND_STEEL", 1), 
        SPREAD("SPREAD", 2), 
        LIGHTNING("LIGHTNING", 3), 
        FIREBALL("FIREBALL", 4), 
        ENDER_CRYSTAL("ENDER_CRYSTAL", 5), 
        EXPLOSION("EXPLOSION", 6);
        
        private IgniteCause(final String name, final int ordinal) {
        }
    }
}
