// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class TemporaryListener implements Listener
{
    private final HandlerList[] lists;
    private boolean closed;
    
    public TemporaryListener(final HandlerList... lists) {
        this.lists = lists;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
    }
    
    public void close(final long n) {
        Bukkit.getScheduler().runTaskLater((Plugin)MMOItems.plugin, this::close, n);
    }
    
    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        final HandlerList[] lists = this.lists;
        for (int length = lists.length, i = 0; i < length; ++i) {
            lists[i].unregister((Listener)this);
        }
    }
}
