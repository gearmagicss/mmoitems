// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.recipe;

public enum SmithingCombinationType
{
    ADDITIVE, 
    MAXIMUM, 
    EVEN, 
    MINIMUM, 
    NONE;
    
    private static /* synthetic */ SmithingCombinationType[] $values() {
        return new SmithingCombinationType[] { SmithingCombinationType.ADDITIVE, SmithingCombinationType.MAXIMUM, SmithingCombinationType.EVEN, SmithingCombinationType.MINIMUM, SmithingCombinationType.NONE };
    }
    
    static {
        $VALUES = $values();
    }
}
