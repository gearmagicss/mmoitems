// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin.messaging;

public class ChannelNotRegisteredException extends RuntimeException
{
    public ChannelNotRegisteredException() {
        this("Attempted to send a plugin message through an unregistered channel.");
    }
    
    public ChannelNotRegisteredException(final String channel) {
        super("Attempted to send a plugin message through the unregistered channel `" + channel + "'.");
    }
}
