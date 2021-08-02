// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import java.io.BufferedReader;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.io.Reader;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import com.google.common.io.Resources;
import com.google.common.base.Charsets;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.command.CommandSender;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class VersionCommand extends BukkitCommand
{
    private final ReentrantLock versionLock;
    private boolean hasVersion;
    private String versionMessage;
    private final Set<CommandSender> versionWaiters;
    private boolean versionTaskStarted;
    private long lastCheck;
    
    public VersionCommand(final String name) {
        super(name);
        this.versionLock = new ReentrantLock();
        this.hasVersion = false;
        this.versionMessage = null;
        this.versionWaiters = new HashSet<CommandSender>();
        this.versionTaskStarted = false;
        this.lastCheck = 0L;
        this.description = "Gets the version of this server including any plugins in use";
        this.usageMessage = "/version [plugin name]";
        this.setPermission("bukkit.command.version");
        this.setAliases(Arrays.asList("ver", "about"));
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
            this.sendVersion(sender);
        }
        else {
            final StringBuilder name = new StringBuilder();
            for (final String arg : args) {
                if (name.length() > 0) {
                    name.append(' ');
                }
                name.append(arg);
            }
            String pluginName = name.toString();
            final Plugin exactPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (exactPlugin != null) {
                this.describeToSender(exactPlugin, sender);
                return true;
            }
            boolean found = false;
            pluginName = pluginName.toLowerCase();
            Plugin[] plugins;
            for (int length2 = (plugins = Bukkit.getPluginManager().getPlugins()).length, j = 0; j < length2; ++j) {
                final Plugin plugin = plugins[j];
                if (plugin.getName().toLowerCase().contains(pluginName)) {
                    this.describeToSender(plugin, sender);
                    found = true;
                }
            }
            if (!found) {
                sender.sendMessage("This server is not running any plugin by that name.");
                sender.sendMessage("Use /plugins to get a list of plugins.");
            }
        }
        return true;
    }
    
    private void describeToSender(final Plugin plugin, final CommandSender sender) {
        final PluginDescriptionFile desc = plugin.getDescription();
        sender.sendMessage(ChatColor.GREEN + desc.getName() + ChatColor.WHITE + " version " + ChatColor.GREEN + desc.getVersion());
        if (desc.getDescription() != null) {
            sender.sendMessage(desc.getDescription());
        }
        if (desc.getWebsite() != null) {
            sender.sendMessage("Website: " + ChatColor.GREEN + desc.getWebsite());
        }
        if (!desc.getAuthors().isEmpty()) {
            if (desc.getAuthors().size() == 1) {
                sender.sendMessage("Author: " + this.getAuthors(desc));
            }
            else {
                sender.sendMessage("Authors: " + this.getAuthors(desc));
            }
        }
    }
    
    private String getAuthors(final PluginDescriptionFile desc) {
        final StringBuilder result = new StringBuilder();
        final List<String> authors = desc.getAuthors();
        for (int i = 0; i < authors.size(); ++i) {
            if (result.length() > 0) {
                result.append(ChatColor.WHITE);
                if (i < authors.size() - 1) {
                    result.append(", ");
                }
                else {
                    result.append(" and ");
                }
            }
            result.append(ChatColor.GREEN);
            result.append(authors.get(i));
        }
        return result.toString();
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            final List<String> completions = new ArrayList<String>();
            final String toComplete = args[0].toLowerCase();
            Plugin[] plugins;
            for (int length = (plugins = Bukkit.getPluginManager().getPlugins()).length, i = 0; i < length; ++i) {
                final Plugin plugin = plugins[i];
                if (StringUtil.startsWithIgnoreCase(plugin.getName(), toComplete)) {
                    completions.add(plugin.getName());
                }
            }
            return completions;
        }
        return (List<String>)ImmutableList.of();
    }
    
    private void sendVersion(final CommandSender sender) {
        if (this.hasVersion) {
            if (System.currentTimeMillis() - this.lastCheck <= 21600000L) {
                sender.sendMessage(this.versionMessage);
                return;
            }
            this.lastCheck = System.currentTimeMillis();
            this.hasVersion = false;
        }
        this.versionLock.lock();
        try {
            if (this.hasVersion) {
                sender.sendMessage(this.versionMessage);
                return;
            }
            this.versionWaiters.add(sender);
            sender.sendMessage("Checking version, please wait...");
            if (!this.versionTaskStarted) {
                this.versionTaskStarted = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VersionCommand.this.obtainVersion();
                    }
                }).start();
            }
        }
        finally {
            this.versionLock.unlock();
        }
        this.versionLock.unlock();
    }
    
    private void obtainVersion() {
        String version = Bukkit.getVersion();
        if (version == null) {
            version = "Custom";
        }
        if (version.startsWith("git-Spigot-")) {
            final String[] parts = version.substring("git-Spigot-".length()).split("-");
            final int cbVersions = getDistance("craftbukkit", parts[1].substring(0, parts[1].indexOf(32)));
            final int spigotVersions = getDistance("spigot", parts[0]);
            if (cbVersions == -1 || spigotVersions == -1) {
                this.setVersionMessage("Error obtaining version information");
            }
            else if (cbVersions == 0 && spigotVersions == 0) {
                this.setVersionMessage("You are running the latest version");
            }
            else {
                this.setVersionMessage("You are " + (cbVersions + spigotVersions) + " version(s) behind");
            }
        }
        else if (version.startsWith("git-Bukkit-")) {
            version = version.substring("git-Bukkit-".length());
            final int cbVersions2 = getDistance("craftbukkit", version.substring(0, version.indexOf(32)));
            if (cbVersions2 == -1) {
                this.setVersionMessage("Error obtaining version information");
            }
            else if (cbVersions2 == 0) {
                this.setVersionMessage("You are running the latest version");
            }
            else {
                this.setVersionMessage("You are " + cbVersions2 + " version(s) behind");
            }
        }
        else {
            this.setVersionMessage("Unknown version, custom build?");
        }
    }
    
    private void setVersionMessage(final String msg) {
        this.lastCheck = System.currentTimeMillis();
        this.versionMessage = msg;
        this.versionLock.lock();
        try {
            this.hasVersion = true;
            this.versionTaskStarted = false;
            for (final CommandSender sender : this.versionWaiters) {
                sender.sendMessage(this.versionMessage);
            }
            this.versionWaiters.clear();
        }
        finally {
            this.versionLock.unlock();
        }
        this.versionLock.unlock();
    }
    
    private static int getDistance(final String repo, final String hash) {
        try {
            final BufferedReader reader = Resources.asCharSource(new URL("https://hub.spigotmc.org/stash/rest/api/1.0/projects/SPIGOT/repos/" + repo + "/commits?since=" + URLEncoder.encode(hash, "UTF-8") + "&withCounts=true"), Charsets.UTF_8).openBufferedStream();
            try {
                final JSONObject obj = (JSONObject)new JSONParser().parse(reader);
                return obj.get("totalCount").intValue();
            }
            catch (ParseException ex) {
                ex.printStackTrace();
                return -1;
            }
            finally {
                reader.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
