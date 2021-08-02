// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event;

public enum EventPriority
{
    LOWEST("LOWEST", 0, 0), 
    LOW("LOW", 1, 1), 
    NORMAL("NORMAL", 2, 2), 
    HIGH("HIGH", 3, 3), 
    HIGHEST("HIGHEST", 4, 4), 
    MONITOR("MONITOR", 5, 5);
    
    private final int slot;
    
    private EventPriority(final String name, final int ordinal, final int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
}
