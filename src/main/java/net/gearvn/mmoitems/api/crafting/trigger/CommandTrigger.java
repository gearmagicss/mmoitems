// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.trigger;

import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class CommandTrigger extends Trigger
{
    private String command;
    private final String sender;
    
    public CommandTrigger(final MMOLineConfig mmoLineConfig) {
        super("command");
        mmoLineConfig.validate(new String[] { "format" });
        this.sender = mmoLineConfig.getString("sender", "PLAYER").toUpperCase();
        this.command = mmoLineConfig.getString("format");
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        this.dispatchCommand(playerData.getPlayer(), this.sender.equals("CONSOLE"), this.sender.equals("OP"));
    }
    
    private void dispatchCommand(final Player player, final boolean b, final boolean b2) {
        final String replaceAll = this.command.replaceAll("(?i)%player%", player.getName());
        if (b) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), replaceAll);
            return;
        }
        if (b2 && !player.isOp()) {
            player.setOp(true);
            try {
                Bukkit.dispatchCommand((CommandSender)player, replaceAll);
            }
            catch (Exception ex) {}
            player.setOp(false);
        }
        else {
            Bukkit.dispatchCommand((CommandSender)player, replaceAll);
        }
    }
}
