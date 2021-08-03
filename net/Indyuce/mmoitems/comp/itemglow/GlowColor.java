// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.itemglow;

import java.util.HashMap;
import org.bukkit.Color;
import java.util.Map;
import org.inventivetalent.glow.GlowAPI;

public class GlowColor
{
    private final GlowAPI.Color glow;
    private static final Map<Color, GlowAPI.Color> map;
    
    public GlowColor(final Color color) {
        this.glow = GlowColor.map.get(color);
    }
    
    public GlowAPI.Color get() {
        return this.glow;
    }
    
    static {
        (map = new HashMap<Color, GlowAPI.Color>()).put(Color.AQUA, GlowAPI.Color.AQUA);
        GlowColor.map.put(Color.BLACK, GlowAPI.Color.BLACK);
        GlowColor.map.put(Color.BLUE, GlowAPI.Color.BLUE);
        GlowColor.map.put(Color.FUCHSIA, GlowAPI.Color.DARK_PURPLE);
        GlowColor.map.put(Color.GRAY, GlowAPI.Color.DARK_GRAY);
        GlowColor.map.put(Color.GREEN, GlowAPI.Color.DARK_GREEN);
        GlowColor.map.put(Color.LIME, GlowAPI.Color.GREEN);
        GlowColor.map.put(Color.NAVY, GlowAPI.Color.DARK_BLUE);
        GlowColor.map.put(Color.OLIVE, GlowAPI.Color.AQUA);
        GlowColor.map.put(Color.ORANGE, GlowAPI.Color.GOLD);
        GlowColor.map.put(Color.PURPLE, GlowAPI.Color.PURPLE);
        GlowColor.map.put(Color.RED, GlowAPI.Color.RED);
        GlowColor.map.put(Color.SILVER, GlowAPI.Color.GRAY);
        GlowColor.map.put(Color.WHITE, GlowAPI.Color.WHITE);
        GlowColor.map.put(Color.YELLOW, GlowAPI.Color.YELLOW);
        GlowColor.map.put(Color.TEAL, GlowAPI.Color.BLUE);
        GlowColor.map.put(Color.MAROON, GlowAPI.Color.DARK_RED);
    }
}
