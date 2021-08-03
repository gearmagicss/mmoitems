// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import java.util.Optional;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.template.explorer.IDFilter;
import net.Indyuce.mmoitems.api.item.template.explorer.TypeFilter;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.function.Predicate;
import net.Indyuce.mmoitems.api.item.template.explorer.ClassFilter;
import net.Indyuce.mmoitems.api.item.template.explorer.TemplateExplorer;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.Collection;
import java.util.Arrays;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import java.util.Random;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class GenerateCommandTreeNode extends CommandTreeNode
{
    private static final Random random;
    
    public GenerateCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "generate");
        this.addParameter(Parameter.PLAYER);
        this.addParameter(new Parameter("(extra-args)", (p0, list) -> list.addAll(Arrays.asList("-matchlevel", "-matchclass", "-level:", "-class:", "-type:", "-id:", "-tier:", "-gimme"))));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        try {
            if (array.length < 2) {
                return CommandTreeNode.CommandResult.THROW_USAGE;
            }
            final Player player = Bukkit.getPlayer(array[1]);
            Validate.notNull((Object)player, "Could not find player called " + array[1] + ".");
            final GenerateCommandHandler generateCommandHandler = new GenerateCommandHandler(array);
            final Player player2 = (generateCommandHandler.hasArgument("gimme") || generateCommandHandler.hasArgument("giveme")) ? ((commandSender instanceof Player) ? commandSender : null) : player;
            Validate.notNull((Object)player2, "You cannot use -gimme");
            final RPGPlayer rpg = PlayerData.get((OfflinePlayer)player).getRPG();
            final int n = generateCommandHandler.hasArgument("level") ? Integer.parseInt(generateCommandHandler.getValue("level")) : (generateCommandHandler.hasArgument("matchlevel") ? MMOItems.plugin.getTemplates().rollLevel(rpg.getLevel()) : (1 + GenerateCommandTreeNode.random.nextInt(100)));
            final ItemTier itemTier = generateCommandHandler.hasArgument("tier") ? MMOItems.plugin.getTiers().getOrThrow(generateCommandHandler.getValue("tier").toUpperCase().replace("-", "_")) : MMOItems.plugin.getTemplates().rollTier();
            final TemplateExplorer templateExplorer = new TemplateExplorer();
            if (generateCommandHandler.hasArgument("matchclass")) {
                templateExplorer.applyFilter(new ClassFilter(rpg));
            }
            if (generateCommandHandler.hasArgument("class")) {
                templateExplorer.applyFilter(new ClassFilter(generateCommandHandler.getValue("class").replace("-", " ").replace("_", " ")));
            }
            String value = null;
            if (generateCommandHandler.hasArgument("type")) {
                value = generateCommandHandler.getValue("type");
                Validate.isTrue(Type.isValid(value), "Could not find type with ID '" + value + "'");
                templateExplorer.applyFilter(new TypeFilter(Type.get(value)));
            }
            if (generateCommandHandler.hasArgument("id")) {
                Validate.isTrue(value != null, "You have to specify a type if using the id option!");
                templateExplorer.applyFilter(new IDFilter(generateCommandHandler.getValue("id")));
            }
            final Optional<MMOItemTemplate> rollLoot = templateExplorer.rollLoot();
            Validate.isTrue(rollLoot.isPresent(), "No item matched your criterias.");
            final ItemStack build = rollLoot.get().newBuilder(n, itemTier).build().newBuilder().build();
            Validate.isTrue(build != null && build.getType() != Material.AIR, "Could not generate item with ID '" + rollLoot.get().getId() + "'");
            new SmartGive(player2).give(new ItemStack[] { build });
            return CommandTreeNode.CommandResult.SUCCESS;
        }
        catch (IllegalArgumentException ex) {
            commandSender.sendMessage(ChatColor.RED + ex.getMessage());
            return CommandTreeNode.CommandResult.FAILURE;
        }
    }
    
    static {
        random = new Random();
    }
}
