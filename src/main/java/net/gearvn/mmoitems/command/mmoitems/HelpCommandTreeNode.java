// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.command.PluginHelp;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class HelpCommandTreeNode extends CommandTreeNode
{
    public HelpCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "help");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 2) {
            new PluginHelp(commandSender).open(1);
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        try {
            new PluginHelp(commandSender).open(Integer.parseInt(array[1]));
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        catch (NumberFormatException ex) {
            commandSender.sendMessage(ChatColor.RED + array[1] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
    }
}
