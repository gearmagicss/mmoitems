// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.Statistic;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerStatisticIncrementEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected final Statistic statistic;
    private final int initialValue;
    private final int newValue;
    private boolean isCancelled;
    private final EntityType entityType;
    private final Material material;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerStatisticIncrementEvent(final Player player, final Statistic statistic, final int initialValue, final int newValue) {
        super(player);
        this.isCancelled = false;
        this.statistic = statistic;
        this.initialValue = initialValue;
        this.newValue = newValue;
        this.entityType = null;
        this.material = null;
    }
    
    public PlayerStatisticIncrementEvent(final Player player, final Statistic statistic, final int initialValue, final int newValue, final EntityType entityType) {
        super(player);
        this.isCancelled = false;
        this.statistic = statistic;
        this.initialValue = initialValue;
        this.newValue = newValue;
        this.entityType = entityType;
        this.material = null;
    }
    
    public PlayerStatisticIncrementEvent(final Player player, final Statistic statistic, final int initialValue, final int newValue, final Material material) {
        super(player);
        this.isCancelled = false;
        this.statistic = statistic;
        this.initialValue = initialValue;
        this.newValue = newValue;
        this.entityType = null;
        this.material = material;
    }
    
    public Statistic getStatistic() {
        return this.statistic;
    }
    
    public int getPreviousValue() {
        return this.initialValue;
    }
    
    public int getNewValue() {
        return this.newValue;
    }
    
    public EntityType getEntityType() {
        return this.entityType;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerStatisticIncrementEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerStatisticIncrementEvent.handlers;
    }
}
