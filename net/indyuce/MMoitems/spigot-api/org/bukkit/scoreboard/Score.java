// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;

public interface Score
{
    @Deprecated
    OfflinePlayer getPlayer();
    
    String getEntry();
    
    Objective getObjective();
    
    int getScore() throws IllegalStateException;
    
    void setScore(final int p0) throws IllegalStateException;
    
    boolean isScoreSet() throws IllegalStateException;
    
    Scoreboard getScoreboard();
}
