// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.itemglow;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;

public class TierColor
{
    private final GlowColor glow;
    private final Color bukkit;
    
    public TierColor(final String s, final boolean b) {
        Validate.notNull((Object)s, "String must not be null");
        this.bukkit = (Color)Color.class.getField(s.toUpperCase().replace("-", "_").replace(" ", "_")).get(Color.class);
        this.glow = (b ? new GlowColor(this.bukkit) : null);
    }
    
    public GlowColor toGlow() {
        return this.glow;
    }
    
    public Color toBukkit() {
        return this.bukkit;
    }
}
