// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.update;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.PluginUpdate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class InfoCommandTreeNode extends CommandTreeNode
{
    public InfoCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "info");
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
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Config Update n" + value.getId());
        if (value.hasDescription()) {
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.DARK_GRAY + "Description:");
            final Iterator<String> iterator = value.getDescription().iterator();
            while (iterator.hasNext()) {
                commandSender.sendMessage(ChatColor.GRAY + "- " + ChatColor.translateAlternateColorCodes('&', (String)iterator.next()));
            }
        }
        commandSender.sendMessage("");
        commandSender.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/mi update apply " + value.getId() + ChatColor.YELLOW + " to apply this config update.");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
