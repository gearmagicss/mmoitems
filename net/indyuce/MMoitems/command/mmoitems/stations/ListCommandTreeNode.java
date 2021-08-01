// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.stations;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;
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
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " Crafting Stations " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        final Iterator<CraftingStation> iterator = MMOItems.plugin.getCrafting().getAll().iterator();
        while (iterator.hasNext()) {
            commandSender.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + iterator.next().getId());
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
