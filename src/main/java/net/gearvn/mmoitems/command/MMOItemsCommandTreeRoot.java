// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command;

import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import net.Indyuce.mmoitems.command.item.ItemCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.revid.RevisionIDCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.ItemListCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.GiveAllCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.AbilityCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.DropCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.list.ListCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.AllItemsCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.stations.StationsCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.ReloadCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.debug.DebugCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.update.UpdateCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.BrowseCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.GenerateCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.GiveCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.CopyCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.EditCommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.DeleteCommandTreeNode;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;
import net.Indyuce.mmoitems.command.mmoitems.CreateCommandTreeNode;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeRoot;

public class MMOItemsCommandTreeRoot extends CommandTreeRoot
{
    public static final Parameter TYPE;
    public static final Parameter ID_2;
    
    public MMOItemsCommandTreeRoot() {
        super("mmoitems", "mmoitems.admin");
        this.addChild((CommandTreeNode)new CreateCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new DeleteCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new EditCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new CopyCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new GiveCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new GenerateCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new BrowseCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new UpdateCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new DebugCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new ReloadCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new StationsCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new AllItemsCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new ListCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new DropCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new AbilityCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new GiveAllCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new ItemListCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new RevisionIDCommandTreeNode((CommandTreeNode)this));
        this.addChild((CommandTreeNode)new ItemCommandTreeNode((CommandTreeNode)this));
    }
    
    static {
        TYPE = new Parameter("<type>", (p0, list) -> MMOItems.plugin.getTypes().getAll().forEach(type -> list.add(type.getId())));
        ID_2 = new Parameter("<id>", (commandTreeExplorer, list2) -> {
            try {
                MMOItems.plugin.getTemplates().getTemplates(Type.get(commandTreeExplorer.getArguments()[1])).forEach(mmoItemTemplate -> list2.add(mmoItemTemplate.getId()));
            }
            catch (Exception ex) {}
        });
    }
}
