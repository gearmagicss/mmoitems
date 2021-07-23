// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.Indyuce.mmoitems.api.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.util.Collection;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class AbilityCommandTreeNode extends CommandTreeNode
{
    public AbilityCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "ability");
        this.addParameter(new Parameter("<ability>", (p0, list) -> MMOItems.plugin.getAbilities().getAllAbilities().forEach(ability -> list.add(ability.getID()))));
        this.addParameter(Parameter.PLAYER_OPTIONAL);
        for (int i = 0; i < 3; ++i) {
            this.addParameter(new Parameter("<modifier>", (commandTreeExplorer, list2) -> {
                try {
                    list2.addAll(MMOItems.plugin.getAbilities().getAbility(commandTreeExplorer.getArguments()[1].toUpperCase().replace("-", "_")).getModifiers());
                }
                catch (Exception ex) {}
                return;
            }));
            this.addParameter(new Parameter("<value>", (p0, list3) -> list3.add("0")));
        }
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 2) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (array.length < 3 && !(commandSender instanceof Player)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Please specify a player to use this command.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Player player = (Player)((array.length > 2) ? Bukkit.getPlayer(array[2]) : commandSender);
        if (player == null) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Couldn't find player called " + array[2] + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final String replace = array[1].toUpperCase().replace("-", "_");
        if (!MMOItems.plugin.getAbilities().hasAbility(replace)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Couldn't find ability " + replace + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final AbilityData abilityData = new AbilityData(MMOItems.plugin.getAbilities().getAbility(replace), Ability.CastingMode.RIGHT_CLICK);
        for (int i = 3; i < array.length - 1; i += 2) {
            final String str = array[i];
            final String s = array[i + 1];
            try {
                abilityData.setModifier(str, Double.parseDouble(s));
            }
            catch (Exception ex) {
                commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Wrong format: {" + str + " " + s + "}");
                return CommandTreeNode.CommandResult.FAILURE;
            }
        }
        PlayerData.get((OfflinePlayer)player).cast(abilityData);
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
