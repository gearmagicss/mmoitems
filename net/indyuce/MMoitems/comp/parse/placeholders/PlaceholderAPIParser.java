// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.parse.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPIParser implements PlaceholderParser
{
    public PlaceholderAPIParser() {
        new MMOItemsPlaceholders().register();
    }
    
    @Override
    public String parse(final OfflinePlayer offlinePlayer, final String s) {
        return MythicLib.plugin.parseColors(PlaceholderAPI.setPlaceholders(offlinePlayer, s.replace("%player%", offlinePlayer.getName())));
    }
}
