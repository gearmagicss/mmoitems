// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import java.util.Date;

public interface BanEntry
{
    String getTarget();
    
    Date getCreated();
    
    void setCreated(final Date p0);
    
    String getSource();
    
    void setSource(final String p0);
    
    Date getExpiration();
    
    void setExpiration(final Date p0);
    
    String getReason();
    
    void setReason(final String p0);
    
    void save();
}
