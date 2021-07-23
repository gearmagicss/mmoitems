// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.item;

import java.util.Iterator;
import java.util.List;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class DeconstructCommandTreeNode extends CommandTreeNode
{
    public DeconstructCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "deconstruct");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)commandSender;
        final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemInMainHand);
        final String string = nbtItem.getString("MMOITEMS_TIER");
        if (string.equals("") || !nbtItem.getBoolean("MMOITEMS_CAN_DECONSTRUCT")) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "The item you are holding can't be deconstructed.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final List<ItemStack> deconstructedLoot = MMOItems.plugin.getTiers().get(string).getDeconstructedLoot(PlayerData.get((OfflinePlayer)player));
        if (deconstructedLoot.isEmpty()) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "There we no items to be yielded from the deconstruction.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
        player.getInventory().setItemInMainHand(itemInMainHand);
        final Iterator<ItemStack> iterator = player.getInventory().addItem((ItemStack[])deconstructedLoot.toArray(new ItemStack[0])).values().iterator();
        while (iterator.hasNext()) {
            player.getWorld().dropItem(player.getLocation(), (ItemStack)iterator.next());
        }
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Successfully deconstructed the item you are holding.");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
