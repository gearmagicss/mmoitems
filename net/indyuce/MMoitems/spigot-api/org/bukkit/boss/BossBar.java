// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.boss;

import java.util.List;
import org.bukkit.entity.Player;

public interface BossBar
{
    String getTitle();
    
    void setTitle(final String p0);
    
    BarColor getColor();
    
    void setColor(final BarColor p0);
    
    BarStyle getStyle();
    
    void setStyle(final BarStyle p0);
    
    void removeFlag(final BarFlag p0);
    
    void addFlag(final BarFlag p0);
    
    boolean hasFlag(final BarFlag p0);
    
    void setProgress(final double p0);
    
    double getProgress();
    
    void addPlayer(final Player p0);
    
    void removePlayer(final Player p0);
    
    void removeAll();
    
    List<Player> getPlayers();
    
    void setVisible(final boolean p0);
    
    boolean isVisible();
    
    @Deprecated
    void show();
    
    @Deprecated
    void hide();
}
