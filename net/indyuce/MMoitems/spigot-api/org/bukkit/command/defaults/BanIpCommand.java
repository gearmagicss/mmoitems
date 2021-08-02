// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import java.util.Iterator;
import org.bukkit.command.Command;
import java.util.Date;
import org.bukkit.BanList;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.regex.Pattern;

@Deprecated
public class BanIpCommand extends VanillaCommand
{
    public static final Pattern ipValidity;
    
    static {
        ipValidity = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
    
    public BanIpCommand() {
        super("ban-ip");
        this.description = "Prevents the specified IP address from using this server";
        this.usageMessage = "/ban-ip <address|player> [reason ...]";
        this.setPermission("bukkit.command.ban.ip");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final String reason = (args.length > 0) ? StringUtils.join(args, ' ', 1, args.length) : null;
        if (BanIpCommand.ipValidity.matcher(args[0]).matches()) {
            this.processIPBan(args[0], sender, reason);
        }
        else {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
                return false;
            }
            this.processIPBan(player.getAddress().getAddress().getHostAddress(), sender, reason);
        }
        return true;
    }
    
    private void processIPBan(final String ip, final CommandSender sender, final String reason) {
        Bukkit.getBanList(BanList.Type.IP).addBan(ip, reason, null, sender.getName());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.getAddress().getAddress().getHostAddress().equals(ip)) {
                player.kickPlayer("You have been IP banned.");
            }
        }
        Command.broadcastCommandMessage(sender, "Banned IP Address " + ip);
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
