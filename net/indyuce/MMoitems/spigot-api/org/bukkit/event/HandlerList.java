// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event;

import java.util.Map;
import java.util.ListIterator;
import java.util.Collection;
import org.bukkit.plugin.Plugin;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.EnumMap;
import org.bukkit.plugin.RegisteredListener;

public class HandlerList
{
    private volatile RegisteredListener[] handlers;
    private final EnumMap<EventPriority, ArrayList<RegisteredListener>> handlerslots;
    private static ArrayList<HandlerList> allLists;
    
    static {
        HandlerList.allLists = new ArrayList<HandlerList>();
    }
    
    public static void bakeAll() {
        synchronized (HandlerList.allLists) {
            for (final HandlerList h : HandlerList.allLists) {
                h.bake();
            }
        }
        // monitorexit(HandlerList.allLists)
    }
    
    public static void unregisterAll() {
        synchronized (HandlerList.allLists) {
            for (final HandlerList h : HandlerList.allLists) {
                try {
                    for (final List<RegisteredListener> list : h.handlerslots.values()) {
                        list.clear();
                    }
                    h.handlers = null;
                }
                // monitorexit(list2)
                finally {}
            }
        }
        // monitorexit(HandlerList.allLists)
    }
    
    public static void unregisterAll(final Plugin plugin) {
        synchronized (HandlerList.allLists) {
            for (final HandlerList h : HandlerList.allLists) {
                h.unregister(plugin);
            }
        }
        // monitorexit(HandlerList.allLists)
    }
    
    public static void unregisterAll(final Listener listener) {
        synchronized (HandlerList.allLists) {
            for (final HandlerList h : HandlerList.allLists) {
                h.unregister(listener);
            }
        }
        // monitorexit(HandlerList.allLists)
    }
    
    public HandlerList() {
        this.handlers = null;
        this.handlerslots = new EnumMap<EventPriority, ArrayList<RegisteredListener>>(EventPriority.class);
        EventPriority[] values;
        for (int length = (values = EventPriority.values()).length, i = 0; i < length; ++i) {
            final EventPriority o = values[i];
            this.handlerslots.put(o, new ArrayList<RegisteredListener>());
        }
        synchronized (HandlerList.allLists) {
            HandlerList.allLists.add(this);
        }
        // monitorexit(HandlerList.allLists)
    }
    
    public synchronized void register(final RegisteredListener listener) {
        if (this.handlerslots.get(listener.getPriority()).contains(listener)) {
            throw new IllegalStateException("This listener is already registered to priority " + listener.getPriority().toString());
        }
        this.handlers = null;
        this.handlerslots.get(listener.getPriority()).add(listener);
    }
    
    public void registerAll(final Collection<RegisteredListener> listeners) {
        for (final RegisteredListener listener : listeners) {
            this.register(listener);
        }
    }
    
    public synchronized void unregister(final RegisteredListener listener) {
        if (this.handlerslots.get(listener.getPriority()).remove(listener)) {
            this.handlers = null;
        }
    }
    
    public synchronized void unregister(final Plugin plugin) {
        boolean changed = false;
        for (final List<RegisteredListener> list : this.handlerslots.values()) {
            final ListIterator<RegisteredListener> i = list.listIterator();
            while (i.hasNext()) {
                if (i.next().getPlugin().equals(plugin)) {
                    i.remove();
                    changed = true;
                }
            }
        }
        if (changed) {
            this.handlers = null;
        }
    }
    
    public synchronized void unregister(final Listener listener) {
        boolean changed = false;
        for (final List<RegisteredListener> list : this.handlerslots.values()) {
            final ListIterator<RegisteredListener> i = list.listIterator();
            while (i.hasNext()) {
                if (i.next().getListener().equals(listener)) {
                    i.remove();
                    changed = true;
                }
            }
        }
        if (changed) {
            this.handlers = null;
        }
    }
    
    public synchronized void bake() {
        if (this.handlers != null) {
            return;
        }
        final List<RegisteredListener> entries = new ArrayList<RegisteredListener>();
        for (final Map.Entry<EventPriority, ArrayList<RegisteredListener>> entry : this.handlerslots.entrySet()) {
            entries.addAll(entry.getValue());
        }
        this.handlers = entries.toArray(new RegisteredListener[entries.size()]);
    }
    
    public RegisteredListener[] getRegisteredListeners() {
        RegisteredListener[] handlers;
        while ((handlers = this.handlers) == null) {
            this.bake();
        }
        return handlers;
    }
    
    public static ArrayList<RegisteredListener> getRegisteredListeners(final Plugin plugin) {
        final ArrayList<RegisteredListener> listeners = new ArrayList<RegisteredListener>();
        synchronized (HandlerList.allLists) {
            for (final HandlerList h : HandlerList.allLists) {
                try {
                    for (final List<RegisteredListener> list : h.handlerslots.values()) {
                        for (final RegisteredListener listener : list) {
                            if (listener.getPlugin().equals(plugin)) {
                                listeners.add(listener);
                            }
                        }
                    }
                }
                // monitorexit(list2)
                finally {}
            }
        }
        // monitorexit(HandlerList.allLists)
        return listeners;
    }
    
    public static ArrayList<HandlerList> getHandlerLists() {
        synchronized (HandlerList.allLists) {
            final ArrayList list = (ArrayList)HandlerList.allLists.clone();
            // monitorexit(HandlerList.allLists)
            return (ArrayList<HandlerList>)list;
        }
    }
}
