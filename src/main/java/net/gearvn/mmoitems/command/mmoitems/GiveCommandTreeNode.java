// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.util.RandomAmount;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.util.Collection;
import java.util.Arrays;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import java.util.Random;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class GiveCommandTreeNode extends CommandTreeNode
{
    private static final Random random;
    
    public GiveCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "give");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(MMOItemsCommandTreeRoot.ID_2);
        this.addParameter(Parameter.PLAYER_OPTIONAL);
        this.addParameter(new Parameter("(min-max)", (p0, list) -> list.addAll(Arrays.asList("1-3", "1", "10", "32", "64"))));
        this.addParameter(new Parameter("(unidentify-chance)", (p0, list2) -> list2.add("(unidentify-chance)")));
        this.addParameter(new Parameter("(drop-chance)", (p0, list3) -> list3.add("(drop-chance)")));
        this.addParameter(new Parameter("(soulbound-chance)", (p0, list4) -> list4.add("(soulbound-chance)")));
        this.addParameter(new Parameter("(silent)", (p0, list5) -> list5.addAll(Arrays.asList("silent", "s"))));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 3) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        try {
            Validate.isTrue(array.length > 3 || commandSender instanceof Player, "Please specify a player.");
            final Player obj = (Player)((array.length > 3) ? Bukkit.getPlayer(array[3]) : commandSender);
            Validate.notNull((Object)obj, "Could not find player called '" + array[(array.length > 3) ? 3 : 2] + "'.");
            final Type orThrow = MMOItems.plugin.getTypes().getOrThrow(array[1].toUpperCase().replace("-", "_"));
            final MMOItemTemplate templateOrThrow = MMOItems.plugin.getTemplates().getTemplateOrThrow(orThrow, array[2].toUpperCase().replace("-", "_"));
            final RandomAmount randomAmount = (array.length > 4) ? new RandomAmount(array[4]) : new RandomAmount(1, 1);
            final double n = (array.length > 5) ? (Double.parseDouble(array[5]) / 100.0) : 0.0;
            final double n2 = (array.length > 6) ? (Double.parseDouble(array[6]) / 100.0) : 1.0;
            final double n3 = (array.length > 7) ? (Double.parseDouble(array[7]) / 100.0) : 0.0;
            final boolean b = array.length > 8 && (array[8].equalsIgnoreCase("silent") || array[8].equalsIgnoreCase("s"));
            if (GiveCommandTreeNode.random.nextDouble() > n2) {
                return CommandTreeNode.CommandResult.SUCCESS;
            }
            final MMOItem build = templateOrThrow.newBuilder(PlayerData.get((OfflinePlayer)obj).getRPG()).build();
            if (GiveCommandTreeNode.random.nextDouble() < n3) {
                build.setData(ItemStats.SOULBOUND, new SoulboundData(obj, 1));
            }
            final ItemStack itemStack = (GiveCommandTreeNode.random.nextDouble() < n) ? orThrow.getUnidentifiedTemplate().newBuilder(build.newBuilder().buildNBT()).build() : build.newBuilder().build();
            Validate.isTrue(itemStack != null && itemStack.getType() != Material.AIR, "Couldn't find/generate the item called '" + templateOrThrow.getId() + "'. Check your console for potential item generation issues.");
            itemStack.setAmount(randomAmount.getRandomAmount());
            if (!commandSender.equals(obj)) {
                commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.YELLOW + "Successfully gave " + ChatColor.GOLD + MMOUtils.getDisplayName(itemStack) + ((itemStack.getAmount() > 1) ? (" x" + itemStack.getAmount()) : "") + ChatColor.YELLOW + " to " + ChatColor.GOLD + obj.getName() + ChatColor.YELLOW + ".");
            }
            if (!b) {
                Message.RECEIVED_ITEM.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(itemStack), "#amount#", (itemStack.getAmount() > 1) ? (" x" + itemStack.getAmount()) : "").send((CommandSender)obj);
            }
            new SmartGive(obj).give(new ItemStack[] { itemStack });
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        catch (IllegalArgumentException ex) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + ex.getMessage());
            return CommandTreeNode.CommandResult.FAILURE;
        }
    }
    
    static {
        random = new Random();
    }
}
