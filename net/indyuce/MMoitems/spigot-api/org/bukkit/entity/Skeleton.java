// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface Skeleton extends Monster
{
    SkeletonType getSkeletonType();
    
    void setSkeletonType(final SkeletonType p0);
    
    public enum SkeletonType
    {
        NORMAL("NORMAL", 0), 
        WITHER("WITHER", 1), 
        STRAY("STRAY", 2);
        
        private SkeletonType(final String name, final int ordinal) {
        }
    }
}
