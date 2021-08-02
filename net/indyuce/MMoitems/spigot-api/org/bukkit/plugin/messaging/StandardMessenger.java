// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin.messaging;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import java.util.Set;
import java.util.Map;

public class StandardMessenger implements Messenger
{
    private final Map<String, Set<PluginMessageListenerRegistration>> incomingByChannel;
    private final Map<Plugin, Set<PluginMessageListenerRegistration>> incomingByPlugin;
    private final Map<String, Set<Plugin>> outgoingByChannel;
    private final Map<Plugin, Set<String>> outgoingByPlugin;
    private final Object incomingLock;
    private final Object outgoingLock;
    
    public StandardMessenger() {
        this.incomingByChannel = new HashMap<String, Set<PluginMessageListenerRegistration>>();
        this.incomingByPlugin = new HashMap<Plugin, Set<PluginMessageListenerRegistration>>();
        this.outgoingByChannel = new HashMap<String, Set<Plugin>>();
        this.outgoingByPlugin = new HashMap<Plugin, Set<String>>();
        this.incomingLock = new Object();
        this.outgoingLock = new Object();
    }
    
    private void addToOutgoing(final Plugin plugin, final String channel) {
        synchronized (this.outgoingLock) {
            Set<Plugin> plugins = this.outgoingByChannel.get(channel);
            Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (plugins == null) {
                plugins = new HashSet<Plugin>();
                this.outgoingByChannel.put(channel, plugins);
            }
            if (channels == null) {
                channels = new HashSet<String>();
                this.outgoingByPlugin.put(plugin, channels);
            }
            plugins.add(plugin);
            channels.add(channel);
        }
        // monitorexit(this.outgoingLock)
    }
    
    private void removeFromOutgoing(final Plugin plugin, final String channel) {
        synchronized (this.outgoingLock) {
            final Set<Plugin> plugins = this.outgoingByChannel.get(channel);
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (plugins != null) {
                plugins.remove(plugin);
                if (plugins.isEmpty()) {
                    this.outgoingByChannel.remove(channel);
                }
            }
            if (channels != null) {
                channels.remove(channel);
                if (channels.isEmpty()) {
                    this.outgoingByChannel.remove(channel);
                }
            }
        }
        // monitorexit(this.outgoingLock)
    }
    
    private void removeFromOutgoing(final Plugin plugin) {
        synchronized (this.outgoingLock) {
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (channels != null) {
                final String[] toRemove = channels.toArray(new String[0]);
                this.outgoingByPlugin.remove(plugin);
                String[] array;
                for (int length = (array = toRemove).length, i = 0; i < length; ++i) {
                    final String channel = array[i];
                    this.removeFromOutgoing(plugin, channel);
                }
            }
        }
        // monitorexit(this.outgoingLock)
    }
    
    private void addToIncoming(final PluginMessageListenerRegistration registration) {
        synchronized (this.incomingLock) {
            Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(registration.getChannel());
            if (registrations == null) {
                registrations = new HashSet<PluginMessageListenerRegistration>();
                this.incomingByChannel.put(registration.getChannel(), registrations);
            }
            else if (registrations.contains(registration)) {
                throw new IllegalArgumentException("This registration already exists");
            }
            registrations.add(registration);
            registrations = this.incomingByPlugin.get(registration.getPlugin());
            if (registrations == null) {
                registrations = new HashSet<PluginMessageListenerRegistration>();
                this.incomingByPlugin.put(registration.getPlugin(), registrations);
            }
            else if (registrations.contains(registration)) {
                throw new IllegalArgumentException("This registration already exists");
            }
            registrations.add(registration);
        }
        // monitorexit(this.incomingLock)
    }
    
    private void removeFromIncoming(final PluginMessageListenerRegistration registration) {
        synchronized (this.incomingLock) {
            Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(registration.getChannel());
            if (registrations != null) {
                registrations.remove(registration);
                if (registrations.isEmpty()) {
                    this.incomingByChannel.remove(registration.getChannel());
                }
            }
            registrations = this.incomingByPlugin.get(registration.getPlugin());
            if (registrations != null) {
                registrations.remove(registration);
                if (registrations.isEmpty()) {
                    this.incomingByPlugin.remove(registration.getPlugin());
                }
            }
        }
        // monitorexit(this.incomingLock)
    }
    
    private void removeFromIncoming(final Plugin plugin, final String channel) {
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final PluginMessageListenerRegistration[] toRemove = registrations.toArray(new PluginMessageListenerRegistration[0]);
                PluginMessageListenerRegistration[] array;
                for (int length = (array = toRemove).length, i = 0; i < length; ++i) {
                    final PluginMessageListenerRegistration registration = array[i];
                    if (registration.getChannel().equals(channel)) {
                        this.removeFromIncoming(registration);
                    }
                }
            }
        }
        // monitorexit(this.incomingLock)
    }
    
    private void removeFromIncoming(final Plugin plugin) {
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final PluginMessageListenerRegistration[] toRemove = registrations.toArray(new PluginMessageListenerRegistration[0]);
                this.incomingByPlugin.remove(plugin);
                PluginMessageListenerRegistration[] array;
                for (int length = (array = toRemove).length, i = 0; i < length; ++i) {
                    final PluginMessageListenerRegistration registration = array[i];
                    this.removeFromIncoming(registration);
                }
            }
        }
        // monitorexit(this.incomingLock)
    }
    
    @Override
    public boolean isReservedChannel(final String channel) {
        validateChannel(channel);
        return channel.equals("REGISTER") || channel.equals("UNREGISTER");
    }
    
    @Override
    public void registerOutgoingPluginChannel(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        if (this.isReservedChannel(channel)) {
            throw new ReservedChannelException(channel);
        }
        this.addToOutgoing(plugin, channel);
    }
    
    @Override
    public void unregisterOutgoingPluginChannel(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        this.removeFromOutgoing(plugin, channel);
    }
    
    @Override
    public void unregisterOutgoingPluginChannel(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.removeFromOutgoing(plugin);
    }
    
    @Override
    public PluginMessageListenerRegistration registerIncomingPluginChannel(final Plugin plugin, final String channel, final PluginMessageListener listener) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        if (this.isReservedChannel(channel)) {
            throw new ReservedChannelException(channel);
        }
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        final PluginMessageListenerRegistration result = new PluginMessageListenerRegistration(this, plugin, channel, listener);
        this.addToIncoming(result);
        return result;
    }
    
    @Override
    public void unregisterIncomingPluginChannel(final Plugin plugin, final String channel, final PluginMessageListener listener) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        validateChannel(channel);
        this.removeFromIncoming(new PluginMessageListenerRegistration(this, plugin, channel, listener));
    }
    
    @Override
    public void unregisterIncomingPluginChannel(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        this.removeFromIncoming(plugin, channel);
    }
    
    @Override
    public void unregisterIncomingPluginChannel(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.removeFromIncoming(plugin);
    }
    
    @Override
    public Set<String> getOutgoingChannels() {
        synchronized (this.outgoingLock) {
            final Set<String> keys = this.outgoingByChannel.keySet();
            final ImmutableSet<Object> copy = ImmutableSet.copyOf((Collection<?>)keys);
            // monitorexit(this.outgoingLock)
            return (Set<String>)copy;
        }
    }
    
    @Override
    public Set<String> getOutgoingChannels(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        synchronized (this.outgoingLock) {
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (channels != null) {
                final ImmutableSet<Object> copy = ImmutableSet.copyOf((Collection<?>)channels);
                // monitorexit(this.outgoingLock)
                return (Set<String>)copy;
            }
            final ImmutableSet<Object> of = ImmutableSet.of();
            // monitorexit(this.outgoingLock)
            return (Set<String>)of;
        }
    }
    
    @Override
    public Set<String> getIncomingChannels() {
        synchronized (this.incomingLock) {
            final Set<String> keys = this.incomingByChannel.keySet();
            final ImmutableSet<Object> copy = ImmutableSet.copyOf((Collection<?>)keys);
            // monitorexit(this.incomingLock)
            return (Set<String>)copy;
        }
    }
    
    @Override
    public Set<String> getIncomingChannels(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
                for (final PluginMessageListenerRegistration registration : registrations) {
                    builder.add(registration.getChannel());
                }
                // monitorexit(this.incomingLock)
                return builder.build();
            }
            final ImmutableSet<Object> of = ImmutableSet.of();
            // monitorexit(this.incomingLock)
            return (Set<String>)of;
        }
    }
    
    @Override
    public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final ImmutableSet<Object> copy = ImmutableSet.copyOf((Collection<?>)registrations);
                // monitorexit(this.incomingLock)
                return (Set<PluginMessageListenerRegistration>)copy;
            }
            final ImmutableSet<Object> of = ImmutableSet.of();
            // monitorexit(this.incomingLock)
            return (Set<PluginMessageListenerRegistration>)of;
        }
    }
    
    @Override
    public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final String channel) {
        validateChannel(channel);
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByChannel.get(channel);
            if (registrations != null) {
                final ImmutableSet<Object> copy = ImmutableSet.copyOf((Collection<?>)registrations);
                // monitorexit(this.incomingLock)
                return (Set<PluginMessageListenerRegistration>)copy;
            }
            final ImmutableSet<Object> of = ImmutableSet.of();
            // monitorexit(this.incomingLock)
            return (Set<PluginMessageListenerRegistration>)of;
        }
    }
    
    @Override
    public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                final ImmutableSet.Builder<PluginMessageListenerRegistration> builder = ImmutableSet.builder();
                for (final PluginMessageListenerRegistration registration : registrations) {
                    if (registration.getChannel().equals(channel)) {
                        builder.add(registration);
                    }
                }
                // monitorexit(this.incomingLock)
                return builder.build();
            }
            final ImmutableSet<Object> of = ImmutableSet.of();
            // monitorexit(this.incomingLock)
            return (Set<PluginMessageListenerRegistration>)of;
        }
    }
    
    @Override
    public boolean isRegistrationValid(final PluginMessageListenerRegistration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("Registration cannot be null");
        }
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(registration.getPlugin());
            if (registrations != null) {
                // monitorexit(this.incomingLock)
                return registrations.contains(registration);
            }
            // monitorexit(this.incomingLock)
            return false;
        }
    }
    
    @Override
    public boolean isIncomingChannelRegistered(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        synchronized (this.incomingLock) {
            final Set<PluginMessageListenerRegistration> registrations = this.incomingByPlugin.get(plugin);
            if (registrations != null) {
                for (final PluginMessageListenerRegistration registration : registrations) {
                    if (registration.getChannel().equals(channel)) {
                        // monitorexit(this.incomingLock)
                        return true;
                    }
                }
            }
            // monitorexit(this.incomingLock)
            return false;
        }
    }
    
    @Override
    public boolean isOutgoingChannelRegistered(final Plugin plugin, final String channel) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        validateChannel(channel);
        synchronized (this.outgoingLock) {
            final Set<String> channels = this.outgoingByPlugin.get(plugin);
            if (channels != null) {
                // monitorexit(this.outgoingLock)
                return channels.contains(channel);
            }
            // monitorexit(this.outgoingLock)
            return false;
        }
    }
    
    @Override
    public void dispatchIncomingMessage(final Player source, final String channel, final byte[] message) {
        if (source == null) {
            throw new IllegalArgumentException("Player source cannot be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        validateChannel(channel);
        final Set<PluginMessageListenerRegistration> registrations = this.getIncomingChannelRegistrations(channel);
        for (final PluginMessageListenerRegistration registration : registrations) {
            try {
                registration.getListener().onPluginMessageReceived(channel, source, message);
            }
            catch (Throwable t) {
                Bukkit.getLogger().log(Level.WARNING, "Could not pass incoming plugin message to " + registration.getPlugin(), t);
            }
        }
    }
    
    public static void validateChannel(final String channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        if (channel.length() > 20) {
            throw new ChannelNameTooLongException(channel);
        }
    }
    
    public static void validatePluginMessage(final Messenger messenger, final Plugin source, final String channel, final byte[] message) {
        if (messenger == null) {
            throw new IllegalArgumentException("Messenger cannot be null");
        }
        if (source == null) {
            throw new IllegalArgumentException("Plugin source cannot be null");
        }
        if (!source.isEnabled()) {
            throw new IllegalArgumentException("Plugin must be enabled to send messages");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (!messenger.isOutgoingChannelRegistered(source, channel)) {
            throw new ChannelNotRegisteredException(channel);
        }
        if (message.length > 32766) {
            throw new MessageTooLargeException(message);
        }
        validateChannel(channel);
    }
}
