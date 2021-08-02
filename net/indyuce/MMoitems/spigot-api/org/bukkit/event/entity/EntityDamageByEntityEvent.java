// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import com.google.common.base.Function;
import java.util.Map;
import org.bukkit.entity.Entity;

public class EntityDamageByEntityEvent extends EntityDamageEvent
{
    private final Entity damager;
    
    @Deprecated
    public EntityDamageByEntityEvent(final Entity damager, final Entity damagee, final DamageCause cause, final int damage) {
        this(damager, damagee, cause, (double)damage);
    }
    
    @Deprecated
    public EntityDamageByEntityEvent(final Entity damager, final Entity damagee, final DamageCause cause, final double damage) {
        super(damagee, cause, damage);
        this.damager = damager;
    }
    
    public EntityDamageByEntityEvent(final Entity damager, final Entity damagee, final DamageCause cause, final Map<DamageModifier, Double> modifiers, final Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damagee, cause, modifiers, modifierFunctions);
        this.damager = damager;
    }
    
    public Entity getDamager() {
        return this.damager;
    }
}
