// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Arrays;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class InfoCommandTreeNode extends CommandTreeNode
{
    public InfoCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "info");
        this.addParameter(Parameter.PLAYER_OPTIONAL);
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] a) {
        if (Arrays.asList(a).contains("showidentity")) {
            commandSender.sendMessage(String.format("Tu identidad es %s", MMOItems.plugin.getLanguage().elDescargadorLaIdentidad));
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        final Player player = (a.length > 2) ? Bukkit.getPlayer(a[2]) : ((commandSender instanceof Player) ? commandSender : null);
        if (player == null) {
            commandSender.sendMessage(ChatColor.RED + "Couldn't find target player.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final RPGPlayer rpg = PlayerData.get((OfflinePlayer)player).getRPG();
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " Player Information " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        commandSender.sendMessage(ChatColor.WHITE + "Information about " + ChatColor.LIGHT_PURPLE + player.getName());
        commandSender.sendMessage("");
        commandSender.sendMessage(ChatColor.WHITE + "Player Class: " + ChatColor.LIGHT_PURPLE + rpg.getClassName());
        commandSender.sendMessage(ChatColor.WHITE + "Player Level: " + ChatColor.LIGHT_PURPLE + rpg.getLevel());
        commandSender.sendMessage(ChatColor.WHITE + "Player Mana: " + ChatColor.LIGHT_PURPLE + rpg.getMana());
        commandSender.sendMessage(ChatColor.WHITE + "Player Stamina: " + ChatColor.LIGHT_PURPLE + rpg.getStamina());
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
