// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.revid;

import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class RevisionIDCommandTreeNode extends CommandTreeNode
{
    public static final Parameter TYPE_OR_ALL;
    
    public RevisionIDCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "revid");
        this.addChild((CommandTreeNode)new RevIDActionCommandTreeNode((CommandTreeNode)this, "increase", n -> Math.min(n + 1, Integer.MAX_VALUE)));
        this.addChild((CommandTreeNode)new RevIDActionCommandTreeNode((CommandTreeNode)this, "decrease", n2 -> Math.max(n2 - 1, 1)));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        return CommandTreeNode.CommandResult.THROW_USAGE;
    }
    
    static {
        TYPE_OR_ALL = new Parameter("<type>", (p0, list) -> {
            MMOItems.plugin.getTypes().getAll().forEach(type -> list.add(type.getId()));
            list.add("ALL");
        });
    }
}
