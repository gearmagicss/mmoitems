// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.event.HandlerList;

public class AbilityUseEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final AbilityData ability;
    private final LivingEntity target;
    
    public AbilityUseEvent(final PlayerData playerData, final AbilityData abilityData) {
        this(playerData, abilityData, null);
    }
    
    public AbilityUseEvent(final PlayerData playerData, final AbilityData ability, final LivingEntity target) {
        super(playerData);
        this.ability = ability;
        this.target = target;
    }
    
    public AbilityData getAbility() {
        return this.ability;
    }
    
    public LivingEntity getTarget() {
        return this.target;
    }
    
    public HandlerList getHandlers() {
        return AbilityUseEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return AbilityUseEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
