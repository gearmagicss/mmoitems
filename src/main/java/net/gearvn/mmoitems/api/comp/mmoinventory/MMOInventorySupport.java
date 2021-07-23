// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmoinventory;

import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.comp.mmoinventory.stat.AccessorySet;
import net.Indyuce.mmoitems.MMOItems;

public class MMOInventorySupport
{
    public MMOInventorySupport() {
        MMOItems.plugin.getStats().register(new AccessorySet());
    }
}
