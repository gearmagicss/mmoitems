// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.LivingEntity;
import java.util.List;
import org.bukkit.event.HandlerList;

public class AreaEffectCloudApplyEvent extends EntityEvent
{
    private static final HandlerList handlers;
    private final List<LivingEntity> affectedEntities;
    
    static {
        handlers = new HandlerList();
    }
    
    public AreaEffectCloudApplyEvent(final AreaEffectCloud entity, final List<LivingEntity> affectedEntities) {
        super(entity);
        this.affectedEntities = affectedEntities;
    }
    
    @Override
    public AreaEffectCloud getEntity() {
        return (AreaEffectCloud)this.entity;
    }
    
    public List<LivingEntity> getAffectedEntities() {
        return this.affectedEntities;
    }
    
    @Override
    public HandlerList getHandlers() {
        return AreaEffectCloudApplyEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return AreaEffectCloudApplyEvent.handlers;
    }
}
