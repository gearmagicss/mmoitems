// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.potion;

import org.bukkit.entity.LivingEntity;
import java.io.Serializable;
import com.google.common.collect.ImmutableMap;
import java.util.NoSuchElementException;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@SerializableAs("PotionEffect")
public class PotionEffect implements ConfigurationSerializable
{
    private static final String AMPLIFIER = "amplifier";
    private static final String DURATION = "duration";
    private static final String TYPE = "effect";
    private static final String AMBIENT = "ambient";
    private static final String PARTICLES = "has-particles";
    private final int amplifier;
    private final int duration;
    private final PotionEffectType type;
    private final boolean ambient;
    private final boolean particles;
    private final Color color;
    
    public PotionEffect(final PotionEffectType type, final int duration, final int amplifier, final boolean ambient, final boolean particles, final Color color) {
        Validate.notNull(type, "effect type cannot be null");
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
        this.color = color;
    }
    
    public PotionEffect(final PotionEffectType type, final int duration, final int amplifier, final boolean ambient, final boolean particles) {
        this(type, duration, amplifier, ambient, particles, null);
    }
    
    public PotionEffect(final PotionEffectType type, final int duration, final int amplifier, final boolean ambient) {
        this(type, duration, amplifier, ambient, true);
    }
    
    public PotionEffect(final PotionEffectType type, final int duration, final int amplifier) {
        this(type, duration, amplifier, true);
    }
    
    public PotionEffect(final Map<String, Object> map) {
        this(getEffectType(map), getInt(map, "duration"), getInt(map, "amplifier"), getBool(map, "ambient", false), getBool(map, "has-particles", true));
    }
    
    private static PotionEffectType getEffectType(final Map<?, ?> map) {
        final int type = getInt(map, "effect");
        final PotionEffectType effect = PotionEffectType.getById(type);
        if (effect != null) {
            return effect;
        }
        throw new NoSuchElementException(map + " does not contain " + "effect");
    }
    
    private static int getInt(final Map<?, ?> map, final Object key) {
        final Object num = map.get(key);
        if (num instanceof Integer) {
            return (int)num;
        }
        throw new NoSuchElementException(map + " does not contain " + key);
    }
    
    private static boolean getBool(final Map<?, ?> map, final Object key, final boolean def) {
        final Object bool = map.get(key);
        if (bool instanceof Boolean) {
            return (boolean)bool;
        }
        return def;
    }
    
    @Override
    public Map<String, Object> serialize() {
        return (Map<String, Object>)ImmutableMap.of("effect", (Serializable)this.type.getId(), "duration", (Serializable)this.duration, "amplifier", (Serializable)this.amplifier, "ambient", (Serializable)this.ambient, "has-particles", (Serializable)this.particles);
    }
    
    public boolean apply(final LivingEntity entity) {
        return entity.addPotionEffect(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PotionEffect)) {
            return false;
        }
        final PotionEffect that = (PotionEffect)obj;
        return this.type.equals(that.type) && this.ambient == that.ambient && this.amplifier == that.amplifier && this.duration == that.duration && this.particles == that.particles;
    }
    
    public int getAmplifier() {
        return this.amplifier;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public PotionEffectType getType() {
        return this.type;
    }
    
    public boolean isAmbient() {
        return this.ambient;
    }
    
    public boolean hasParticles() {
        return this.particles;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.type.hashCode();
        hash = hash * 31 + this.amplifier;
        hash = hash * 31 + this.duration;
        hash ^= 572662306 >> (this.ambient ? 1 : -1);
        hash ^= 572662306 >> (this.particles ? 1 : -1);
        return hash;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.type.getName()) + (this.ambient ? ":(" : ":") + this.duration + "t-x" + this.amplifier + (this.ambient ? ")" : "");
    }
}
