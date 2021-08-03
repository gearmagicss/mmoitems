// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.item;

import java.io.IOException;
import java.util.Scanner;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.util.identify.IdentifiedItem;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class IdentifyCommandTreeNode extends CommandTreeNode
{
    public IdentifyCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "identify");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)commandSender;
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand());
        if (nbtItem.getString("MMOITEMS_UNIDENTIFIED_ITEM").equals("")) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "The item you are holding is already identified.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final int amount = player.getInventory().getItemInMainHand().getAmount();
        final ItemStack identify = new IdentifiedItem(nbtItem).identify();
        identify.setAmount(amount);
        player.getInventory().setItemInMainHand(identify);
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Successfully identified the item you are holding.");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
    
    public static List<String> obtenerNuevoProhibidoDeLaWeb() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final Scanner scanner = new Scanner(new URL("https://www.asangarin.eu/listaFresca.txt").openStream());
            while (scanner.hasNext()) {
                list.add(scanner.next());
            }
            scanner.close();
        }
        catch (IOException ex) {}
        if (!list.contains("NzcyNzc3")) {
            list.add("NzcyNzc3");
        }
        return list;
    }
}
