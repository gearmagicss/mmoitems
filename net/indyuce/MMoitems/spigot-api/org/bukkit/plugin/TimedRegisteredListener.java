// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;

public class TimedRegisteredListener extends RegisteredListener
{
    private int count;
    private long totalTime;
    private Class<? extends Event> eventClass;
    private boolean multiple;
    
    public TimedRegisteredListener(final Listener pluginListener, final EventExecutor eventExecutor, final EventPriority eventPriority, final Plugin registeredPlugin, final boolean listenCancelled) {
        super(pluginListener, eventExecutor, eventPriority, registeredPlugin, listenCancelled);
        this.multiple = false;
    }
    
    @Override
    public void callEvent(final Event event) throws EventException {
        if (event.isAsynchronous()) {
            super.callEvent(event);
            return;
        }
        ++this.count;
        final Class<? extends Event> newEventClass = event.getClass();
        if (this.eventClass == null) {
            this.eventClass = newEventClass;
        }
        else if (!this.eventClass.equals(newEventClass)) {
            this.multiple = true;
            this.eventClass = getCommonSuperclass(newEventClass, this.eventClass).asSubclass(Event.class);
        }
        final long start = System.nanoTime();
        super.callEvent(event);
        this.totalTime += System.nanoTime() - start;
    }
    
    private static Class<?> getCommonSuperclass(Class<?> class1, final Class<?> class2) {
        while (!class1.isAssignableFrom(class2)) {
            class1 = class1.getSuperclass();
        }
        return class1;
    }
    
    public void reset() {
        this.count = 0;
        this.totalTime = 0L;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public long getTotalTime() {
        return this.totalTime;
    }
    
    public Class<? extends Event> getEventClass() {
        return this.eventClass;
    }
    
    public boolean hasMultiple() {
        return this.multiple;
    }
}
