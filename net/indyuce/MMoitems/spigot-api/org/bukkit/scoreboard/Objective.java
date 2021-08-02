// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;

public interface Objective
{
    String getName() throws IllegalStateException;
    
    String getDisplayName() throws IllegalStateException;
    
    void setDisplayName(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    String getCriteria() throws IllegalStateException;
    
    boolean isModifiable() throws IllegalStateException;
    
    Scoreboard getScoreboard();
    
    void unregister() throws IllegalStateException;
    
    void setDisplaySlot(final DisplaySlot p0) throws IllegalStateException;
    
    DisplaySlot getDisplaySlot() throws IllegalStateException;
    
    @Deprecated
    Score getScore(final OfflinePlayer p0) throws IllegalArgumentException, IllegalStateException;
    
    Score getScore(final String p0) throws IllegalArgumentException, IllegalStateException;
}
