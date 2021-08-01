// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.Indyuce.mmoitems.api.block.CustomBlock;

public class CustomBlockDropEvent extends PlayerDataEvent
{
    private final CustomBlock block;
    private final List<ItemStack> drops;
    private static final HandlerList handlers;
    
    public CustomBlockDropEvent(final PlayerData playerData, final CustomBlock block, final List<ItemStack> drops) {
        super(playerData);
        this.block = block;
        this.drops = drops;
    }
    
    public CustomBlock getCustomBlock() {
        return this.block;
    }
    
    public List<ItemStack> getDrops() {
        return this.drops;
    }
    
    public HandlerList getHandlers() {
        return CustomBlockDropEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CustomBlockDropEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
