// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ChooseStat;

public class GemUpgradeScaling extends ChooseStat implements GemStoneStat
{
    public static final String NEVER = "NEVER";
    public static final String HISTORIC = "HISTORIC";
    public static final String SUBSEQUENT = "SUBSEQUENT";
    public static String defaultValue;
    
    public GemUpgradeScaling() {
        super("GEM_UPGRADE_SCALING", VersionMaterial.LIME_DYE.toMaterial(), "Gem Upgrade Scaling", new String[] { "Gem stones add their stats to items, but you may also", "upgrade your items via crafting stations or consumables.", "", "§6Should this gem stone stats be affected by upgrading?" }, new String[] { "gem_stone" }, new Material[0]);
        final ArrayList<String> c = (ArrayList<String>)new ArrayList<Object>();
        Collections.addAll(c, new String[] { "SUBSEQUENT", "NEVER", "HISTORIC" });
        this.InitializeChooseableList(c);
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("SUBSEQUENT", "Gem stats scale by upgrading the item, but only after putting the gem in.");
        hashMap.put("NEVER", "Gem stats are never scaled by upgrading the item.");
        hashMap.put("HISTORIC", "Gem stats instantly upgrade to the current item level, and subsequently thereafter.");
        this.HintChooseableDefs(hashMap);
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringData(GemUpgradeScaling.defaultValue);
    }
    
    static {
        GemUpgradeScaling.defaultValue = "SUBSEQUENT";
    }
}
