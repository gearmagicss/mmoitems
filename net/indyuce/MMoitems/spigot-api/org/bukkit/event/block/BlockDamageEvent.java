// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockDamageEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Player player;
    private boolean instaBreak;
    private boolean cancel;
    private final ItemStack itemstack;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockDamageEvent(final Player player, final Block block, final ItemStack itemInHand, final boolean instaBreak) {
        super(block);
        this.instaBreak = instaBreak;
        this.player = player;
        this.itemstack = itemInHand;
        this.cancel = false;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean getInstaBreak() {
        return this.instaBreak;
    }
    
    public void setInstaBreak(final boolean bool) {
        this.instaBreak = bool;
    }
    
    public ItemStack getItemInHand() {
        return this.itemstack;
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
        return BlockDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockDamageEvent.handlers;
    }
}
