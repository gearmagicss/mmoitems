// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import java.util.logging.Level;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.spigotmc.CustomTimingsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.command.CommandSender;
import com.google.common.collect.ImmutableList;
import java.util.List;

public class TimingsCommand extends BukkitCommand
{
    private static final List<String> TIMINGS_SUBCOMMANDS;
    public static long timingStart;
    
    static {
        TIMINGS_SUBCOMMANDS = ImmutableList.of("report", "reset", "on", "off", "paste");
        TimingsCommand.timingStart = 0L;
    }
    
    public TimingsCommand(final String name) {
        super(name);
        this.description = "Manages Spigot Timings data to see performance of the server.";
        this.usageMessage = "/timings <reset|report|on|off|paste>";
        this.setPermission("bukkit.command.timings");
    }
    
    public void executeSpigotTimings(final CommandSender sender, final String[] args) {
        if ("on".equals(args[0])) {
            ((SimplePluginManager)Bukkit.getPluginManager()).useTimings(true);
            CustomTimingsHandler.reload();
            sender.sendMessage("Enabled Timings & Reset");
            return;
        }
        if ("off".equals(args[0])) {
            ((SimplePluginManager)Bukkit.getPluginManager()).useTimings(false);
            sender.sendMessage("Disabled Timings");
            return;
        }
        if (!Bukkit.getPluginManager().useTimings()) {
            sender.sendMessage("Please enable timings by typing /timings on");
            return;
        }
        final boolean paste = "paste".equals(args[0]);
        if ("reset".equals(args[0])) {
            CustomTimingsHandler.reload();
            sender.sendMessage("Timings reset");
        }
        else if ("merged".equals(args[0]) || "report".equals(args[0]) || paste) {
            final long sampleTime = System.nanoTime() - TimingsCommand.timingStart;
            int index = 0;
            final File timingFolder = new File("timings");
            timingFolder.mkdirs();
            File timings = new File(timingFolder, "timings.txt");
            final ByteArrayOutputStream bout = paste ? new ByteArrayOutputStream() : null;
            while (timings.exists()) {
                timings = new File(timingFolder, "timings" + ++index + ".txt");
            }
            PrintStream fileTimings = null;
            try {
                fileTimings = (paste ? new PrintStream(bout) : new PrintStream(timings));
                CustomTimingsHandler.printTimings(fileTimings);
                fileTimings.println("Sample time " + sampleTime + " (" + sampleTime / 1.0E9 + "s)");
                fileTimings.println("<spigotConfig>");
                fileTimings.println(Bukkit.spigot().getConfig().saveToString());
                fileTimings.println("</spigotConfig>");
                if (paste) {
                    new PasteThread(sender, bout).start();
                    return;
                }
                sender.sendMessage("Timings written to " + timings.getPath());
                sender.sendMessage("Paste contents of file into form at http://www.spigotmc.org/go/timings to read results.");
            }
            catch (IOException ex) {
                return;
            }
            finally {
                if (fileTimings != null) {
                    fileTimings.close();
                }
            }
            if (fileTimings != null) {
                fileTimings.close();
            }
        }
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
        this.executeSpigotTimings(sender, args);
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], TimingsCommand.TIMINGS_SUBCOMMANDS, new ArrayList<String>(TimingsCommand.TIMINGS_SUBCOMMANDS.size()));
        }
        return (List<String>)ImmutableList.of();
    }
    
    private static class PasteThread extends Thread
    {
        private final CommandSender sender;
        private final ByteArrayOutputStream bout;
        
        public PasteThread(final CommandSender sender, final ByteArrayOutputStream bout) {
            super("Timings paste thread");
            this.sender = sender;
            this.bout = bout;
        }
        
        @Override
        public synchronized void start() {
            if (this.sender instanceof RemoteConsoleCommandSender) {
                this.run();
            }
            else {
                super.start();
            }
        }
        
        @Override
        public void run() {
            try {
                final HttpURLConnection con = (HttpURLConnection)new URL("http://paste.ubuntu.com/").openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setInstanceFollowRedirects(false);
                final OutputStream out = con.getOutputStream();
                out.write("poster=Spigot&syntax=text&content=".getBytes("UTF-8"));
                out.write(URLEncoder.encode(this.bout.toString("UTF-8"), "UTF-8").getBytes("UTF-8"));
                out.close();
                con.getInputStream().close();
                final String location = con.getHeaderField("Location");
                final String pasteID = location.substring("http://paste.ubuntu.com/".length(), location.length() - 1);
                this.sender.sendMessage(ChatColor.GREEN + "Timings results can be viewed at http://www.spigotmc.org/go/timings?url=" + pasteID);
            }
            catch (IOException ex) {
                this.sender.sendMessage(ChatColor.RED + "Error pasting timings, check your console for more information");
                Bukkit.getServer().getLogger().log(Level.WARNING, "Could not paste timings", ex);
            }
        }
    }
}
