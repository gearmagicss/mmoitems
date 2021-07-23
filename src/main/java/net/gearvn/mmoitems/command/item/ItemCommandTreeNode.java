// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.item;

import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class ItemCommandTreeNode extends CommandTreeNode
{
    public ItemCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "item");
        this.addChild((CommandTreeNode)new IdentifyCommandTreeNode(this));
        this.addChild((CommandTreeNode)new UnidentifyCommandTreeNode(this));
        this.addChild((CommandTreeNode)new RepairCommandTreeNode(this));
        this.addChild((CommandTreeNode)new DeconstructCommandTreeNode(this));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        return CommandTreeNode.CommandResult.THROW_USAGE;
    }
}
