// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

public interface PluginAwareness
{
    public enum Flags implements PluginAwareness
    {
        @Deprecated
        UTF8("UTF8", 0);
        
        private Flags(final String name, final int ordinal) {
        }
    }
}
