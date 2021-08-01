// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.revid;

import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.item.ItemReference;
import java.util.Collection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import java.util.function.Function;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class RevIDActionCommandTreeNode extends CommandTreeNode
{
    private final String cmdType;
    private final Function<Integer, Integer> modifier;
    
    public RevIDActionCommandTreeNode(final CommandTreeNode commandTreeNode, final String cmdType, final Function<Integer, Integer> modifier) {
        super(commandTreeNode, cmdType);
        this.cmdType = cmdType;
        this.modifier = modifier;
        this.addParameter(RevisionIDCommandTreeNode.TYPE_OR_ALL);
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!Type.isValid(array[2]) && !array[2].equalsIgnoreCase("all")) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[2].toUpperCase().replace("-", "_") + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Type " + ChatColor.GREEN + "/mi list type" + ChatColor.RED + " to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type type = array[2].equalsIgnoreCase("all") ? null : Type.get(array[2]);
        final ArrayList list = new ArrayList<MMOItemTemplate>((type == null) ? MMOItems.plugin.getTemplates().collectTemplates() : MMOItems.plugin.getTemplates().getTemplates(type));
        int i = 0;
        for (final MMOItemTemplate mmoItemTemplate : list) {
            final ConfigFile configFile = mmoItemTemplate.getType().getConfigFile();
            if (!configFile.getConfig().isConfigurationSection(mmoItemTemplate.getId() + ".base")) {
                ++i;
            }
            else {
                configFile.getConfig().getConfigurationSection(mmoItemTemplate.getId() + ".base").set("revision-id", (Object)this.modifier.apply(mmoItemTemplate.getRevisionId()));
                configFile.registerTemplateEdition(mmoItemTemplate);
            }
        }
        if (i > 0) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "Couldn't find ConfigurationSection for " + i + " of the specified items.");
        }
        else {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GREEN + "Successfully " + this.cmdType + "d Rev IDs" + ((type != null) ? (" for " + type.getName()) : "") + "!");
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
