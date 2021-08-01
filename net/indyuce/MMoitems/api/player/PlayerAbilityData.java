// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.Indyuce.mmoitems.comp.mythicmobs.MythicMobsAbility;
import java.util.HashMap;
import java.util.Map;

public class PlayerAbilityData
{
    private final Map<String, CachedModifier> cache;
    
    public PlayerAbilityData() {
        this.cache = new HashMap<String, CachedModifier>();
    }
    
    public double getCachedModifier(final String s) {
        return this.cache.containsKey(s) ? this.cache.get(s).getValue() : 0.0;
    }
    
    public void cacheModifiers(final MythicMobsAbility mythicMobsAbility, final AbilityData abilityData) {
        for (final String s : abilityData.getModifiers()) {
            this.cacheModifier(mythicMobsAbility, s, abilityData.getModifier(s));
        }
    }
    
    public void cacheModifier(final MythicMobsAbility mythicMobsAbility, final String str, final double n) {
        this.cache.put(mythicMobsAbility.getInternalName() + "." + str, new CachedModifier(n));
    }
    
    public void refresh() {
        this.cache.values().removeIf(CachedModifier::isTimedOut);
    }
    
    public static class CachedModifier
    {
        private final long date;
        private final double value;
        
        public CachedModifier(final double value) {
            this.date = System.currentTimeMillis();
            this.value = value;
        }
        
        public boolean isTimedOut() {
            return this.date + 60000L < System.currentTimeMillis();
        }
        
        public double getValue() {
            return this.value;
        }
    }
}
