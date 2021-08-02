// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

@Deprecated
public class SetWorldSpawnCommand extends VanillaCommand
{
    public SetWorldSpawnCommand() {
        super("setworldspawn");
        this.description = "Sets a worlds's spawn point. If no coordinates are specified, the player's coordinates will be used.";
        this.usageMessage = "/setworldspawn OR /setworldspawn <x> <y> <z>";
        this.setPermission("bukkit.command.setworldspawn");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        Player player = null;
        World world;
        if (sender instanceof Player) {
            player = (Player)sender;
            world = player.getWorld();
        }
        else {
            world = Bukkit.getWorlds().get(0);
        }
        int x = 0;
        int y = 0;
        int z = 0;
        Label_0214: {
            if (args.length != 0) {
                if (args.length == 3) {
                    try {
                        x = this.getInteger(sender, args[0], -30000000, 30000000, true);
                        y = this.getInteger(sender, args[1], 0, world.getMaxHeight(), true);
                        z = this.getInteger(sender, args[2], -30000000, 30000000, true);
                        break Label_0214;
                    }
                    catch (NumberFormatException ex) {
                        sender.sendMessage(ex.getMessage());
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
                return false;
            }
            if (player == null) {
                sender.sendMessage("You can only perform this command as a player");
                return true;
            }
            final Location location = player.getLocation();
            x = location.getBlockX();
            y = location.getBlockY();
            z = location.getBlockZ();
        }
        world.setSpawnLocation(x, y, z);
        Command.broadcastCommandMessage(sender, "Set world " + world.getName() + "'s spawnpoint to (" + x + ", " + y + ", " + z + ")");
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
