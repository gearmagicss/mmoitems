// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block.banner;

import java.util.HashMap;
import java.util.Map;

public enum PatternType
{
    BASE("BASE", 0, "b"), 
    SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "bl"), 
    SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "br"), 
    SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "tl"), 
    SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "tr"), 
    STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "bs"), 
    STRIPE_TOP("STRIPE_TOP", 6, "ts"), 
    STRIPE_LEFT("STRIPE_LEFT", 7, "ls"), 
    STRIPE_RIGHT("STRIPE_RIGHT", 8, "rs"), 
    STRIPE_CENTER("STRIPE_CENTER", 9, "cs"), 
    STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "ms"), 
    STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "drs"), 
    STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "dls"), 
    STRIPE_SMALL("STRIPE_SMALL", 13, "ss"), 
    CROSS("CROSS", 14, "cr"), 
    STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "sc"), 
    TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "bt"), 
    TRIANGLE_TOP("TRIANGLE_TOP", 17, "tt"), 
    TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "bts"), 
    TRIANGLES_TOP("TRIANGLES_TOP", 19, "tts"), 
    DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "ld"), 
    DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "rd"), 
    DIAGONAL_LEFT_MIRROR("DIAGONAL_LEFT_MIRROR", 22, "lud"), 
    DIAGONAL_RIGHT_MIRROR("DIAGONAL_RIGHT_MIRROR", 23, "rud"), 
    CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "mc"), 
    RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "mr"), 
    HALF_VERTICAL("HALF_VERTICAL", 26, "vh"), 
    HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "hh"), 
    HALF_VERTICAL_MIRROR("HALF_VERTICAL_MIRROR", 28, "vhr"), 
    HALF_HORIZONTAL_MIRROR("HALF_HORIZONTAL_MIRROR", 29, "hhb"), 
    BORDER("BORDER", 30, "bo"), 
    CURLY_BORDER("CURLY_BORDER", 31, "cbo"), 
    CREEPER("CREEPER", 32, "cre"), 
    GRADIENT("GRADIENT", 33, "gra"), 
    GRADIENT_UP("GRADIENT_UP", 34, "gru"), 
    BRICKS("BRICKS", 35, "bri"), 
    SKULL("SKULL", 36, "sku"), 
    FLOWER("FLOWER", 37, "flo"), 
    MOJANG("MOJANG", 38, "moj");
    
    private final String identifier;
    private static final Map<String, PatternType> byString;
    
    static {
        byString = new HashMap<String, PatternType>();
        PatternType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PatternType p = values[i];
            PatternType.byString.put(p.identifier, p);
        }
    }
    
    private PatternType(final String name, final int ordinal, final String key) {
        this.identifier = key;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public static PatternType getByIdentifier(final String identifier) {
        return PatternType.byString.get(identifier);
    }
}
