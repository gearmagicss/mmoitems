// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import java.util.Collections;
import java.util.List;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.Arrays;

public class PluginsCommand extends BukkitCommand
{
    public PluginsCommand(final String name) {
        super(name);
        this.description = "Gets a list of plugins running on the server";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(Arrays.asList("pl"));
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        sender.sendMessage("Plugins " + this.getPluginList());
        return true;
    }
    
    private String getPluginList() {
        final StringBuilder pluginList = new StringBuilder();
        final Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        Plugin[] array;
        for (int length = (array = plugins).length, i = 0; i < length; ++i) {
            final Plugin plugin = array[i];
            if (pluginList.length() > 0) {
                pluginList.append(ChatColor.WHITE);
                pluginList.append(", ");
            }
            pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
            pluginList.append(plugin.getDescription().getName());
        }
        return "(" + plugins.length + "): " + pluginList.toString();
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }
}
