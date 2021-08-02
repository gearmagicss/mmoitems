// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

public class CommandException extends RuntimeException
{
    public CommandException() {
    }
    
    public CommandException(final String msg) {
        super(msg);
    }
    
    public CommandException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
