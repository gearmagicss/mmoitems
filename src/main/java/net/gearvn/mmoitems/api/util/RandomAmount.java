// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import java.util.Random;

public class RandomAmount
{
    private final int min;
    private final int max;
    private static final Random RANDOM;
    
    public RandomAmount(final int min, final int max) {
        this.min = min;
        this.max = max;
    }
    
    public RandomAmount(final String s) {
        final String[] split = s.split("-");
        this.min = Integer.parseInt(split[0]);
        this.max = ((split.length > 1) ? Integer.parseInt(split[1]) : 0);
    }
    
    public int getMin() {
        return this.min;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public int getRandomAmount() {
        return (this.max > 0) ? (this.min + RandomAmount.RANDOM.nextInt(this.max - this.min + 1)) : this.min;
    }
    
    static {
        RANDOM = new Random();
    }
}
