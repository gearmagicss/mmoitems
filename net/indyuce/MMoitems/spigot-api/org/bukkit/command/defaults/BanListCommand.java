// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.BanEntry;
import org.bukkit.ChatColor;
import org.bukkit.BanList;
import org.bukkit.command.CommandSender;
import com.google.common.collect.ImmutableList;
import java.util.List;

@Deprecated
public class BanListCommand extends VanillaCommand
{
    private static final List<String> BANLIST_TYPES;
    
    static {
        BANLIST_TYPES = ImmutableList.of("ips", "players");
    }
    
    public BanListCommand() {
        super("banlist");
        this.description = "View all players banned from this server";
        this.usageMessage = "/banlist [ips|players]";
        this.setPermission("bukkit.command.ban.list");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        BanList.Type banType = BanList.Type.NAME;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("ips")) {
                banType = BanList.Type.IP;
            }
            else if (!args[0].equalsIgnoreCase("players")) {
                sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
                return false;
            }
        }
        final StringBuilder message = new StringBuilder();
        final BanEntry[] banlist = Bukkit.getBanList(banType).getBanEntries().toArray(new BanEntry[0]);
        for (int x = 0; x < banlist.length; ++x) {
            if (x != 0) {
                if (x == banlist.length - 1) {
                    message.append(" and ");
                }
                else {
                    message.append(", ");
                }
            }
            message.append(banlist[x].getTarget());
        }
        sender.sendMessage("There are " + banlist.length + " total banned players:");
        sender.sendMessage(message.toString());
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], BanListCommand.BANLIST_TYPES, new ArrayList<String>(BanListCommand.BANLIST_TYPES.size()));
        }
        return (List<String>)ImmutableList.of();
    }
}
