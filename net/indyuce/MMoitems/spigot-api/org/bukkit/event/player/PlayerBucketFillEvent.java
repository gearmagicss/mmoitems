// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerBucketFillEvent extends PlayerBucketEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerBucketFillEvent(final Player who, final Block blockClicked, final BlockFace blockFace, final Material bucket, final ItemStack itemInHand) {
        super(who, blockClicked, blockFace, bucket, itemInHand);
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerBucketFillEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerBucketFillEvent.handlers;
    }
}
