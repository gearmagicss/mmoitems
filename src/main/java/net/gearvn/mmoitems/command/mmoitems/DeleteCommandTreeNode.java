// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class DeleteCommandTreeNode extends CommandTreeNode
{
    public DeleteCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "delete");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(MMOItemsCommandTreeRoot.ID_2);
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[1].toUpperCase().replace("-", "_") + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Type " + ChatColor.GREEN + "/mi list type" + ChatColor.RED + " to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type value = Type.get(array[1]);
        final String replace = array[2].toUpperCase().replace("-", "_");
        if (!MMOItems.plugin.getTemplates().hasTemplate(value, replace)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item called " + replace + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        MMOItems.plugin.getTemplates().deleteTemplate(value, replace);
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GREEN + "You successfully deleted " + replace + ".");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
