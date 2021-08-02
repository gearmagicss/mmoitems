// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@Deprecated
public class ListCommand extends VanillaCommand
{
    public ListCommand() {
        super("list");
        this.description = "Lists all online players";
        this.usageMessage = "/list";
        this.setPermission("bukkit.command.list");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        final StringBuilder online = new StringBuilder();
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (final Player player : players) {
            if (sender instanceof Player && !((Player)sender).canSee(player)) {
                continue;
            }
            if (online.length() > 0) {
                online.append(", ");
            }
            online.append(player.getDisplayName());
        }
        sender.sendMessage("There are " + players.size() + "/" + Bukkit.getMaxPlayers() + " players online:\n" + online.toString());
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
