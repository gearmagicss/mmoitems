// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import net.Indyuce.mmoitems.gui.edition.ItemEdition;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class EditCommandTreeNode extends CommandTreeNode
{
    public EditCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "edit");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(MMOItemsCommandTreeRoot.ID_2);
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[1].toUpperCase().replace("-", "_") + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Type " + ChatColor.GREEN + "/mi list type" + ChatColor.RED + " to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type value = Type.get(array[1]);
        final String replace = array[2].toUpperCase().replace("-", "_");
        if (!MMOItems.plugin.getTemplates().hasTemplate(value, replace)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Could not find a template called '" + replace + "'.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        new ItemEdition((Player)commandSender, MMOItems.plugin.getTemplates().getTemplate(value, replace)).open();
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
