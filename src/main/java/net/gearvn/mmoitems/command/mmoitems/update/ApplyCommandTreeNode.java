// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.update;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import net.Indyuce.mmoitems.api.PluginUpdate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class ApplyCommandTreeNode extends CommandTreeNode
{
    public ApplyCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "apply");
        this.addParameter(new Parameter("<id>", (p0, list) -> MMOItems.plugin.getUpdates().getAll().forEach(pluginUpdate -> list.add("" + pluginUpdate.getId()))));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        int int1;
        try {
            int1 = Integer.parseInt(array[2]);
        }
        catch (NumberFormatException ex) {
            commandSender.sendMessage(ChatColor.RED + "Please specify a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        if (!MMOItems.plugin.getUpdates().has(int1)) {
            commandSender.sendMessage(ChatColor.RED + "Could not find any config update with ID " + int1);
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final PluginUpdate value = MMOItems.plugin.getUpdates().get(int1);
        commandSender.sendMessage(ChatColor.YELLOW + "Applying config update " + int1 + "...");
        value.apply(commandSender);
        commandSender.sendMessage(ChatColor.YELLOW + "Config update " + int1 + " was successfully applied. Check the console for potential update error logs.");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
