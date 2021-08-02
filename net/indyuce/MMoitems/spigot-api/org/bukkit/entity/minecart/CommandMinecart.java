// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity.minecart;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;

public interface CommandMinecart extends Minecart, CommandSender
{
    String getCommand();
    
    void setCommand(final String p0);
    
    void setName(final String p0);
}
