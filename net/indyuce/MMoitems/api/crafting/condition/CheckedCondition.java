// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

public class CheckedCondition
{
    private final Condition condition;
    private final boolean met;
    
    public CheckedCondition(final Condition condition, final boolean met) {
        this.condition = condition;
        this.met = met;
    }
    
    public boolean isMet() {
        return this.met;
    }
    
    public Condition getCondition() {
        return this.condition;
    }
    
    public String format() {
        return this.condition.formatDisplay(this.isMet() ? this.condition.getDisplay().getPositive() : this.condition.getDisplay().getNegative());
    }
}
