// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Deprecated
public class AchievementCommand extends VanillaCommand
{
    public AchievementCommand() {
        super("achievement");
        this.description = "Gives the specified player an achievement or changes a statistic value. Use '*' to give all achievements.";
        this.usageMessage = "/achievement give <stat_name> [player]";
        this.setPermission("bukkit.command.achievement");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        if (!args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final String statisticString = args[1];
        Player player = null;
        if (args.length > 2) {
            player = Bukkit.getPlayer(args[1]);
        }
        else if (sender instanceof Player) {
            player = (Player)sender;
        }
        if (player == null) {
            sender.sendMessage("You must specify which player you wish to perform this action on.");
            return true;
        }
        if (statisticString.equals("*")) {
            Achievement[] values;
            for (int length = (values = Achievement.values()).length, i = 0; i < length; ++i) {
                final Achievement achievement = values[i];
                if (!player.hasAchievement(achievement)) {
                    final PlayerAchievementAwardedEvent event = new PlayerAchievementAwardedEvent(player, achievement);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        player.awardAchievement(achievement);
                    }
                }
            }
            Command.broadcastCommandMessage(sender, String.format("Successfully given all achievements to %s", player.getName()));
            return true;
        }
        final Achievement achievement = Bukkit.getUnsafe().getAchievementFromInternalName(statisticString);
        final Statistic statistic = Bukkit.getUnsafe().getStatisticFromInternalName(statisticString);
        if (achievement != null) {
            if (player.hasAchievement(achievement)) {
                sender.sendMessage(String.format("%s already has achievement %s", player.getName(), statisticString));
                return true;
            }
            final PlayerAchievementAwardedEvent event2 = new PlayerAchievementAwardedEvent(player, achievement);
            Bukkit.getServer().getPluginManager().callEvent(event2);
            if (event2.isCancelled()) {
                sender.sendMessage(String.format("Unable to award %s the achievement %s", player.getName(), statisticString));
                return true;
            }
            player.awardAchievement(achievement);
            Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", player.getName(), statisticString));
            return true;
        }
        else {
            if (statistic == null) {
                sender.sendMessage(String.format("Unknown achievement or statistic '%s'", statisticString));
                return true;
            }
            if (statistic.getType() != Statistic.Type.UNTYPED) {
                Label_1012: {
                    if (statistic.getType() == Statistic.Type.ENTITY) {
                        final EntityType entityType = EntityType.fromName(statisticString.substring(statisticString.lastIndexOf(".") + 1));
                        if (entityType == null) {
                            sender.sendMessage(String.format("Unknown achievement or statistic '%s'", statisticString));
                            return true;
                        }
                        final PlayerStatisticIncrementEvent event3 = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1, entityType);
                        Bukkit.getServer().getPluginManager().callEvent(event3);
                        if (event3.isCancelled()) {
                            sender.sendMessage(String.format("Unable to increment %s for %s", statisticString, player.getName()));
                            return true;
                        }
                        try {
                            player.incrementStatistic(statistic, entityType);
                            break Label_1012;
                        }
                        catch (IllegalArgumentException ex) {
                            sender.sendMessage(String.format("Unknown achievement or statistic '%s'", statisticString));
                            return true;
                        }
                    }
                    int id;
                    try {
                        id = this.getInteger(sender, statisticString.substring(statisticString.lastIndexOf(".") + 1), 0, Integer.MAX_VALUE, true);
                    }
                    catch (NumberFormatException e) {
                        sender.sendMessage(e.getMessage());
                        return true;
                    }
                    final Material material = Material.getMaterial(id);
                    if (material == null) {
                        sender.sendMessage(String.format("Unknown achievement or statistic '%s'", statisticString));
                        return true;
                    }
                    final PlayerStatisticIncrementEvent event4 = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1, material);
                    Bukkit.getServer().getPluginManager().callEvent(event4);
                    if (event4.isCancelled()) {
                        sender.sendMessage(String.format("Unable to increment %s for %s", statisticString, player.getName()));
                        return true;
                    }
                    try {
                        player.incrementStatistic(statistic, material);
                    }
                    catch (IllegalArgumentException ex2) {
                        sender.sendMessage(String.format("Unknown achievement or statistic '%s'", statisticString));
                        return true;
                    }
                }
                Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", player.getName(), statisticString));
                return true;
            }
            final PlayerStatisticIncrementEvent event5 = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1);
            Bukkit.getServer().getPluginManager().callEvent(event5);
            if (event5.isCancelled()) {
                sender.sendMessage(String.format("Unable to increment %s for %s", statisticString, player.getName()));
                return true;
            }
            player.incrementStatistic(statistic);
            Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", player.getName(), statisticString));
            return true;
        }
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return Arrays.asList("give");
        }
        if (args.length == 2) {
            return Bukkit.getUnsafe().tabCompleteInternalStatisticOrAchievementName(args[1], new ArrayList<String>());
        }
        if (args.length == 3) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
