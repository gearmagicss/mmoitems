// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import org.bukkit.configuration.file.FileConfiguration;
import java.util.Iterator;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class AllItemsCommandTreeNode extends CommandTreeNode
{
    public AllItemsCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "allitems");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        commandSender.sendMessage(ChatColor.GREEN + "List of all MMOItems:");
        final Iterator<Type> iterator = MMOItems.plugin.getTypes().getAll().iterator();
        while (iterator.hasNext()) {
            final FileConfiguration config = iterator.next().getConfigFile().getConfig();
            for (final String s : config.getKeys(false)) {
                commandSender.sendMessage("* " + ChatColor.GREEN + s + (config.getConfigurationSection(s).contains("name") ? (" " + ChatColor.WHITE + "(" + MythicLib.plugin.parseColors(config.getString(s + ".name")) + ChatColor.WHITE + ")") : ""));
            }
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
