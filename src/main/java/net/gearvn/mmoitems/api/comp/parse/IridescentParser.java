// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.parse;

import com.github.klyser8.iridescent.util.ColorUtil;
import org.bukkit.entity.Player;

public class IridescentParser implements StringInputParser
{
    @Override
    public String parseInput(final Player player, final String s) {
        return ColorUtil.colorMessage(player, s, false);
    }
}
