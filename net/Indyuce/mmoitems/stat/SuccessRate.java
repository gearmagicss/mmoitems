// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class SuccessRate extends DoubleStat implements GemStoneStat
{
    public SuccessRate() {
        super("SUCCESS_RATE", Material.EMERALD, "Success Rate", new String[] { "The chance of your gem/skin to successfully", "apply onto an item. This value is 100%", "by default. If it is not successfully", "applied, the gem/skin will be lost." }, new String[] { "gem_stone", "skin" }, new Material[0]);
    }
}
