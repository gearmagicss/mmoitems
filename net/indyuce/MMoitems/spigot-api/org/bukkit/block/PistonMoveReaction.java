// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import java.util.HashMap;
import java.util.Map;

public enum PistonMoveReaction
{
    MOVE("MOVE", 0, 0), 
    BREAK("BREAK", 1, 1), 
    BLOCK("BLOCK", 2, 2);
    
    private int id;
    private static Map<Integer, PistonMoveReaction> byId;
    
    static {
        PistonMoveReaction.byId = new HashMap<Integer, PistonMoveReaction>();
        PistonMoveReaction[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PistonMoveReaction reaction = values[i];
            PistonMoveReaction.byId.put(reaction.id, reaction);
        }
    }
    
    private PistonMoveReaction(final String name, final int ordinal, final int id) {
        this.id = id;
    }
    
    @Deprecated
    public int getId() {
        return this.id;
    }
    
    @Deprecated
    public static PistonMoveReaction getById(final int id) {
        return PistonMoveReaction.byId.get(id);
    }
}
