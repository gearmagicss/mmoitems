// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.stat.data.type.StatData;

public class BooleanData implements StatData
{
    private final boolean state;
    
    public BooleanData(final boolean state) {
        this.state = state;
    }
    
    public boolean isEnabled() {
        return this.state;
    }
    
    @Override
    public String toString() {
        return "" + this.state;
    }
}
