// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.material.Colorable;

public interface Sheep extends Animals, Colorable
{
    boolean isSheared();
    
    void setSheared(final boolean p0);
}
