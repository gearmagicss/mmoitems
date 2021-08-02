// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

public interface ProxiedCommandSender extends CommandSender
{
    CommandSender getCaller();
    
    CommandSender getCallee();
}
