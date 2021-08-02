// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class LingeringPotionSplashEvent extends ProjectileHitEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final AreaEffectCloud entity;
    
    static {
        handlers = new HandlerList();
    }
    
    public LingeringPotionSplashEvent(final ThrownPotion potion, final AreaEffectCloud entity) {
        super(potion);
        this.entity = entity;
    }
    
    @Override
    public LingeringPotion getEntity() {
        return (LingeringPotion)super.getEntity();
    }
    
    public AreaEffectCloud getAreaEffectCloud() {
        return this.entity;
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
        return LingeringPotionSplashEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return LingeringPotionSplashEvent.handlers;
    }
}
