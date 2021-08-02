// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;
import java.util.Set;

public interface Scoreboard
{
    Objective registerNewObjective(final String p0, final String p1) throws IllegalArgumentException;
    
    Objective getObjective(final String p0) throws IllegalArgumentException;
    
    Set<Objective> getObjectivesByCriteria(final String p0) throws IllegalArgumentException;
    
    Set<Objective> getObjectives();
    
    Objective getObjective(final DisplaySlot p0) throws IllegalArgumentException;
    
    @Deprecated
    Set<Score> getScores(final OfflinePlayer p0) throws IllegalArgumentException;
    
    Set<Score> getScores(final String p0) throws IllegalArgumentException;
    
    @Deprecated
    void resetScores(final OfflinePlayer p0) throws IllegalArgumentException;
    
    void resetScores(final String p0) throws IllegalArgumentException;
    
    @Deprecated
    Team getPlayerTeam(final OfflinePlayer p0) throws IllegalArgumentException;
    
    Team getEntryTeam(final String p0) throws IllegalArgumentException;
    
    Team getTeam(final String p0) throws IllegalArgumentException;
    
    Set<Team> getTeams();
    
    Team registerNewTeam(final String p0) throws IllegalArgumentException;
    
    @Deprecated
    Set<OfflinePlayer> getPlayers();
    
    Set<String> getEntries();
    
    void clearSlot(final DisplaySlot p0) throws IllegalArgumentException;
}
