// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.Arrays;

public class ReloadCommand extends BukkitCommand
{
    public ReloadCommand(final String name) {
        super(name);
        this.description = "Reloads the server configuration and plugins";
        this.usageMessage = "/reload";
        this.setPermission("bukkit.command.reload");
        this.setAliases(Arrays.asList("rl"));
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        Command.broadcastCommandMessage(sender, ChatColor.RED + "Please note that this command is not supported and may cause issues when using some plugins.");
        Command.broadcastCommandMessage(sender, ChatColor.RED + "If you encounter any issues please use the /stop command to restart your server.");
        Bukkit.reload();
        Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Reload complete.");
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }
}
