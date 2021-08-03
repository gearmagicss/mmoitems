// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.parse.placeholders;

import org.bukkit.OfflinePlayer;

public class DefaultPlaceholderParser implements PlaceholderParser
{
    @Override
    public String parse(final OfflinePlayer offlinePlayer, final String s) {
        return s.replace("%player%", offlinePlayer.getName());
    }
}
