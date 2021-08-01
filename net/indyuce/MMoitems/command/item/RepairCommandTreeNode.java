// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.item;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class RepairCommandTreeNode extends CommandTreeNode
{
    public RepairCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "repair");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)commandSender;
        final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (!MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemInMainHand).hasTag("MMOITEMS_DURABILITY")) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "The item you are holding can't be repaired.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final DurabilityItem durabilityItem = new DurabilityItem(player, itemInMainHand);
        player.getInventory().setItemInMainHand(durabilityItem.addDurability(durabilityItem.getMaxDurability()).toItem());
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Successfully repaired the item you are holding.");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
