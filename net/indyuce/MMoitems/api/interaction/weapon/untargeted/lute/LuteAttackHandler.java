// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute;

import net.Indyuce.mmoitems.api.util.SoundReader;
import org.bukkit.util.Vector;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.util.Random;

public interface LuteAttackHandler
{
    public static final Random random = new Random();
    
    void handle(final PlayerStats.CachedStats p0, final NBTItem p1, final double p2, final double p3, final Vector p4, final SoundReader p5);
}
