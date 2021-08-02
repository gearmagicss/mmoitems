// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

public enum ServicePriority
{
    Lowest("Lowest", 0), 
    Low("Low", 1), 
    Normal("Normal", 2), 
    High("High", 3), 
    Highest("Highest", 4);
    
    private ServicePriority(final String name, final int ordinal) {
    }
}
