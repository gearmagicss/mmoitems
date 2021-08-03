// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util.message;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;

public class FFPMMOItems extends FriendlyFeedbackPalette
{
    @NotNull
    static FFPMMOItems instance;
    
    FFPMMOItems() {
    }
    
    @NotNull
    public static FFPMMOItems get() {
        return FFPMMOItems.instance;
    }
    
    @NotNull
    public String getBodyFormat() {
        return "§x§a§5§b§5§a§7";
    }
    
    @NotNull
    public String consoleBodyFormat() {
        return ChatColor.GRAY.toString();
    }
    
    @NotNull
    public String getExampleFormat() {
        return "§x§e§0§f§5§9§3";
    }
    
    @NotNull
    public String consoleExampleFormat() {
        return ChatColor.YELLOW.toString();
    }
    
    @NotNull
    public String getInputFormat() {
        return "§x§7§d§c§7§5§8";
    }
    
    @NotNull
    public String consoleInputFormat() {
        return ChatColor.GREEN.toString();
    }
    
    @NotNull
    public String getResultFormat() {
        return "§x§5§c§e§0§0§4";
    }
    
    @NotNull
    public String consoleResultFormat() {
        return ChatColor.GREEN.toString();
    }
    
    @NotNull
    public String getSuccessFormat() {
        return "§x§2§5§f§7§c§6";
    }
    
    @NotNull
    public String consoleSuccessFormat() {
        return ChatColor.AQUA.toString();
    }
    
    @NotNull
    public String getFailureFormat() {
        return "§x§f§f§6§0§2§6";
    }
    
    @NotNull
    public String consoleFailureFormat() {
        return ChatColor.RED.toString();
    }
    
    @NotNull
    public String getRawPrefix() {
        return "§8[§eMMOItems#s§8] ";
    }
    
    @NotNull
    public String getRawPrefixConsole() {
        return "§8[§eMMOItems#s§8] ";
    }
    
    @NotNull
    public String getSubdivisionFormat() {
        return "§x§c§c§a§3§3§3§o";
    }
    
    @NotNull
    public String consoleSubdivisionFormat() {
        return "§6§o";
    }
    
    static {
        FFPMMOItems.instance = new FFPMMOItems();
    }
}
