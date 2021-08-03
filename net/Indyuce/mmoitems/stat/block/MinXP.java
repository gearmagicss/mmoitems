// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.block;

import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class MinXP extends DoubleStat
{
    public MinXP() {
        super("MIN_XP", Material.EXPERIENCE_BOTTLE, "Minimum XP", new String[] { "The minimum xp you will receive", "for breaking this custom block." }, new String[] { "block" }, new Material[0]);
    }
}
