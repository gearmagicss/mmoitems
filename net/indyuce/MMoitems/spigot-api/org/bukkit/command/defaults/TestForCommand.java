// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Deprecated
public class TestForCommand extends VanillaCommand
{
    public TestForCommand() {
        super("testfor");
        this.description = "Tests whether a specifed player is online";
        this.usageMessage = "/testfor <player>";
        this.setPermission("bukkit.command.testfor");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        sender.sendMessage(ChatColor.RED + "/testfor is only usable by commandblocks with analog output.");
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        if (args.length == 0) {
            return super.tabComplete(sender, alias, args);
        }
        return Collections.emptyList();
    }
}
