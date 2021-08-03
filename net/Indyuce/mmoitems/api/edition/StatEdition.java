// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.edition;

import java.util.Iterator;
import net.Indyuce.mmoitems.comp.parse.StringInputParser;
import net.Indyuce.mmoitems.api.edition.input.ChatEdition;
import net.Indyuce.mmoitems.api.edition.input.AnvilGUI;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.gui.PluginInventory;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;

public class StatEdition implements Edition
{
    private final EditionInventory inv;
    private final ItemStat stat;
    private final Object[] info;
    
    public StatEdition(final EditionInventory inv, final ItemStat stat, final Object... info) {
        this.inv = inv;
        this.stat = stat;
        this.info = info;
    }
    
    @Override
    public PluginInventory getInventory() {
        return this.inv;
    }
    
    public ItemStat getStat() {
        return this.stat;
    }
    
    public Object[] getData() {
        return this.info;
    }
    
    @Override
    public void enable(final String... array) {
        this.inv.getPlayer().closeInventory();
        this.inv.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        for (int length = array.length, i = 0; i < length; ++i) {
            this.inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', array[i]));
        }
        this.inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Type 'cancel' to abort editing.");
        if (MMOItems.plugin.getConfig().getBoolean("anvil-text-input") && MythicLib.plugin.getVersion().isBelowOrEqual(new int[] { 1, 13 })) {
            new AnvilGUI(this);
            return;
        }
        new ChatEdition(this);
        this.inv.getPlayer().sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Item Edition", "See chat.", 10, 40, 10);
    }
    
    @Override
    public boolean processInput(String input) {
        final Iterator<StringInputParser> iterator = MMOItems.plugin.getStringInputParsers().iterator();
        while (iterator.hasNext()) {
            input = iterator.next().parseInput(this.inv.getPlayer(), input);
        }
        if (input.equals("cancel")) {
            this.inv.open();
            return true;
        }
        try {
            this.stat.whenInput(this.inv, input, this.info);
            return true;
        }
        catch (IllegalArgumentException ex) {
            this.inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean shouldGoBack() {
        return true;
    }
}
