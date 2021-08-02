// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.plugin.Plugin;

public final class PluginCommand extends Command implements PluginIdentifiableCommand
{
    private final Plugin owningPlugin;
    private CommandExecutor executor;
    private TabCompleter completer;
    
    protected PluginCommand(final String name, final Plugin owner) {
        super(name);
        this.executor = owner;
        this.owningPlugin = owner;
        this.usageMessage = "";
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        boolean success = false;
        if (!this.owningPlugin.isEnabled()) {
            return false;
        }
        if (!this.testPermission(sender)) {
            return true;
        }
        try {
            success = this.executor.onCommand(sender, this, commandLabel, args);
        }
        catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.owningPlugin.getDescription().getFullName(), ex);
        }
        if (!success && this.usageMessage.length() > 0) {
            String[] split;
            for (int length = (split = this.usageMessage.replace("<command>", commandLabel).split("\n")).length, i = 0; i < length; ++i) {
                final String line = split[i];
                sender.sendMessage(line);
            }
        }
        return success;
    }
    
    public void setExecutor(final CommandExecutor executor) {
        this.executor = ((executor == null) ? this.owningPlugin : executor);
    }
    
    public CommandExecutor getExecutor() {
        return this.executor;
    }
    
    public void setTabCompleter(final TabCompleter completer) {
        this.completer = completer;
    }
    
    public TabCompleter getTabCompleter() {
        return this.completer;
    }
    
    @Override
    public Plugin getPlugin() {
        return this.owningPlugin;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws CommandException, IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        List<String> completions = null;
        try {
            if (this.completer != null) {
                completions = this.completer.onTabComplete(sender, this, alias, args);
            }
            if (completions == null && this.executor instanceof TabCompleter) {
                completions = ((TabCompleter)this.executor).onTabComplete(sender, this, alias, args);
            }
        }
        catch (Throwable ex) {
            final StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (final String arg : args) {
                message.append(arg).append(' ');
            }
            message.deleteCharAt(message.length() - 1).append("' in plugin ").append(this.owningPlugin.getDescription().getFullName());
            throw new CommandException(message.toString(), ex);
        }
        if (completions == null) {
            return super.tabComplete(sender, alias, args);
        }
        return completions;
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(", ").append(this.owningPlugin.getDescription().getFullName()).append(')');
        return stringBuilder.toString();
    }
}
