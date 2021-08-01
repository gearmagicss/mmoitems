// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.stations;

import net.Indyuce.mmoitems.api.crafting.CraftingStation;
import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import net.Indyuce.mmoitems.gui.CraftingStationView;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class OpenCommandTreeNode extends CommandTreeNode
{
    public OpenCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "open");
        this.addParameter(new Parameter("<station>", (p0, list) -> MMOItems.plugin.getCrafting().getStations().forEach(craftingStation -> list.add(craftingStation.getId()))));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!MMOItems.plugin.getCrafting().hasStation(array[2])) {
            commandSender.sendMessage(ChatColor.RED + "There is no station called " + array[2] + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (array.length > 3) ? Bukkit.getPlayer(array[3]) : ((commandSender instanceof Player) ? commandSender : null);
        if (player == null) {
            commandSender.sendMessage(ChatColor.RED + "Please specify a valid player.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        new CraftingStationView(player, MMOItems.plugin.getCrafting().getStation(array[2]), 1).open();
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
