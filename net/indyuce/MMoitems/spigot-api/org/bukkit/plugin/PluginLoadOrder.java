// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

public enum PluginLoadOrder
{
    STARTUP("STARTUP", 0), 
    POSTWORLD("POSTWORLD", 1);
    
    private PluginLoadOrder(final String name, final int ordinal) {
    }
}
