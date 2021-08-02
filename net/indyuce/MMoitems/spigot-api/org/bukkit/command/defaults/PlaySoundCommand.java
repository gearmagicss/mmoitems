// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Deprecated
public class PlaySoundCommand extends VanillaCommand
{
    public PlaySoundCommand() {
        super("playsound");
        this.description = "Plays a sound to a given player";
        this.usageMessage = "/playsound <sound> <player> [x] [y] [z] [volume] [pitch] [minimumVolume]";
        this.setPermission("bukkit.command.playsound");
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
        final String soundArg = args[0];
        final String playerArg = args[1];
        final Player player = Bukkit.getPlayerExact(playerArg);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Can't find player " + playerArg);
            return false;
        }
        final Location location = player.getLocation();
        double x = Math.floor(location.getX());
        double y = Math.floor(location.getY() + 0.5);
        double z = Math.floor(location.getZ());
        double volume = 1.0;
        double pitch = 1.0;
        double minimumVolume = 0.0;
        switch (args.length) {
            default: {
                minimumVolume = VanillaCommand.getDouble(sender, args[7], 0.0, 1.0);
            }
            case 7: {
                pitch = VanillaCommand.getDouble(sender, args[6], 0.0, 2.0);
            }
            case 6: {
                volume = VanillaCommand.getDouble(sender, args[5], 0.0, 3.4028234663852886E38);
            }
            case 5: {
                z = VanillaCommand.getRelativeDouble(z, sender, args[4]);
            }
            case 4: {
                y = VanillaCommand.getRelativeDouble(y, sender, args[3]);
            }
            case 3: {
                x = VanillaCommand.getRelativeDouble(x, sender, args[2]);
            }
            case 2: {
                final double fixedVolume = (volume > 1.0) ? (volume * 16.0) : 16.0;
                final Location soundLocation = new Location(player.getWorld(), x, y, z);
                if (location.distanceSquared(soundLocation) > fixedVolume * fixedVolume) {
                    if (minimumVolume <= 0.0) {
                        sender.sendMessage(ChatColor.RED + playerArg + " is too far away to hear the sound");
                        return false;
                    }
                    final double deltaX = x - location.getX();
                    final double deltaY = y - location.getY();
                    final double deltaZ = z - location.getZ();
                    final double delta = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / 2.0;
                    if (delta > 0.0) {
                        location.add(deltaX / delta, deltaY / delta, deltaZ / delta);
                    }
                    player.playSound(location, soundArg, (float)minimumVolume, (float)pitch);
                }
                else {
                    player.playSound(soundLocation, soundArg, (float)volume, (float)pitch);
                }
                sender.sendMessage(String.format("Played '%s' to %s", soundArg, playerArg));
                return true;
            }
        }
    }
}
