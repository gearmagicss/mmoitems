// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.attribute;

import java.util.Collection;

public interface AttributeInstance
{
    Attribute getAttribute();
    
    double getBaseValue();
    
    void setBaseValue(final double p0);
    
    Collection<AttributeModifier> getModifiers();
    
    void addModifier(final AttributeModifier p0);
    
    void removeModifier(final AttributeModifier p0);
    
    double getValue();
}
