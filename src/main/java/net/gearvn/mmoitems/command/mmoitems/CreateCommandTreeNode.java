// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.gui.edition.ItemEdition;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class CreateCommandTreeNode extends CommandTreeNode
{
    public CreateCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "create");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(new Parameter("<id>", (p0, list) -> list.add("ITEM_ID")));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[1].toUpperCase().replace("-", "_") + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Type " + ChatColor.GREEN + "/mi list type" + ChatColor.RED + " to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type value = Type.get(array[1]);
        final String replace = array[2].toUpperCase().replace("-", "_");
        final ConfigFile configFile = value.getConfigFile();
        if (configFile.getConfig().contains(replace)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is already an item called " + replace + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        configFile.getConfig().set(replace + ".base.material", (Object)value.getItem().getType().name());
        configFile.save();
        MMOItems.plugin.getTemplates().requestTemplateUpdate(value, replace);
        if (commandSender instanceof Player) {
            new ItemEdition((Player)commandSender, MMOItems.plugin.getTemplates().getTemplate(value, replace)).open();
        }
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GREEN + "You successfully created " + replace + "!");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
