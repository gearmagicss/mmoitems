// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Deprecated
public class SayCommand extends VanillaCommand
{
    public SayCommand() {
        super("say");
        this.description = "Broadcasts the given message as the sender";
        this.usageMessage = "/say <message ...>";
        this.setPermission("bukkit.command.say");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final StringBuilder message = new StringBuilder();
        message.append(ChatColor.LIGHT_PURPLE).append("[");
        if (sender instanceof ConsoleCommandSender) {
            message.append("Server");
        }
        else if (sender instanceof Player) {
            message.append(((Player)sender).getDisplayName());
        }
        else {
            message.append(sender.getName());
        }
        message.append(ChatColor.LIGHT_PURPLE).append("] ");
        if (args.length > 0) {
            message.append(args[0]);
            for (int i = 1; i < args.length; ++i) {
                message.append(" ").append(args[i]);
            }
        }
        Bukkit.broadcastMessage(message.toString());
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        if (args.length >= 1) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
