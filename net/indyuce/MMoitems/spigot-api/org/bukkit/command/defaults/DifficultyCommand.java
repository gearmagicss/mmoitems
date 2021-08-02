// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.google.common.collect.ImmutableList;
import java.util.List;

@Deprecated
public class DifficultyCommand extends VanillaCommand
{
    private static final List<String> DIFFICULTY_NAMES;
    
    static {
        DIFFICULTY_NAMES = ImmutableList.of("peaceful", "easy", "normal", "hard");
    }
    
    public DifficultyCommand() {
        super("difficulty");
        this.description = "Sets the game difficulty";
        this.usageMessage = "/difficulty <new difficulty> ";
        this.setPermission("bukkit.command.difficulty");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length != 1 || args[0].length() == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        Difficulty difficulty = Difficulty.getByValue(this.getDifficultyForString(sender, args[0]));
        if (Bukkit.isHardcore()) {
            difficulty = Difficulty.HARD;
        }
        Bukkit.getWorlds().get(0).setDifficulty(difficulty);
        int levelCount = 1;
        if (Bukkit.getAllowNether()) {
            Bukkit.getWorlds().get(levelCount).setDifficulty(difficulty);
            ++levelCount;
        }
        if (Bukkit.getAllowEnd()) {
            Bukkit.getWorlds().get(levelCount).setDifficulty(difficulty);
        }
        Command.broadcastCommandMessage(sender, "Set difficulty to " + difficulty.toString());
        return true;
    }
    
    protected int getDifficultyForString(final CommandSender sender, final String name) {
        if (name.equalsIgnoreCase("peaceful") || name.equalsIgnoreCase("p")) {
            return 0;
        }
        if (name.equalsIgnoreCase("easy") || name.equalsIgnoreCase("e")) {
            return 1;
        }
        if (name.equalsIgnoreCase("normal") || name.equalsIgnoreCase("n")) {
            return 2;
        }
        if (name.equalsIgnoreCase("hard") || name.equalsIgnoreCase("h")) {
            return 3;
        }
        return this.getInteger(sender, name, 0, 3);
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], DifficultyCommand.DIFFICULTY_NAMES, new ArrayList<String>(DifficultyCommand.DIFFICULTY_NAMES.size()));
        }
        return (List<String>)ImmutableList.of();
    }
}
