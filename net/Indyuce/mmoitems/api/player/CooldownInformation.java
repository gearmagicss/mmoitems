// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

public class CooldownInformation
{
    private final long castTime;
    private final long cooldown;
    private long available;
    
    public CooldownInformation(final double n) {
        this.castTime = System.currentTimeMillis();
        this.cooldown = (long)(n * 1000.0);
        this.available = (long)(System.currentTimeMillis() + n * 1000.0);
    }
    
    public double getInitialCooldown() {
        return this.cooldown / 1000.0;
    }
    
    public boolean hasCooledDown() {
        return System.currentTimeMillis() > this.available;
    }
    
    public double getRemaining() {
        return Math.max(0L, this.available - System.currentTimeMillis()) / 1000.0;
    }
    
    public long getCastTime() {
        return this.castTime;
    }
    
    public void reduceCooldown(final double n) {
        this.available -= (long)(n * 100.0);
    }
}
