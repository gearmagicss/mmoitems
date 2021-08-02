// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

public enum Rotation
{
    NONE("NONE", 0), 
    CLOCKWISE_45("CLOCKWISE_45", 1), 
    CLOCKWISE("CLOCKWISE", 2), 
    CLOCKWISE_135("CLOCKWISE_135", 3), 
    FLIPPED("FLIPPED", 4), 
    FLIPPED_45("FLIPPED_45", 5), 
    COUNTER_CLOCKWISE("COUNTER_CLOCKWISE", 6), 
    COUNTER_CLOCKWISE_45("COUNTER_CLOCKWISE_45", 7);
    
    private static final Rotation[] rotations;
    
    static {
        rotations = values();
    }
    
    private Rotation(final String name, final int ordinal) {
    }
    
    public Rotation rotateClockwise() {
        return Rotation.rotations[this.ordinal() + 1 & 0x7];
    }
    
    public Rotation rotateCounterClockwise() {
        return Rotation.rotations[this.ordinal() - 1 & 0x7];
    }
}
