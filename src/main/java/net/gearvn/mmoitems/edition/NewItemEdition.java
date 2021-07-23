// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.edition;

import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.edition.input.ChatEdition;
import net.Indyuce.mmoitems.api.edition.input.AnvilGUI;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.gui.PluginInventory;
import net.Indyuce.mmoitems.gui.ItemBrowser;

public class NewItemEdition implements Edition
{
    private final ItemBrowser inv;
    
    public NewItemEdition(final ItemBrowser inv) {
        this.inv = inv;
    }
    
    @Override
    public PluginInventory getInventory() {
        return this.inv;
    }
    
    @Override
    public void enable(final String... array) {
        this.inv.getPlayer().closeInventory();
        this.inv.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        this.inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Write in the chat, the id of the new item.");
        this.inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Type 'cancel' to abort editing.");
        if (MMOItems.plugin.getConfig().getBoolean("anvil-text-input") && MythicLib.plugin.getVersion().isBelowOrEqual(new int[] { 1, 13 })) {
            new AnvilGUI(this);
            return;
        }
        new ChatEdition(this);
        this.inv.getPlayer().sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Item Creation", "See chat.", 10, 40, 10);
    }
    
    @Override
    public boolean processInput(final String s) {
        if (s.equals("cancel")) {
            return true;
        }
        Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> Bukkit.dispatchCommand((CommandSender)this.inv.getPlayer(), "mmoitems create " + this.inv.getType().getId() + " " + s.toUpperCase().replace(" ", "_").replace("-", "_")));
        return true;
    }
    
    @Override
    public boolean shouldGoBack() {
        return false;
    }
}
