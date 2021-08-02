// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;
import org.bukkit.util.StringUtil;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.util.Java15Compat;
import org.bukkit.command.defaults.VanillaCommand;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.command.defaults.TimingsCommand;
import org.bukkit.command.defaults.PluginsCommand;
import org.bukkit.command.defaults.ReloadCommand;
import org.bukkit.command.defaults.VersionCommand;
import java.util.HashMap;
import org.bukkit.Server;
import java.util.Map;
import java.util.regex.Pattern;

public class SimpleCommandMap implements CommandMap
{
    private static final Pattern PATTERN_ON_SPACE;
    protected final Map<String, Command> knownCommands;
    private final Server server;
    
    static {
        PATTERN_ON_SPACE = Pattern.compile(" ", 16);
    }
    
    public SimpleCommandMap(final Server server) {
        this.knownCommands = new HashMap<String, Command>();
        this.server = server;
        this.setDefaultCommands();
    }
    
    private void setDefaultCommands() {
        this.register("bukkit", new VersionCommand("version"));
        this.register("bukkit", new ReloadCommand("reload"));
        this.register("bukkit", new PluginsCommand("plugins"));
        this.register("bukkit", new TimingsCommand("timings"));
    }
    
    public void setFallbackCommands() {
        this.register("bukkit", new HelpCommand());
    }
    
    @Override
    public void registerAll(final String fallbackPrefix, final List<Command> commands) {
        if (commands != null) {
            for (final Command c : commands) {
                this.register(fallbackPrefix, c);
            }
        }
    }
    
    @Override
    public boolean register(final String fallbackPrefix, final Command command) {
        return this.register(command.getName(), fallbackPrefix, command);
    }
    
    @Override
    public boolean register(String label, String fallbackPrefix, final Command command) {
        label = label.toLowerCase().trim();
        fallbackPrefix = fallbackPrefix.toLowerCase().trim();
        final boolean registered = this.register(label, command, false, fallbackPrefix);
        final Iterator<String> iterator = command.getAliases().iterator();
        while (iterator.hasNext()) {
            if (!this.register(iterator.next(), command, true, fallbackPrefix)) {
                iterator.remove();
            }
        }
        if (!registered) {
            command.setLabel(String.valueOf(fallbackPrefix) + ":" + label);
        }
        command.register(this);
        return registered;
    }
    
    private synchronized boolean register(final String label, final Command command, final boolean isAlias, final String fallbackPrefix) {
        this.knownCommands.put(String.valueOf(fallbackPrefix) + ":" + label, command);
        if ((command instanceof VanillaCommand || isAlias) && this.knownCommands.containsKey(label)) {
            return false;
        }
        final boolean registered = true;
        final Command conflict = this.knownCommands.get(label);
        if (conflict != null && conflict.getLabel().equals(label)) {
            return false;
        }
        if (!isAlias) {
            command.setLabel(label);
        }
        this.knownCommands.put(label, command);
        return registered;
    }
    
    @Override
    public boolean dispatch(final CommandSender sender, final String commandLine) throws CommandException {
        final String[] args = SimpleCommandMap.PATTERN_ON_SPACE.split(commandLine);
        if (args.length == 0) {
            return false;
        }
        final String sentCommandLabel = args[0].toLowerCase();
        final Command target = this.getCommand(sentCommandLabel);
        if (target == null) {
            return false;
        }
        try {
            target.timings.startTiming();
            target.execute(sender, sentCommandLabel, Java15Compat.Arrays_copyOfRange(args, 1, args.length));
            target.timings.stopTiming();
        }
        catch (CommandException ex) {
            target.timings.stopTiming();
            throw ex;
        }
        catch (Throwable ex2) {
            target.timings.stopTiming();
            throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex2);
        }
        return true;
    }
    
    @Override
    public synchronized void clearCommands() {
        for (final Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
            entry.getValue().unregister(this);
        }
        this.knownCommands.clear();
        this.setDefaultCommands();
    }
    
    @Override
    public Command getCommand(final String name) {
        final Command target = this.knownCommands.get(name.toLowerCase());
        return target;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String cmdLine) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(cmdLine, "Command line cannot null");
        final int spaceIndex = cmdLine.indexOf(32);
        if (spaceIndex == -1) {
            final ArrayList<String> completions = new ArrayList<String>();
            final Map<String, Command> knownCommands = this.knownCommands;
            final String prefix = (sender instanceof Player) ? "/" : "";
            for (final Map.Entry<String, Command> commandEntry : knownCommands.entrySet()) {
                final Command command = commandEntry.getValue();
                if (!command.testPermissionSilent(sender)) {
                    continue;
                }
                final String name = commandEntry.getKey();
                if (!StringUtil.startsWithIgnoreCase(name, cmdLine)) {
                    continue;
                }
                completions.add(String.valueOf(prefix) + name);
            }
            Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
            return completions;
        }
        final String commandName = cmdLine.substring(0, spaceIndex);
        final Command target = this.getCommand(commandName);
        if (target == null) {
            return null;
        }
        if (!target.testPermissionSilent(sender)) {
            return null;
        }
        final String argLine = cmdLine.substring(spaceIndex + 1, cmdLine.length());
        final String[] args = SimpleCommandMap.PATTERN_ON_SPACE.split(argLine, -1);
        try {
            return target.tabComplete(sender, commandName, args);
        }
        catch (CommandException ex) {
            throw ex;
        }
        catch (Throwable ex2) {
            throw new CommandException("Unhandled exception executing tab-completer for '" + cmdLine + "' in " + target, ex2);
        }
    }
    
    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection((Collection<? extends Command>)this.knownCommands.values());
    }
    
    public void registerServerAliases() {
        final Map<String, String[]> values = this.server.getCommandAliases();
        for (final String alias : values.keySet()) {
            if (alias.contains(":") || alias.contains(" ")) {
                this.server.getLogger().warning("Could not register alias " + alias + " because it contains illegal characters");
            }
            else {
                final String[] commandStrings = values.get(alias);
                final List<String> targets = new ArrayList<String>();
                final StringBuilder bad = new StringBuilder();
                String[] array;
                for (int length = (array = commandStrings).length, i = 0; i < length; ++i) {
                    final String commandString = array[i];
                    final String[] commandArgs = commandString.split(" ");
                    final Command command = this.getCommand(commandArgs[0]);
                    if (command == null) {
                        if (bad.length() > 0) {
                            bad.append(", ");
                        }
                        bad.append(commandString);
                    }
                    else {
                        targets.add(commandString);
                    }
                }
                if (bad.length() > 0) {
                    this.server.getLogger().warning("Could not register alias " + alias + " because it contains commands that do not exist: " + (Object)bad);
                }
                else if (targets.size() > 0) {
                    this.knownCommands.put(alias.toLowerCase(), new FormattedCommandAlias(alias.toLowerCase(), targets.toArray(new String[targets.size()])));
                }
                else {
                    this.knownCommands.remove(alias.toLowerCase());
                }
            }
        }
    }
}
