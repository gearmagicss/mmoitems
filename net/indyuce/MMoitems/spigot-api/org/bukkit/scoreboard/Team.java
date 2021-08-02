// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;
import java.util.Set;

public interface Team
{
    String getName() throws IllegalStateException;
    
    String getDisplayName() throws IllegalStateException;
    
    void setDisplayName(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    String getPrefix() throws IllegalStateException;
    
    void setPrefix(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    String getSuffix() throws IllegalStateException;
    
    void setSuffix(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    boolean allowFriendlyFire() throws IllegalStateException;
    
    void setAllowFriendlyFire(final boolean p0) throws IllegalStateException;
    
    boolean canSeeFriendlyInvisibles() throws IllegalStateException;
    
    void setCanSeeFriendlyInvisibles(final boolean p0) throws IllegalStateException;
    
    @Deprecated
    NameTagVisibility getNameTagVisibility() throws IllegalArgumentException;
    
    @Deprecated
    void setNameTagVisibility(final NameTagVisibility p0) throws IllegalArgumentException;
    
    @Deprecated
    Set<OfflinePlayer> getPlayers() throws IllegalStateException;
    
    Set<String> getEntries() throws IllegalStateException;
    
    int getSize() throws IllegalStateException;
    
    Scoreboard getScoreboard();
    
    @Deprecated
    void addPlayer(final OfflinePlayer p0) throws IllegalStateException, IllegalArgumentException;
    
    void addEntry(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    @Deprecated
    boolean removePlayer(final OfflinePlayer p0) throws IllegalStateException, IllegalArgumentException;
    
    boolean removeEntry(final String p0) throws IllegalStateException, IllegalArgumentException;
    
    void unregister() throws IllegalStateException;
    
    @Deprecated
    boolean hasPlayer(final OfflinePlayer p0) throws IllegalArgumentException, IllegalStateException;
    
    boolean hasEntry(final String p0) throws IllegalArgumentException, IllegalStateException;
    
    OptionStatus getOption(final Option p0) throws IllegalStateException;
    
    void setOption(final Option p0, final OptionStatus p1) throws IllegalStateException;
    
    public enum Option
    {
        NAME_TAG_VISIBILITY("NAME_TAG_VISIBILITY", 0), 
        DEATH_MESSAGE_VISIBILITY("DEATH_MESSAGE_VISIBILITY", 1), 
        COLLISION_RULE("COLLISION_RULE", 2);
        
        private Option(final String name, final int ordinal) {
        }
    }
    
    public enum OptionStatus
    {
        ALWAYS("ALWAYS", 0), 
        NEVER("NEVER", 1), 
        FOR_OTHER_TEAMS("FOR_OTHER_TEAMS", 2), 
        FOR_OWN_TEAM("FOR_OWN_TEAM", 3);
        
        private OptionStatus(final String name, final int ordinal) {
        }
    }
}
