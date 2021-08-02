// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory.meta;

import org.bukkit.block.banner.Pattern;
import java.util.List;
import org.bukkit.DyeColor;

public interface BannerMeta extends ItemMeta
{
    DyeColor getBaseColor();
    
    void setBaseColor(final DyeColor p0);
    
    List<Pattern> getPatterns();
    
    void setPatterns(final List<Pattern> p0);
    
    void addPattern(final Pattern p0);
    
    Pattern getPattern(final int p0);
    
    Pattern removePattern(final int p0);
    
    void setPattern(final int p0, final Pattern p1);
    
    int numberOfPatterns();
}
