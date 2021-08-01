// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.debug;

import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class DebugCommandTreeNode extends CommandTreeNode
{
    public DebugCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "debug");
        this.addChild((CommandTreeNode)new CheckStatCommandTreeNode(this));
        this.addChild((CommandTreeNode)new CheckAttributeCommandTreeNode(this));
        this.addChild((CommandTreeNode)new CheckTagCommandTreeNode(this));
        this.addChild((CommandTreeNode)new SetTagCommandTreeNode(this));
        this.addChild((CommandTreeNode)new CheckTagsCommandTreeNode(this));
        this.addChild((CommandTreeNode)new InfoCommandTreeNode(this));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        return CommandTreeNode.CommandResult.THROW_USAGE;
    }
}
