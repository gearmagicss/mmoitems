// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block.banner;

import com.google.common.collect.ImmutableMap;
import java.util.NoSuchElementException;
import java.util.Map;
import org.bukkit.DyeColor;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@SerializableAs("Pattern")
public class Pattern implements ConfigurationSerializable
{
    private static final String COLOR = "color";
    private static final String PATTERN = "pattern";
    private final DyeColor color;
    private final PatternType pattern;
    
    public Pattern(final DyeColor color, final PatternType pattern) {
        this.color = color;
        this.pattern = pattern;
    }
    
    public Pattern(final Map<String, Object> map) {
        this.color = DyeColor.valueOf(getString(map, "color"));
        this.pattern = PatternType.getByIdentifier(getString(map, "pattern"));
    }
    
    private static String getString(final Map<?, ?> map, final Object key) {
        final Object str = map.get(key);
        if (str instanceof String) {
            return (String)str;
        }
        throw new NoSuchElementException(map + " does not contain " + key);
    }
    
    @Override
    public Map<String, Object> serialize() {
        return (Map<String, Object>)ImmutableMap.of("color", this.color.toString(), "pattern", this.pattern.getIdentifier());
    }
    
    public DyeColor getColor() {
        return this.color;
    }
    
    public PatternType getPattern() {
        return this.pattern;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + ((this.color != null) ? this.color.hashCode() : 0);
        hash = 97 * hash + ((this.pattern != null) ? this.pattern.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Pattern other = (Pattern)obj;
        return this.color == other.color && this.pattern == other.pattern;
    }
}
