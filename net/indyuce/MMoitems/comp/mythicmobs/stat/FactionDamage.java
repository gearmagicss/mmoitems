// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs.stat;

import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class FactionDamage extends DoubleStat
{
    public FactionDamage(final String s) {
        super("FACTION_DAMAGE_" + s.toUpperCase(), VersionMaterial.RED_DYE.toMaterial(), s + " Faction Damage", new String[] { "Deals additional damage to mobs", "from the " + s + " faction in %." }, new String[] { "!block", "all" }, new Material[0]);
    }
}
