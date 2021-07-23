// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.list;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class AbilityCommandTreeNode extends CommandTreeNode
{
    public AbilityCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "ability");
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " Abilities " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        commandSender.sendMessage(ChatColor.WHITE + "Here are all the abilities you can bind to items.");
        commandSender.sendMessage(ChatColor.WHITE + "The values inside brackets are " + ChatColor.UNDERLINE + "modifiers" + ChatColor.WHITE + " which allow you to change the ability values (cooldown, damage...)");
        for (final Ability ability : MMOItems.plugin.getAbilities().getAllAbilities()) {
            commandSender.sendMessage("* " + ChatColor.LIGHT_PURPLE + ability.getName() + " " + (ChatColor.WHITE + "(" + (ChatColor.GRAY + String.join(ChatColor.WHITE + ", " + ChatColor.GRAY, ability.getModifiers())) + ChatColor.WHITE + ")"));
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
