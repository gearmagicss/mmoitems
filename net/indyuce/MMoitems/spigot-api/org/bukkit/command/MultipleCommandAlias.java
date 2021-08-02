// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

public class MultipleCommandAlias extends Command
{
    private Command[] commands;
    
    public MultipleCommandAlias(final String name, final Command[] commands) {
        super(name);
        this.commands = commands;
    }
    
    public Command[] getCommands() {
        return this.commands;
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        boolean result = false;
        Command[] commands;
        for (int length = (commands = this.commands).length, i = 0; i < length; ++i) {
            final Command command = commands[i];
            result |= command.execute(sender, commandLabel, args);
        }
        return result;
    }
}
