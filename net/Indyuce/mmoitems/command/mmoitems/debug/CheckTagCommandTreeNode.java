// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class CheckTagCommandTreeNode extends CommandTreeNode
{
    public CheckTagCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "checktag");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)commandSender;
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand());
        final String s = nbtItem.hasTag(array[2]) ? array[2] : ("MMOITEMS_" + array[2].toUpperCase().replace("-", "_"));
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
        player.sendMessage(ChatColor.AQUA + "Boolean = " + ChatColor.RESET + nbtItem.getBoolean(s));
        player.sendMessage(ChatColor.AQUA + "Double = " + ChatColor.RESET + nbtItem.getDouble(s));
        player.sendMessage(ChatColor.AQUA + "String = " + ChatColor.RESET + nbtItem.getString(s));
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
