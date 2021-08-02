// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.google.common.collect.ImmutableList;
import java.util.List;

@Deprecated
public class GameModeCommand extends VanillaCommand
{
    private static final List<String> GAMEMODE_NAMES;
    
    static {
        GAMEMODE_NAMES = ImmutableList.of("adventure", "creative", "survival", "spectator");
    }
    
    public GameModeCommand() {
        super("gamemode");
        this.description = "Changes the player to a specific game mode";
        this.usageMessage = "/gamemode <mode> [player]";
        this.setPermission("bukkit.command.gamemode");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final String modeArg = args[0];
        String playerArg = sender.getName();
        if (args.length == 2) {
            playerArg = args[1];
        }
        final Player player = Bukkit.getPlayerExact(playerArg);
        if (player != null) {
            int value = -1;
            try {
                value = Integer.parseInt(modeArg);
            }
            catch (NumberFormatException ex) {}
            GameMode mode = GameMode.getByValue(value);
            if (mode == null) {
                if (modeArg.equalsIgnoreCase("creative") || modeArg.equalsIgnoreCase("c")) {
                    mode = GameMode.CREATIVE;
                }
                else if (modeArg.equalsIgnoreCase("adventure") || modeArg.equalsIgnoreCase("a")) {
                    mode = GameMode.ADVENTURE;
                }
                else if (modeArg.equalsIgnoreCase("spectator") || modeArg.equalsIgnoreCase("sp")) {
                    mode = GameMode.SPECTATOR;
                }
                else {
                    mode = GameMode.SURVIVAL;
                }
            }
            if (mode != player.getGameMode()) {
                player.setGameMode(mode);
                if (mode != player.getGameMode()) {
                    sender.sendMessage("Game mode change for " + player.getName() + " failed!");
                }
                else if (player == sender) {
                    Command.broadcastCommandMessage(sender, "Set own game mode to " + mode.toString() + " mode");
                }
                else {
                    Command.broadcastCommandMessage(sender, "Set " + player.getName() + "'s game mode to " + mode.toString() + " mode");
                }
            }
            else {
                sender.sendMessage(String.valueOf(player.getName()) + " already has game mode " + mode.getValue());
            }
        }
        else {
            sender.sendMessage("Can't find player " + playerArg);
        }
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], GameModeCommand.GAMEMODE_NAMES, new ArrayList<String>(GameModeCommand.GAMEMODE_NAMES.size()));
        }
        if (args.length == 2) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
