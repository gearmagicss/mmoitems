// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.vehicle;

import org.bukkit.util.NumberConversions;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleDamageEvent extends VehicleEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity attacker;
    private double damage;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    @Deprecated
    public VehicleDamageEvent(final Vehicle vehicle, final Entity attacker, final int damage) {
        this(vehicle, attacker, (double)damage);
    }
    
    public VehicleDamageEvent(final Vehicle vehicle, final Entity attacker, final double damage) {
        super(vehicle);
        this.attacker = attacker;
        this.damage = damage;
    }
    
    public Entity getAttacker() {
        return this.attacker;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    @Deprecated
    public int _INVALID_getDamage() {
        return NumberConversions.ceil(this.getDamage());
    }
    
    public void setDamage(final double damage) {
        this.damage = damage;
    }
    
    @Deprecated
    public void _INVALID_setDamage(final int damage) {
        this.setDamage(damage);
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
    public HandlerList getHandlers() {
        return VehicleDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleDamageEvent.handlers;
    }
}
