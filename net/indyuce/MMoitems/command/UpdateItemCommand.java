// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class UpdateItemCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return true;
        }
        if (!commandSender.hasPermission("mmoitems.update")) {
            return true;
        }
        final Player player = (Player)commandSender;
        if (array.length < 1 || !player.hasPermission("mmoitems.admin")) {}
        return true;
    }
}
