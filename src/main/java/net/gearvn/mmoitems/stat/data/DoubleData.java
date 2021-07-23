// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class DoubleData implements StatData, Mergeable
{
    private double value;
    
    public DoubleData(final double value) {
        this.value = value;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(final double value) {
        this.value = value;
    }
    
    public void add(final double n) {
        this.value += n;
    }
    
    public void addRelative(final double n) {
        this.value *= 1.0 + n;
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof DoubleData, "Cannot merge two different stat data types");
        this.value += ((DoubleData)statData).value;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof DoubleData && ((DoubleData)o).getValue() == this.getValue();
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new DoubleData(this.getValue());
    }
    
    @Override
    public boolean isClear() {
        return this.getValue() == 0.0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }
}
