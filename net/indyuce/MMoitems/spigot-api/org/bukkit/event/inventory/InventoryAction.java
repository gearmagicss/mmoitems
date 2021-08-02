// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

public enum InventoryAction
{
    NOTHING("NOTHING", 0), 
    PICKUP_ALL("PICKUP_ALL", 1), 
    PICKUP_SOME("PICKUP_SOME", 2), 
    PICKUP_HALF("PICKUP_HALF", 3), 
    PICKUP_ONE("PICKUP_ONE", 4), 
    PLACE_ALL("PLACE_ALL", 5), 
    PLACE_SOME("PLACE_SOME", 6), 
    PLACE_ONE("PLACE_ONE", 7), 
    SWAP_WITH_CURSOR("SWAP_WITH_CURSOR", 8), 
    DROP_ALL_CURSOR("DROP_ALL_CURSOR", 9), 
    DROP_ONE_CURSOR("DROP_ONE_CURSOR", 10), 
    DROP_ALL_SLOT("DROP_ALL_SLOT", 11), 
    DROP_ONE_SLOT("DROP_ONE_SLOT", 12), 
    MOVE_TO_OTHER_INVENTORY("MOVE_TO_OTHER_INVENTORY", 13), 
    HOTBAR_MOVE_AND_READD("HOTBAR_MOVE_AND_READD", 14), 
    HOTBAR_SWAP("HOTBAR_SWAP", 15), 
    CLONE_STACK("CLONE_STACK", 16), 
    COLLECT_TO_CURSOR("COLLECT_TO_CURSOR", 17), 
    UNKNOWN("UNKNOWN", 18);
    
    private InventoryAction(final String name, final int ordinal) {
    }
}
