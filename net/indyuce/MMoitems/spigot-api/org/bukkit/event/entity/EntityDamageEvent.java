// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.util.NumberConversions;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import java.util.EnumMap;
import com.google.common.collect.ImmutableMap;
import org.bukkit.entity.Entity;
import com.google.common.base.Functions;
import java.util.Map;
import com.google.common.base.Function;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityDamageEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private static final DamageModifier[] MODIFIERS;
    private static final Function<? super Double, Double> ZERO;
    private final Map<DamageModifier, Double> modifiers;
    private final Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions;
    private final Map<DamageModifier, Double> originals;
    private boolean cancelled;
    private final DamageCause cause;
    
    static {
        handlers = new HandlerList();
        MODIFIERS = DamageModifier.values();
        ZERO = Functions.constant(-0.0);
    }
    
    @Deprecated
    public EntityDamageEvent(final Entity damagee, final DamageCause cause, final int damage) {
        this(damagee, cause, (double)damage);
    }
    
    @Deprecated
    public EntityDamageEvent(final Entity damagee, final DamageCause cause, final double damage) {
        this(damagee, cause, new EnumMap<DamageModifier, Double>(ImmutableMap.of(DamageModifier.BASE, Double.valueOf(damage))), new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, EntityDamageEvent.ZERO)));
    }
    
    public EntityDamageEvent(final Entity damagee, final DamageCause cause, final Map<DamageModifier, Double> modifiers, final Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damagee);
        Validate.isTrue(modifiers.containsKey(DamageModifier.BASE), "BASE DamageModifier missing");
        Validate.isTrue(!modifiers.containsKey(null), "Cannot have null DamageModifier");
        Validate.noNullElements(modifiers.values(), "Cannot have null modifier values");
        Validate.isTrue(modifiers.keySet().equals(modifierFunctions.keySet()), "Must have a modifier function for each DamageModifier");
        Validate.noNullElements(modifierFunctions.values(), "Cannot have null modifier function");
        this.originals = new EnumMap<DamageModifier, Double>(modifiers);
        this.cause = cause;
        this.modifiers = modifiers;
        this.modifierFunctions = modifierFunctions;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public double getOriginalDamage(final DamageModifier type) throws IllegalArgumentException {
        final Double damage = this.originals.get(type);
        if (damage != null) {
            return damage;
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot have null DamageModifier");
        }
        return 0.0;
    }
    
    public void setDamage(final DamageModifier type, final double damage) throws IllegalArgumentException, UnsupportedOperationException {
        if (!this.modifiers.containsKey(type)) {
            throw (type == null) ? new IllegalArgumentException("Cannot have null DamageModifier") : new UnsupportedOperationException(type + " is not applicable to " + this.getEntity());
        }
        this.modifiers.put(type, damage);
    }
    
    public double getDamage(final DamageModifier type) throws IllegalArgumentException {
        Validate.notNull(type, "Cannot have null DamageModifier");
        final Double damage = this.modifiers.get(type);
        return (damage == null) ? 0.0 : damage;
    }
    
    public boolean isApplicable(final DamageModifier type) throws IllegalArgumentException {
        Validate.notNull(type, "Cannot have null DamageModifier");
        return this.modifiers.containsKey(type);
    }
    
    public double getDamage() {
        return this.getDamage(DamageModifier.BASE);
    }
    
    public final double getFinalDamage() {
        double damage = 0.0;
        DamageModifier[] modifiers;
        for (int length = (modifiers = EntityDamageEvent.MODIFIERS).length, i = 0; i < length; ++i) {
            final DamageModifier modifier = modifiers[i];
            damage += this.getDamage(modifier);
        }
        return damage;
    }
    
    @Deprecated
    public int _INVALID_getDamage() {
        return NumberConversions.ceil(this.getDamage());
    }
    
    public void setDamage(final double damage) {
        double remaining = damage;
        double oldRemaining = this.getDamage(DamageModifier.BASE);
        DamageModifier[] modifiers;
        for (int length = (modifiers = EntityDamageEvent.MODIFIERS).length, i = 0; i < length; ++i) {
            final DamageModifier modifier = modifiers[i];
            if (this.isApplicable(modifier)) {
                final Function<? super Double, Double> modifierFunction = (Function<? super Double, Double>)this.modifierFunctions.get(modifier);
                final double newVanilla = modifierFunction.apply(remaining);
                final double oldVanilla = modifierFunction.apply(oldRemaining);
                final double difference = oldVanilla - newVanilla;
                final double old = this.getDamage(modifier);
                if (old > 0.0) {
                    this.setDamage(modifier, Math.max(0.0, old - difference));
                }
                else {
                    this.setDamage(modifier, Math.min(0.0, old - difference));
                }
                remaining += newVanilla;
                oldRemaining += oldVanilla;
            }
        }
        this.setDamage(DamageModifier.BASE, damage);
    }
    
    @Deprecated
    public void _INVALID_setDamage(final int damage) {
        this.setDamage(damage);
    }
    
    public DamageCause getCause() {
        return this.cause;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityDamageEvent.handlers;
    }
    
    public enum DamageCause
    {
        CONTACT("CONTACT", 0), 
        ENTITY_ATTACK("ENTITY_ATTACK", 1), 
        PROJECTILE("PROJECTILE", 2), 
        SUFFOCATION("SUFFOCATION", 3), 
        FALL("FALL", 4), 
        FIRE("FIRE", 5), 
        FIRE_TICK("FIRE_TICK", 6), 
        MELTING("MELTING", 7), 
        LAVA("LAVA", 8), 
        DROWNING("DROWNING", 9), 
        BLOCK_EXPLOSION("BLOCK_EXPLOSION", 10), 
        ENTITY_EXPLOSION("ENTITY_EXPLOSION", 11), 
        VOID("VOID", 12), 
        LIGHTNING("LIGHTNING", 13), 
        SUICIDE("SUICIDE", 14), 
        STARVATION("STARVATION", 15), 
        POISON("POISON", 16), 
        MAGIC("MAGIC", 17), 
        WITHER("WITHER", 18), 
        FALLING_BLOCK("FALLING_BLOCK", 19), 
        THORNS("THORNS", 20), 
        DRAGON_BREATH("DRAGON_BREATH", 21), 
        CUSTOM("CUSTOM", 22), 
        FLY_INTO_WALL("FLY_INTO_WALL", 23), 
        HOT_FLOOR("HOT_FLOOR", 24);
        
        private DamageCause(final String name, final int ordinal) {
        }
    }
    
    public enum DamageModifier
    {
        BASE("BASE", 0), 
        HARD_HAT("HARD_HAT", 1), 
        BLOCKING("BLOCKING", 2), 
        ARMOR("ARMOR", 3), 
        RESISTANCE("RESISTANCE", 4), 
        MAGIC("MAGIC", 5), 
        ABSORPTION("ABSORPTION", 6);
        
        private DamageModifier(final String name, final int ordinal) {
        }
    }
}
