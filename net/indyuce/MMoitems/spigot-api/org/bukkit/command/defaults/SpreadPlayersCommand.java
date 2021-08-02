// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import java.util.Iterator;
import java.util.Set;
import com.google.common.collect.Sets;
import org.bukkit.scoreboard.Team;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.Bukkit;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.Random;

@Deprecated
public class SpreadPlayersCommand extends VanillaCommand
{
    private static final Random random;
    
    static {
        random = new Random();
    }
    
    public SpreadPlayersCommand() {
        super("spreadplayers");
        this.description = "Spreads players around a point";
        this.usageMessage = "/spreadplayers <x> <z> <spreadDistance> <maxRange> <respectTeams true|false> <player ...>";
        this.setPermission("bukkit.command.spreadplayers");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 6) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final double x = VanillaCommand.getDouble(sender, args[0], -3.0E7, 3.0E7);
        final double z = VanillaCommand.getDouble(sender, args[1], -3.0E7, 3.0E7);
        final double distance = VanillaCommand.getDouble(sender, args[2]);
        final double range = VanillaCommand.getDouble(sender, args[3]);
        if (distance < 0.0) {
            sender.sendMessage(ChatColor.RED + "Distance is too small.");
            return false;
        }
        if (range < distance + 1.0) {
            sender.sendMessage(ChatColor.RED + "Max range is too small.");
            return false;
        }
        final String respectTeams = args[4];
        boolean teams = false;
        if (respectTeams.equalsIgnoreCase("true")) {
            teams = true;
        }
        else if (!respectTeams.equalsIgnoreCase("false")) {
            sender.sendMessage(String.format(ChatColor.RED + "'%s' is not true or false", args[4]));
            return false;
        }
        final List<Player> players = (List<Player>)Lists.newArrayList();
        World world = null;
        for (int i = 5; i < args.length; ++i) {
            final Player player = Bukkit.getPlayerExact(args[i]);
            if (player != null) {
                if (world == null) {
                    world = player.getWorld();
                }
                players.add(player);
            }
        }
        if (world == null) {
            return true;
        }
        final double xRangeMin = x - range;
        final double zRangeMin = z - range;
        final double xRangeMax = x + range;
        final double zRangeMax = z + range;
        final int spreadSize = teams ? this.getTeams(players) : players.size();
        final Location[] locations = this.getSpreadLocations(world, spreadSize, xRangeMin, zRangeMin, xRangeMax, zRangeMax);
        final int rangeSpread = this.range(world, distance, xRangeMin, zRangeMin, xRangeMax, zRangeMax, locations);
        if (rangeSpread == -1) {
            sender.sendMessage(String.format("Could not spread %d %s around %s,%s (too many players for space - try using spread of at most %s)", spreadSize, teams ? "teams" : "players", x, z));
            return false;
        }
        final double distanceSpread = this.spread(world, players, locations, teams);
        sender.sendMessage(String.format("Succesfully spread %d %s around %s,%s", locations.length, teams ? "teams" : "players", x, z));
        if (locations.length > 1) {
            sender.sendMessage(String.format("(Average distance between %s is %s blocks apart after %s iterations)", teams ? "teams" : "players", String.format("%.2f", distanceSpread), rangeSpread));
        }
        return true;
    }
    
    private int range(final World world, final double distance, final double xRangeMin, final double zRangeMin, final double xRangeMax, final double zRangeMax, final Location[] locations) {
        boolean flag;
        int i;
        double max;
        int k;
        Location loc2;
        int j;
        Location loc3;
        int l;
        Location loc4;
        double dis;
        double d7;
        double x;
        double z;
        boolean swap;
        Location[] locs;
        int i2;
        double x2;
        double z2;
        for (flag = true, i = 0; i < 10000 && flag; ++i) {
            flag = false;
            max = 3.4028234663852886E38;
            for (k = 0; k < locations.length; ++k) {
                loc2 = locations[k];
                j = 0;
                loc3 = new Location(world, 0.0, 0.0, 0.0);
                for (l = 0; l < locations.length; ++l) {
                    if (k != l) {
                        loc4 = locations[l];
                        dis = loc2.distanceSquared(loc4);
                        max = Math.min(dis, max);
                        if (dis < distance) {
                            ++j;
                            loc3.add(loc4.getX() - loc2.getX(), 0.0, 0.0);
                            loc3.add(loc4.getZ() - loc2.getZ(), 0.0, 0.0);
                        }
                    }
                }
                if (j > 0) {
                    loc2.setX(loc2.getX() / j);
                    loc2.setZ(loc2.getZ() / j);
                    d7 = Math.sqrt(loc3.getX() * loc3.getX() + loc3.getZ() * loc3.getZ());
                    if (d7 > 0.0) {
                        loc3.setX(loc3.getX() / d7);
                        loc2.add(-loc3.getX(), 0.0, -loc3.getZ());
                    }
                    else {
                        x = ((xRangeMin >= xRangeMax) ? xRangeMin : (SpreadPlayersCommand.random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin));
                        z = ((zRangeMin >= zRangeMax) ? zRangeMin : (SpreadPlayersCommand.random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin));
                        loc2.setX(x);
                        loc2.setZ(z);
                    }
                    flag = true;
                }
                swap = false;
                if (loc2.getX() < xRangeMin) {
                    loc2.setX(xRangeMin);
                    swap = true;
                }
                else if (loc2.getX() > xRangeMax) {
                    loc2.setX(xRangeMax);
                    swap = true;
                }
                if (loc2.getZ() < zRangeMin) {
                    loc2.setZ(zRangeMin);
                    swap = true;
                }
                else if (loc2.getZ() > zRangeMax) {
                    loc2.setZ(zRangeMax);
                    swap = true;
                }
                if (swap) {
                    flag = true;
                }
            }
            if (!flag) {
                locs = locations;
                for (i2 = locations.length, j = 0; j < i2; ++j) {
                    loc3 = locs[j];
                    if (world.getHighestBlockYAt(loc3) == 0) {
                        x2 = ((xRangeMin >= xRangeMax) ? xRangeMin : (SpreadPlayersCommand.random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin));
                        z2 = ((zRangeMin >= zRangeMax) ? zRangeMin : (SpreadPlayersCommand.random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin));
                        locations[i] = new Location(world, x2, 0.0, z2);
                        loc3.setX(x2);
                        loc3.setZ(z2);
                        flag = true;
                    }
                }
            }
        }
        if (i >= 10000) {
            return -1;
        }
        return i;
    }
    
    private double spread(final World world, final List<Player> list, final Location[] locations, final boolean teams) {
        double distance = 0.0;
        int i = 0;
        final Map<Team, Location> hashmap = (Map<Team, Location>)Maps.newHashMap();
        for (int j = 0; j < list.size(); ++j) {
            final Player player = list.get(j);
            Location location;
            if (teams) {
                final Team team = player.getScoreboard().getPlayerTeam(player);
                if (!hashmap.containsKey(team)) {
                    hashmap.put(team, locations[i++]);
                }
                location = hashmap.get(team);
            }
            else {
                location = locations[i++];
            }
            player.teleport(new Location(world, Math.floor(location.getX()) + 0.5, world.getHighestBlockYAt((int)location.getX(), (int)location.getZ()), Math.floor(location.getZ()) + 0.5));
            double value = Double.MAX_VALUE;
            for (int k = 0; k < locations.length; ++k) {
                if (location != locations[k]) {
                    final double d = location.distanceSquared(locations[k]);
                    value = Math.min(d, value);
                }
            }
            distance += value;
        }
        distance /= list.size();
        return distance;
    }
    
    private int getTeams(final List<Player> players) {
        final Set<Team> teams = (Set<Team>)Sets.newHashSet();
        for (final Player player : players) {
            teams.add(player.getScoreboard().getPlayerTeam(player));
        }
        return teams.size();
    }
    
    private Location[] getSpreadLocations(final World world, final int size, final double xRangeMin, final double zRangeMin, final double xRangeMax, final double zRangeMax) {
        final Location[] locations = new Location[size];
        for (int i = 0; i < size; ++i) {
            final double x = (xRangeMin >= xRangeMax) ? xRangeMin : (SpreadPlayersCommand.random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin);
            final double z = (zRangeMin >= zRangeMax) ? zRangeMin : (SpreadPlayersCommand.random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin);
            locations[i] = new Location(world, x, 0.0, z);
        }
        return locations;
    }
}
