// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import com.google.common.base.Function;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.block.Block;

public class EntityDamageByBlockEvent extends EntityDamageEvent
{
    private final Block damager;
    
    @Deprecated
    public EntityDamageByBlockEvent(final Block damager, final Entity damagee, final DamageCause cause, final int damage) {
        this(damager, damagee, cause, (double)damage);
    }
    
    @Deprecated
    public EntityDamageByBlockEvent(final Block damager, final Entity damagee, final DamageCause cause, final double damage) {
        super(damagee, cause, damage);
        this.damager = damager;
    }
    
    public EntityDamageByBlockEvent(final Block damager, final Entity damagee, final DamageCause cause, final Map<DamageModifier, Double> modifiers, final Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damagee, cause, modifiers, modifierFunctions);
        this.damager = damager;
    }
    
    public Block getDamager() {
        return this.damager;
    }
}
