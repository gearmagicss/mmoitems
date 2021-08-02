// 
// Decompiled by Procyon v0.5.36
// 

package net.md_5.bungee.api.chat;

import java.beans.ConstructorProperties;
import java.util.Arrays;

public final class HoverEvent
{
    private final Action action;
    private final BaseComponent[] value;
    
    public Action getAction() {
        return this.action;
    }
    
    public BaseComponent[] getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "HoverEvent(action=" + this.getAction() + ", value=" + Arrays.deepToString(this.getValue()) + ")";
    }
    
    @ConstructorProperties({ "action", "value" })
    public HoverEvent(final Action action, final BaseComponent[] value) {
        this.action = action;
        this.value = value;
    }
    
    public enum Action
    {
        SHOW_TEXT, 
        SHOW_ACHIEVEMENT, 
        SHOW_ITEM, 
        SHOW_ENTITY;
    }
}
