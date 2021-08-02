// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

public enum ItemFlag
{
    HIDE_ENCHANTS("HIDE_ENCHANTS", 0), 
    HIDE_ATTRIBUTES("HIDE_ATTRIBUTES", 1), 
    HIDE_UNBREAKABLE("HIDE_UNBREAKABLE", 2), 
    HIDE_DESTROYS("HIDE_DESTROYS", 3), 
    HIDE_PLACED_ON("HIDE_PLACED_ON", 4), 
    HIDE_POTION_EFFECTS("HIDE_POTION_EFFECTS", 5);
    
    private ItemFlag(final String name, final int ordinal) {
    }
}
