// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.BanList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Deprecated
public class BanCommand extends VanillaCommand
{
    public BanCommand() {
        super("ban");
        this.description = "Prevents the specified player from using this server";
        this.usageMessage = "/ban <player> [reason ...]";
        this.setPermission("bukkit.command.ban.player");
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
        final String reason = (args.length > 0) ? StringUtils.join(args, ' ', 1, args.length) : null;
        Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
        final Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            player.kickPlayer("Banned by admin.");
        }
        Command.broadcastCommandMessage(sender, "Banned player " + args[0]);
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length >= 1) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
