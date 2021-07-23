// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.update;

import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class UpdateCommandTreeNode extends CommandTreeNode
{
    public UpdateCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "update");
        this.addChild((CommandTreeNode)new ListCommandTreeNode(this));
        this.addChild((CommandTreeNode)new ApplyCommandTreeNode(this));
        this.addChild((CommandTreeNode)new InfoCommandTreeNode(this));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        return CommandTreeNode.CommandResult.THROW_USAGE;
    }
}
