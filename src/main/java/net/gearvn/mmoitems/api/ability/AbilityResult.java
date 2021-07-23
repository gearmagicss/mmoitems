// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.ability;

import net.Indyuce.mmoitems.stat.data.AbilityData;

public abstract class AbilityResult
{
    private final AbilityData ability;
    
    public AbilityResult(final AbilityData ability) {
        this.ability = ability;
    }
    
    public AbilityData getAbility() {
        return this.ability;
    }
    
    public double getModifier(final String s) {
        return this.ability.getModifier(s);
    }
    
    public abstract boolean isSuccessful();
}
