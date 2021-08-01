// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.stations;

import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class StationsCommandTreeNode extends CommandTreeNode
{
    public StationsCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "stations");
        this.addChild((CommandTreeNode)new OpenCommandTreeNode(this));
        this.addChild((CommandTreeNode)new ListCommandTreeNode(this));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        return CommandTreeNode.CommandResult.THROW_USAGE;
    }
}
