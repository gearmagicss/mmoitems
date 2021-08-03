// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.item;

import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class UnidentifyCommandTreeNode extends CommandTreeNode
{
    public UnidentifyCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "unidentify");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)commandSender;
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand());
        if (nbtItem.getType() == null) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Couldn't unidentify the item you are holding.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        if (nbtItem.getBoolean("MMOITEMS_UNIDENTIFIED")) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "The item you are holding is already unidentified.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        player.getInventory().setItemInMainHand(Type.get(nbtItem.getType()).getUnidentifiedTemplate().newBuilder(nbtItem).build());
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Successfully unidentified the item you are holding.");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
