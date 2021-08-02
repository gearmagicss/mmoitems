// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Egg;
import org.bukkit.event.HandlerList;

public class PlayerEggThrowEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final Egg egg;
    private boolean hatching;
    private EntityType hatchType;
    private byte numHatches;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerEggThrowEvent(final Player player, final Egg egg, final boolean hatching, final byte numHatches, final EntityType hatchingType) {
        super(player);
        this.egg = egg;
        this.hatching = hatching;
        this.numHatches = numHatches;
        this.hatchType = hatchingType;
    }
    
    public Egg getEgg() {
        return this.egg;
    }
    
    public boolean isHatching() {
        return this.hatching;
    }
    
    public void setHatching(final boolean hatching) {
        this.hatching = hatching;
    }
    
    public EntityType getHatchingType() {
        return this.hatchType;
    }
    
    public void setHatchingType(final EntityType hatchType) {
        if (!hatchType.isSpawnable()) {
            throw new IllegalArgumentException("Can't spawn that entity type from an egg!");
        }
        this.hatchType = hatchType;
    }
    
    public byte getNumHatches() {
        return this.numHatches;
    }
    
    public void setNumHatches(final byte numHatches) {
        this.numHatches = numHatches;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerEggThrowEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerEggThrowEvent.handlers;
    }
}
