// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import java.util.Iterator;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class CheckTagsCommandTreeNode extends CommandTreeNode
{
    public CheckTagsCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "checktags");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)commandSender;
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
        final Iterator iterator = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand()).getTags().iterator();
        while (iterator.hasNext()) {
            player.sendMessage("- " + iterator.next());
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
