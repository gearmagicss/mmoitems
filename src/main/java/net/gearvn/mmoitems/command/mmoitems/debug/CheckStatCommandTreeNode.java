// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class CheckStatCommandTreeNode extends CommandTreeNode
{
    public CheckStatCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "checkstat");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final ItemStat value = MMOItems.plugin.getStats().get(array[2].toUpperCase().replace("-", "_"));
        if (value == null) {
            commandSender.sendMessage(ChatColor.RED + "Couldn't find the stat called " + array[1].toUpperCase().replace("-", "_") + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        ((Player)commandSender).sendMessage("Found stat with ID " + value.getId() + " = " + PlayerData.get((OfflinePlayer)commandSender).getStats().getStat(value));
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
