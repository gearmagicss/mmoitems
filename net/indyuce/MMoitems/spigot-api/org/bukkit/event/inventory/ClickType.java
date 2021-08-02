// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

public enum ClickType
{
    LEFT("LEFT", 0), 
    SHIFT_LEFT("SHIFT_LEFT", 1), 
    RIGHT("RIGHT", 2), 
    SHIFT_RIGHT("SHIFT_RIGHT", 3), 
    WINDOW_BORDER_LEFT("WINDOW_BORDER_LEFT", 4), 
    WINDOW_BORDER_RIGHT("WINDOW_BORDER_RIGHT", 5), 
    MIDDLE("MIDDLE", 6), 
    NUMBER_KEY("NUMBER_KEY", 7), 
    DOUBLE_CLICK("DOUBLE_CLICK", 8), 
    DROP("DROP", 9), 
    CONTROL_DROP("CONTROL_DROP", 10), 
    CREATIVE("CREATIVE", 11), 
    UNKNOWN("UNKNOWN", 12);
    
    private ClickType(final String name, final int ordinal) {
    }
    
    public boolean isKeyboardClick() {
        return this == ClickType.NUMBER_KEY || this == ClickType.DROP || this == ClickType.CONTROL_DROP;
    }
    
    public boolean isCreativeAction() {
        return this == ClickType.MIDDLE || this == ClickType.CREATIVE;
    }
    
    public boolean isRightClick() {
        return this == ClickType.RIGHT || this == ClickType.SHIFT_RIGHT;
    }
    
    public boolean isLeftClick() {
        return this == ClickType.LEFT || this == ClickType.SHIFT_LEFT || this == ClickType.DOUBLE_CLICK || this == ClickType.CREATIVE;
    }
    
    public boolean isShiftClick() {
        return this == ClickType.SHIFT_LEFT || this == ClickType.SHIFT_RIGHT || this == ClickType.CONTROL_DROP;
    }
}
