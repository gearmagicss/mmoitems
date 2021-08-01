// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.list;

import net.Indyuce.mmoitems.stat.LuteAttackEffectStat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class LuteAttackCommandTreeNode extends CommandTreeNode
{
    public LuteAttackCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "lute");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " Lute Attack Effects " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        final LuteAttackEffectStat.LuteAttackEffect[] values = LuteAttackEffectStat.LuteAttackEffect.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            commandSender.sendMessage("* " + ChatColor.LIGHT_PURPLE + values[i].getName());
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
