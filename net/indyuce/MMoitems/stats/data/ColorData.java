// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.bukkit.Color;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class ColorData implements StatData, RandomStatData
{
    private final int red;
    private final int green;
    private final int blue;
    
    public ColorData(final String s) {
        final String[] split = s.split(" ");
        Validate.isTrue(split.length > 2, "Must specify 3 numbers for red, green and blue");
        this.red = Math.min(255, Math.max(0, Integer.parseInt(split[0])));
        this.green = Math.min(255, Math.max(0, Integer.parseInt(split[1])));
        this.blue = Math.min(255, Math.max(0, Integer.parseInt(split[2])));
    }
    
    public ColorData(final Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
    }
    
    public ColorData(final int red, final int green, final int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public int getRed() {
        return this.red;
    }
    
    public int getGreen() {
        return this.green;
    }
    
    public int getBlue() {
        return this.blue;
    }
    
    public Color getColor() {
        return Color.fromRGB(this.red, this.green, this.blue);
    }
    
    @Override
    public String toString() {
        return "{Red=" + this.red + ",Green=" + this.green + ",Blue=" + this.blue + "}";
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
