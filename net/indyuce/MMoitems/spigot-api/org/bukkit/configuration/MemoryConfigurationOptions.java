// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.configuration;

public class MemoryConfigurationOptions extends ConfigurationOptions
{
    protected MemoryConfigurationOptions(final MemoryConfiguration configuration) {
        super(configuration);
    }
    
    @Override
    public MemoryConfiguration configuration() {
        return (MemoryConfiguration)super.configuration();
    }
    
    @Override
    public MemoryConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }
    
    @Override
    public MemoryConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }
}
