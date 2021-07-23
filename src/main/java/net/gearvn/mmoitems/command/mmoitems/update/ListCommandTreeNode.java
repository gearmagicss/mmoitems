// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.update;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.PluginUpdate;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class ListCommandTreeNode extends CommandTreeNode
{
    public ListCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "list");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Available Config Updates");
        final Iterator<PluginUpdate> iterator = MMOItems.plugin.getUpdates().getAll().iterator();
        while (iterator.hasNext()) {
            commandSender.sendMessage(ChatColor.DARK_GRAY + "- Update " + iterator.next().getId());
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
