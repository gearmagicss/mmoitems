// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import java.util.Set;
import java.util.Date;

public interface BanList
{
    BanEntry getBanEntry(final String p0);
    
    BanEntry addBan(final String p0, final String p1, final Date p2, final String p3);
    
    Set<BanEntry> getBanEntries();
    
    boolean isBanned(final String p0);
    
    void pardon(final String p0);
    
    public enum Type
    {
        NAME("NAME", 0), 
        IP("IP", 1);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
