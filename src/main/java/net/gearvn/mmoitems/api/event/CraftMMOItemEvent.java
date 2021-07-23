// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;

public class CraftMMOItemEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private ItemStack result;
    
    public CraftMMOItemEvent(final PlayerData playerData, final ItemStack result) {
        super(playerData);
        this.result = result;
    }
    
    public ItemStack getResult() {
        return this.result;
    }
    
    public void setResult(final ItemStack result) {
        this.result = result;
    }
    
    public HandlerList getHandlers() {
        return CraftMMOItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CraftMMOItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
