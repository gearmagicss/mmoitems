// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityBreedEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final LivingEntity mother;
    private final LivingEntity father;
    private final LivingEntity breeder;
    private final ItemStack bredWith;
    private int experience;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityBreedEvent(final LivingEntity child, final LivingEntity mother, final LivingEntity father, final LivingEntity breeder, final ItemStack bredWith, final int experience) {
        super(child);
        Validate.notNull(child, "Cannot have null child");
        Validate.notNull(mother, "Cannot have null mother");
        Validate.notNull(father, "Cannot have null father");
        this.mother = mother;
        this.father = father;
        this.breeder = breeder;
        this.bredWith = bredWith;
        this.setExperience(experience);
    }
    
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    public LivingEntity getMother() {
        return this.mother;
    }
    
    public LivingEntity getFather() {
        return this.father;
    }
    
    public LivingEntity getBreeder() {
        return this.breeder;
    }
    
    public ItemStack getBredWith() {
        return this.bredWith;
    }
    
    public int getExperience() {
        return this.experience;
    }
    
    public void setExperience(final int experience) {
        Validate.isTrue(experience >= 0, "Experience cannot be negative");
        this.experience = experience;
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
        return EntityBreedEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityBreedEvent.handlers;
    }
}
