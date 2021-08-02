// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@Deprecated
public class SetIdleTimeoutCommand extends VanillaCommand
{
    public SetIdleTimeoutCommand() {
        super("setidletimeout");
        this.description = "Sets the server's idle timeout";
        this.usageMessage = "/setidletimeout <Minutes until kick>";
        this.setPermission("bukkit.command.setidletimeout");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 1) {
            int minutes;
            try {
                minutes = this.getInteger(sender, args[0], 0, Integer.MAX_VALUE, true);
            }
            catch (NumberFormatException ex) {
                sender.sendMessage(ex.getMessage());
                return true;
            }
            Bukkit.getServer().setIdleTimeout(minutes);
            Command.broadcastCommandMessage(sender, "Successfully set the idle timeout to " + minutes + " minutes.");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
        return false;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
