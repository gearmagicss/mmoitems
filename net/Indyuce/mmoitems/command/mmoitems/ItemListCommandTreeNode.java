// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class ItemListCommandTreeNode extends CommandTreeNode
{
    public ItemListCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "itemlist");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 2) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[1].toUpperCase().replace("-", "_"));
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Type " + ChatColor.GREEN + "/mi list type " + ChatColor.GRAY + "to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type value = Type.get(array[1]);
        commandSender.sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        commandSender.sendMessage(ChatColor.GREEN + "List of all items in " + value.getId().toLowerCase() + ".yml:");
        final FileConfiguration config = value.getConfigFile().getConfig();
        if (commandSender instanceof Player) {
            for (final String s : config.getKeys(false)) {
                final String str = config.getConfigurationSection(s).contains("name") ? (" " + ChatColor.WHITE + "(" + MythicLib.plugin.parseColors(config.getString(s + ".name")) + ChatColor.WHITE + ")") : "";
                MythicLib.plugin.getVersion().getWrapper().sendJson((Player)commandSender, "{\"text\":\"* " + ChatColor.GREEN + s + str + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/mi edit " + value.getId() + " " + s + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"Click to edit " + (str.equals("") ? s : MythicLib.plugin.parseColors(config.getString(s + ".name"))) + ChatColor.WHITE + ".\",\"color\":\"white\"}}}");
            }
        }
        else {
            for (final String s2 : config.getKeys(false)) {
                commandSender.sendMessage("* " + ChatColor.GREEN + s2 + (config.getConfigurationSection(s2).contains("name") ? (" " + ChatColor.WHITE + "(" + MythicLib.plugin.parseColors(config.getString(s2 + ".name")) + ChatColor.WHITE + ")") : ""));
            }
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
