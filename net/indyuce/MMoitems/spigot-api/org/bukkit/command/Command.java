// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

import java.util.Set;
import org.bukkit.permissions.Permissible;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import org.bukkit.util.StringUtil;
import org.bukkit.entity.Player;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.Collection;
import java.util.ArrayList;
import org.spigotmc.CustomTimingsHandler;
import java.util.List;

public abstract class Command
{
    private String name;
    private String nextLabel;
    private String label;
    private List<String> aliases;
    private List<String> activeAliases;
    private CommandMap commandMap;
    protected String description;
    protected String usageMessage;
    private String permission;
    private String permissionMessage;
    public CustomTimingsHandler timings;
    
    protected Command(final String name) {
        this(name, "", "/" + name, new ArrayList<String>());
    }
    
    protected Command(final String name, final String description, final String usageMessage, final List<String> aliases) {
        this.commandMap = null;
        this.description = "";
        this.name = name;
        this.nextLabel = name;
        this.label = name;
        this.description = description;
        this.usageMessage = usageMessage;
        this.aliases = aliases;
        this.activeAliases = new ArrayList<String>(aliases);
        this.timings = new CustomTimingsHandler("** Command: " + name);
    }
    
    public abstract boolean execute(final CommandSender p0, final String p1, final String[] p2);
    
    @Deprecated
    public List<String> tabComplete(final CommandSender sender, final String[] args) {
        return null;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 0) {
            return (List<String>)ImmutableList.of();
        }
        final String lastWord = args[args.length - 1];
        final Player senderPlayer = (sender instanceof Player) ? ((Player)sender) : null;
        final ArrayList<String> matchedPlayers = new ArrayList<String>();
        for (final Player player : sender.getServer().getOnlinePlayers()) {
            final String name = player.getName();
            if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
                matchedPlayers.add(name);
            }
        }
        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean setName(final String name) {
        if (!this.isRegistered()) {
            this.name = name;
            return true;
        }
        return false;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public void setPermission(final String permission) {
        this.permission = permission;
    }
    
    public boolean testPermission(final CommandSender target) {
        if (this.testPermissionSilent(target)) {
            return true;
        }
        if (this.permissionMessage == null) {
            target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
        }
        else if (this.permissionMessage.length() != 0) {
            String[] split;
            for (int length = (split = this.permissionMessage.replace("<permission>", this.permission).split("\n")).length, i = 0; i < length; ++i) {
                final String line = split[i];
                target.sendMessage(line);
            }
        }
        return false;
    }
    
    public boolean testPermissionSilent(final CommandSender target) {
        if (this.permission == null || this.permission.length() == 0) {
            return true;
        }
        String[] split;
        for (int length = (split = this.permission.split(";")).length, i = 0; i < length; ++i) {
            final String p = split[i];
            if (target.hasPermission(p)) {
                return true;
            }
        }
        return false;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public boolean setLabel(final String name) {
        this.nextLabel = name;
        if (!this.isRegistered()) {
            this.timings = new CustomTimingsHandler("** Command: " + name);
            this.label = name;
            return true;
        }
        return false;
    }
    
    public boolean register(final CommandMap commandMap) {
        if (this.allowChangesFrom(commandMap)) {
            this.commandMap = commandMap;
            return true;
        }
        return false;
    }
    
    public boolean unregister(final CommandMap commandMap) {
        if (this.allowChangesFrom(commandMap)) {
            this.commandMap = null;
            this.activeAliases = new ArrayList<String>(this.aliases);
            this.label = this.nextLabel;
            return true;
        }
        return false;
    }
    
    private boolean allowChangesFrom(final CommandMap commandMap) {
        return this.commandMap == null || this.commandMap == commandMap;
    }
    
    public boolean isRegistered() {
        return this.commandMap != null;
    }
    
    public List<String> getAliases() {
        return this.activeAliases;
    }
    
    public String getPermissionMessage() {
        return this.permissionMessage;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getUsage() {
        return this.usageMessage;
    }
    
    public Command setAliases(final List<String> aliases) {
        this.aliases = aliases;
        if (!this.isRegistered()) {
            this.activeAliases = new ArrayList<String>(aliases);
        }
        return this;
    }
    
    public Command setDescription(final String description) {
        this.description = description;
        return this;
    }
    
    public Command setPermissionMessage(final String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }
    
    public Command setUsage(final String usage) {
        this.usageMessage = usage;
        return this;
    }
    
    public static void broadcastCommandMessage(final CommandSender source, final String message) {
        broadcastCommandMessage(source, message, true);
    }
    
    public static void broadcastCommandMessage(final CommandSender source, final String message, final boolean sendToSource) {
        final String result = String.valueOf(source.getName()) + ": " + message;
        if (source instanceof BlockCommandSender) {
            final BlockCommandSender blockCommandSender = (BlockCommandSender)source;
            if (blockCommandSender.getBlock().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
                Bukkit.getConsoleSender().sendMessage(result);
                return;
            }
        }
        else if (source instanceof CommandMinecart) {
            final CommandMinecart commandMinecart = (CommandMinecart)source;
            if (commandMinecart.getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
                Bukkit.getConsoleSender().sendMessage(result);
                return;
            }
        }
        final Set<Permissible> users = Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin");
        final String colored = new StringBuilder().append(ChatColor.GRAY).append(ChatColor.ITALIC).append("[").append(result).append(ChatColor.GRAY).append(ChatColor.ITALIC).append("]").toString();
        if (sendToSource && !(source instanceof ConsoleCommandSender)) {
            source.sendMessage(message);
        }
        for (final Permissible user : users) {
            if (user instanceof CommandSender) {
                final CommandSender target = (CommandSender)user;
                if (target instanceof ConsoleCommandSender) {
                    target.sendMessage(result);
                }
                else {
                    if (target == source) {
                        continue;
                    }
                    target.sendMessage(colored);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getClass().getName()) + '(' + this.name + ')';
    }
}
