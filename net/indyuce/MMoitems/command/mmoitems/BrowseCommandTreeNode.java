// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.gui.ItemBrowser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class BrowseCommandTreeNode extends CommandTreeNode
{
    public BrowseCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "browse");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        if (array.length < 2) {
            new ItemBrowser((Player)commandSender).open();
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Please specify a valid item type.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        new ItemBrowser((Player)commandSender, Type.get(array[1])).open();
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
