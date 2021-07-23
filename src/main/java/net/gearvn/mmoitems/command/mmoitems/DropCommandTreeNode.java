// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.droptable.item.MMOItemDropItem;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class DropCommandTreeNode extends CommandTreeNode
{
    public DropCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "drop");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(MMOItemsCommandTreeRoot.ID_2);
        this.addParameter(new Parameter("<world>", (p0, list) -> Bukkit.getWorlds().forEach(world -> list.add(world.getName()))));
        this.addParameter(new Parameter("<x>", (p0, list2) -> list2.add("<x>")));
        this.addParameter(new Parameter("<y>", (p0, list3) -> list3.add("<y>")));
        this.addParameter(new Parameter("<z>", (p0, list4) -> list4.add("<z>")));
        this.addParameter(new Parameter("<drop-chance>", (p0, list5) -> list5.add("<drop-chance>")));
        this.addParameter(new Parameter("<min-max>", (p0, list6) -> list6.add("1-3")));
        this.addParameter(new Parameter("<unidentify-chance>", (p0, list7) -> list7.add("<unidentify-chance>")));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length != 10) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[1].toUpperCase().replace("-", "_") + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Type " + ChatColor.GREEN + "/mi list type " + ChatColor.RED + "to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type value = Type.get(array[1].toUpperCase());
        final String replace = array[2].toUpperCase().replace("-", "_");
        if (!value.getConfigFile().getConfig().contains(replace)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item called " + replace + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final World world = Bukkit.getWorld(array[3]);
        if (world == null) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Couldn't find the world called " + array[3] + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        double double1;
        try {
            double1 = Double.parseDouble(array[4]);
        }
        catch (Exception ex) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + array[4] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        double double2;
        try {
            double2 = Double.parseDouble(array[5]);
        }
        catch (Exception ex2) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + array[5] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        double double3;
        try {
            double3 = Double.parseDouble(array[6]);
        }
        catch (Exception ex3) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + array[6] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        double double4;
        try {
            double4 = Double.parseDouble(array[7]);
        }
        catch (Exception ex4) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + array[7] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        double double5;
        try {
            double5 = Double.parseDouble(array[9]);
        }
        catch (Exception ex5) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + array[9] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final String[] split = array[8].split("-");
        if (split.length != 2) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "The drop quantity format is incorrect.");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Format: [min]-[max]");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        int int1;
        try {
            int1 = Integer.parseInt(split[0]);
        }
        catch (Exception ex6) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + split[0] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        int int2;
        try {
            int2 = Integer.parseInt(split[1]);
        }
        catch (Exception ex7) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + split[1] + " is not a valid number.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final ItemStack item = new MMOItemDropItem(value, replace, double4 / 100.0, double5 / 100.0, int1, int2).getItem(null);
        if (item == null || item.getType() == Material.AIR) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "An error occured while attempting to generate the item called " + replace + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "See console for more information!");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        world.dropItem(new Location(world, double1, double2, double3), item);
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
