// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class SetTagCommandTreeNode extends CommandTreeNode
{
    public SetTagCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "settag");
        this.addParameter(new Parameter("<path>", (p0, list) -> list.add("TagPath")));
        this.addParameter(new Parameter("<value>", (p0, list2) -> list2.add("TagValue")));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 4) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        try {
            final Player player = (Player)commandSender;
            player.getInventory().setItemInMainHand(MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand()).addTag(new ItemTag[] { new ItemTag(array[2].toUpperCase().replace("-", "_"), (Object)array[3].replace("%%", " ")) }).toItem());
            player.sendMessage("Successfully set tag.");
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        catch (Exception ex) {
            commandSender.sendMessage("Couldn't set tag.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
    }
}
