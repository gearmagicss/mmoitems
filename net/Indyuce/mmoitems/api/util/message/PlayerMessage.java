// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util.message;

import io.lumine.mythic.lib.MythicLib;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class PlayerMessage
{
    private String message;
    
    public PlayerMessage(final String message) {
        this.message = message;
    }
    
    public PlayerMessage format(final ChatColor obj, final String... array) {
        this.message = obj + this.message;
        for (int i = 0; i < array.length; i += 2) {
            this.message = this.message.replace(array[i], array[i + 1]);
        }
        return this;
    }
    
    public void send(final CommandSender commandSender) {
        if (!ChatColor.stripColor(this.message).equals("")) {
            commandSender.sendMessage(this.message);
        }
    }
    
    public void send(final Player player, final String str) {
        if (ChatColor.stripColor(this.message).equals("")) {
            return;
        }
        if (MMOItems.plugin.getConfig().getBoolean("action-bar-display." + str)) {
            if (Bukkit.getPluginManager().isPluginEnabled("MMOCore")) {
                PlayerData.get((OfflinePlayer)player).setActionBarTimeOut(60L);
            }
            MythicLib.plugin.getVersion().getWrapper().sendActionBar(player, this.message);
        }
        else {
            player.sendMessage(this.message);
        }
    }
    
    @Override
    public String toString() {
        return this.message;
    }
}
