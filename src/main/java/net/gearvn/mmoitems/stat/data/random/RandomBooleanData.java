// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.BooleanData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Random;

public class RandomBooleanData implements RandomStatData
{
    private final double chance;
    private static final Random random;
    
    public RandomBooleanData(final boolean b) {
        this.chance = (b ? 1.0 : 0.0);
    }
    
    public RandomBooleanData(final double chance) {
        this.chance = chance;
    }
    
    public double getChance() {
        return this.chance;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new BooleanData(RandomBooleanData.random.nextDouble() < this.chance);
    }
    
    static {
        random = new Random();
    }
}
