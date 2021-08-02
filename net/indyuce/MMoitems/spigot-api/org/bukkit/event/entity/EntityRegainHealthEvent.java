// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.util.NumberConversions;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityRegainHealthEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private double amount;
    private final RegainReason regainReason;
    
    static {
        handlers = new HandlerList();
    }
    
    @Deprecated
    public EntityRegainHealthEvent(final Entity entity, final int amount, final RegainReason regainReason) {
        this(entity, (double)amount, regainReason);
    }
    
    public EntityRegainHealthEvent(final Entity entity, final double amount, final RegainReason regainReason) {
        super(entity);
        this.amount = amount;
        this.regainReason = regainReason;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    @Deprecated
    public int _INVALID_getAmount() {
        return NumberConversions.ceil(this.getAmount());
    }
    
    public void setAmount(final double amount) {
        this.amount = amount;
    }
    
    @Deprecated
    public void _INVALID_setAmount(final int amount) {
        this.setAmount(amount);
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public RegainReason getRegainReason() {
        return this.regainReason;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityRegainHealthEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityRegainHealthEvent.handlers;
    }
    
    public enum RegainReason
    {
        REGEN("REGEN", 0), 
        SATIATED("SATIATED", 1), 
        EATING("EATING", 2), 
        ENDER_CRYSTAL("ENDER_CRYSTAL", 3), 
        MAGIC("MAGIC", 4), 
        MAGIC_REGEN("MAGIC_REGEN", 5), 
        WITHER_SPAWN("WITHER_SPAWN", 6), 
        WITHER("WITHER", 7), 
        CUSTOM("CUSTOM", 8);
        
        private RegainReason(final String name, final int ordinal) {
        }
    }
}
