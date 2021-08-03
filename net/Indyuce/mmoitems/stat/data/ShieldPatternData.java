// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import org.bukkit.block.banner.Pattern;
import java.util.List;
import org.bukkit.DyeColor;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class ShieldPatternData implements StatData, RandomStatData
{
    private final DyeColor base;
    private final List<Pattern> patterns;
    
    public ShieldPatternData(final DyeColor base, final Pattern... a) {
        this.patterns = new ArrayList<Pattern>();
        this.base = base;
        this.patterns.addAll(Arrays.asList(a));
    }
    
    public DyeColor getBaseColor() {
        return this.base;
    }
    
    public List<Pattern> getPatterns() {
        return this.patterns;
    }
    
    public void add(final Pattern pattern) {
        this.patterns.add(pattern);
    }
    
    public void addAll(final List<Pattern> list) {
        this.patterns.addAll(list);
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
