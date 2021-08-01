// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import java.util.Iterator;
import org.bukkit.attribute.AttributeInstance;
import java.text.DecimalFormat;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import java.util.Arrays;
import org.bukkit.attribute.Attribute;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class CheckAttributeCommandTreeNode extends CommandTreeNode
{
    public CheckAttributeCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "checkattribute");
        this.addParameter(new Parameter("<attribute>", (p0, list) -> Arrays.asList(Attribute.values()).forEach(attribute -> list.add(attribute.name()))));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for players.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        final Player player = (Player)commandSender;
        try {
            final AttributeInstance attribute = player.getAttribute(Attribute.valueOf(array[2].toUpperCase().replace("-", "_")));
            commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
            commandSender.sendMessage(ChatColor.AQUA + "Default Value = " + ChatColor.RESET + attribute.getDefaultValue());
            commandSender.sendMessage(ChatColor.AQUA + "Base Value = " + ChatColor.RESET + attribute.getBaseValue());
            commandSender.sendMessage(ChatColor.AQUA + "Value = " + ChatColor.RESET + attribute.getValue());
            for (final AttributeModifier attributeModifier : attribute.getModifiers()) {
                commandSender.sendMessage(attributeModifier.getName() + " " + new DecimalFormat("0.####").format(attributeModifier.getAmount()) + " " + attributeModifier.getOperation() + " " + attributeModifier.getSlot());
            }
        }
        catch (IllegalArgumentException ex) {
            player.sendMessage("Couldn't find attribute.");
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
