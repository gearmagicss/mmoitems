// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VillagerAcquireTradeEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private MerchantRecipe recipe;
    
    static {
        handlers = new HandlerList();
    }
    
    public VillagerAcquireTradeEvent(final Villager what, final MerchantRecipe recipe) {
        super(what);
        this.recipe = recipe;
    }
    
    public MerchantRecipe getRecipe() {
        return this.recipe;
    }
    
    public void setRecipe(final MerchantRecipe recipe) {
        this.recipe = recipe;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    @Override
    public Villager getEntity() {
        return (Villager)super.getEntity();
    }
    
    @Override
    public HandlerList getHandlers() {
        return VillagerAcquireTradeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VillagerAcquireTradeEvent.handlers;
    }
}
