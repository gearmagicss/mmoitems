// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

public enum Action
{
    LEFT_CLICK_BLOCK("LEFT_CLICK_BLOCK", 0), 
    RIGHT_CLICK_BLOCK("RIGHT_CLICK_BLOCK", 1), 
    LEFT_CLICK_AIR("LEFT_CLICK_AIR", 2), 
    RIGHT_CLICK_AIR("RIGHT_CLICK_AIR", 3), 
    PHYSICAL("PHYSICAL", 4);
    
    private Action(final String name, final int ordinal) {
    }
}
