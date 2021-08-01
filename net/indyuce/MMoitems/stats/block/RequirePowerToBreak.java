// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.block;

import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class RequirePowerToBreak extends BooleanStat
{
    public RequirePowerToBreak() {
        super("REQUIRE_POWER_TO_BREAK", Material.BEDROCK, "Require Power to Break", new String[] { "If you need the required pickaxe", "power to break this custom block." }, new String[] { "block" }, new Material[0]);
    }
}
