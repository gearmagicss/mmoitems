// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import java.util.HashMap;
import java.util.Map;

public class StringValue
{
    private final String name;
    private final double value;
    
    public StringValue(final String name, final double value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getValue() {
        return this.value;
    }
    
    @Deprecated
    public static Map<String, Double> readFromArray(final StringValue... array) {
        final HashMap<String, Double> hashMap = new HashMap<String, Double>();
        for (final StringValue stringValue : array) {
            hashMap.put(stringValue.getName(), stringValue.getValue());
        }
        return hashMap;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof StringValue && ((StringValue)o).name.equals(this.name) && ((StringValue)o).value == this.value;
    }
}
