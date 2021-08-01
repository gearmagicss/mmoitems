// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.list;

import net.Indyuce.mmoitems.stat.StaffSpiritStat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class StaffSpiritCommandTreeNode extends CommandTreeNode
{
    public StaffSpiritCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "spirit");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " Staff Spirits " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        for (final StaffSpiritStat.StaffSpirit staffSpirit : StaffSpiritStat.StaffSpirit.values()) {
            commandSender.sendMessage("* " + ChatColor.LIGHT_PURPLE + staffSpirit.getName() + (staffSpirit.hasLore() ? "" : (" " + ChatColor.WHITE + ">> " + ChatColor.GRAY + "" + ChatColor.ITALIC + staffSpirit.getLore())));
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
