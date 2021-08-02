// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

public abstract class PluginBase implements Plugin
{
    @Override
    public final int hashCode() {
        return this.getName().hashCode();
    }
    
    @Override
    public final boolean equals(final Object obj) {
        return this == obj || (obj != null && obj instanceof Plugin && this.getName().equals(((Plugin)obj).getName()));
    }
    
    @Override
    public final String getName() {
        return this.getDescription().getName();
    }
}
