// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template;

import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.apache.commons.lang.Validate;

public class NameModifier
{
    private final ModifierType type;
    private final String format;
    private final int priority;
    
    public NameModifier(final ModifierType type, final Object o) {
        Validate.notNull(o, "Object cannot be null");
        this.type = type;
        if (o instanceof String) {
            this.format = (String)o;
            this.priority = 0;
            return;
        }
        if (o instanceof ConfigurationSection) {
            final ConfigurationSection configurationSection = (ConfigurationSection)o;
            Validate.isTrue(configurationSection.contains("format"), MMOUtils.caseOnWords(type.name().toLowerCase()) + " format cannot be null");
            this.format = configurationSection.get("format").toString();
            this.priority = configurationSection.getInt("priority");
            return;
        }
        throw new IllegalArgumentException("Must specify a string or a config section");
    }
    
    public NameModifier(final ModifierType type, final String format, final int priority) {
        Validate.notNull((Object)format, "Format cannot be null");
        this.type = type;
        this.format = format;
        this.priority = priority;
        Validate.notNull((Object)type, "Type cannot be null");
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public ModifierType getType() {
        return this.type;
    }
    
    public boolean hasPriority() {
        return this.priority > 0;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public enum ModifierType
    {
        PREFIX, 
        SUFFIX;
    }
}
