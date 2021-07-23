// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.list;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class TypeCommandTreeNode extends CommandTreeNode
{
    public TypeCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "type");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " Item Types " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + ChatColor.LIGHT_PURPLE + " Item Types " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        for (final Type type : MMOItems.plugin.getTypes().getAll()) {
            commandSender.sendMessage("* " + ChatColor.LIGHT_PURPLE + type.getName() + " (" + type.getId() + ")");
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
