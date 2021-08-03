// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs;

import java.util.Collection;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import java.util.ArrayList;
import io.lumine.xikage.mythicmobs.MythicMobs;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.comp.mythicmobs.stat.FactionDamage;
import net.Indyuce.mmoitems.MMOItems;

public class MythicMobsLoader
{
    public MythicMobsLoader() {
        try {
            final Iterator<String> iterator = this.getFactions().iterator();
            while (iterator.hasNext()) {
                MMOItems.plugin.getStats().register(new FactionDamage(iterator.next()));
            }
        }
        catch (NullPointerException ex) {}
    }
    
    private Set<String> getFactions() {
        final HashSet<String> set = new HashSet<String>();
        final ArrayList<MythicMob> list = new ArrayList<MythicMob>(MythicMobs.inst().getMobManager().getVanillaTypes());
        list.addAll((Collection<?>)MythicMobs.inst().getMobManager().getMobTypes());
        for (final MythicMob mythicMob : list) {
            if (mythicMob.hasFaction()) {
                set.add(mythicMob.getFaction());
            }
        }
        return set;
    }
}
