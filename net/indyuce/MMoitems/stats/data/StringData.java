// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class StringData implements StatData, RandomStatData
{
    private String value;
    
    public StringData(final String value) {
        this.value = value;
    }
    
    public void setString(final String value) {
        this.value = value;
    }
    
    public String getString() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
