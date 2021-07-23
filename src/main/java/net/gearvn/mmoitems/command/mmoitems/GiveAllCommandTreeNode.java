// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.droptable.item.MMOItemDropItem;
import net.Indyuce.mmoitems.api.util.RandomAmount;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class GiveAllCommandTreeNode extends CommandTreeNode
{
    public GiveAllCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "giveall");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(MMOItemsCommandTreeRoot.ID_2);
        this.addParameter(new Parameter("<min-max>", (p0, list) -> list.add("1-3")));
        this.addParameter(new Parameter("<unidentify-chance>", (p0, list2) -> list2.add("<unidentify-chance>")));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        try {
            Validate.isTrue(array.length > 4, "Usage: /mi giveall <type> <item-id> <min-max> <unidentified-chance>");
            final ItemStack item = new MMOItemDropItem(MMOItems.plugin.getTypes().getOrThrow(array[1]), array[2], 1.0, Double.parseDouble(array[4]) / 100.0, new RandomAmount(array[3])).getItem(null);
            Validate.isTrue(item != null && item.getType() != Material.AIR, "Couldn't find/generate the item called '" + array[1].toUpperCase() + "'. Check your console for potential item generation issues.");
            final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
            while (iterator.hasNext()) {
                new SmartGive((Player)iterator.next()).give(new ItemStack[] { item });
            }
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        catch (IllegalArgumentException ex) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + ex.getMessage());
            return CommandTreeNode.CommandResult.FAILURE;
        }
    }
}
