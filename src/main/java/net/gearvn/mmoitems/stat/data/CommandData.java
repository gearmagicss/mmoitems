// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.apache.commons.lang.Validate;

public class CommandData
{
    private final String command;
    private final double delay;
    private final boolean console;
    private final boolean op;
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof CommandData && ((CommandData)o).getDelay() == this.getDelay() && ((CommandData)o).isConsoleCommand() == this.isConsoleCommand() && ((CommandData)o).hasOpPerms() == this.hasOpPerms() && ((CommandData)o).getCommand().equals(this.getCommand());
    }
    
    public CommandData(final String command, final double delay, final boolean console, final boolean op) {
        Validate.notNull((Object)command, "Command cannot be null");
        this.command = command;
        this.delay = delay;
        this.console = console;
        this.op = op;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public double getDelay() {
        return this.delay;
    }
    
    public boolean hasDelay() {
        return this.delay > 0.0;
    }
    
    public boolean isConsoleCommand() {
        return this.console;
    }
    
    public boolean hasOpPerms() {
        return this.op;
    }
}
