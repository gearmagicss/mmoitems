// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.scoreboard;

@Deprecated
public enum NameTagVisibility
{
    ALWAYS("ALWAYS", 0), 
    NEVER("NEVER", 1), 
    HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2), 
    HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3);
    
    private NameTagVisibility(final String name, final int ordinal) {
    }
}
