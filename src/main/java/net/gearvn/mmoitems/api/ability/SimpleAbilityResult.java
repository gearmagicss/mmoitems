// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.ability;

import net.Indyuce.mmoitems.stat.data.AbilityData;

public class SimpleAbilityResult extends AbilityResult
{
    private final boolean successful;
    
    public SimpleAbilityResult(final AbilityData abilityData) {
        this(abilityData, true);
    }
    
    public SimpleAbilityResult(final AbilityData abilityData, final boolean successful) {
        super(abilityData);
        this.successful = successful;
    }
    
    @Override
    public boolean isSuccessful() {
        return this.successful;
    }
}
