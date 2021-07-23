// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.StringListData;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class RequiredBiomes extends StringListStat implements ItemRestriction, GemStoneStat
{
    public RequiredBiomes() {
        super("REQUIRED_BIOMES", Material.JUNGLE_SAPLING, "Required Biomes", new String[] { "The biome the player must be within", "for this item to activate." }, new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (!nbtItem.hasTag(this.getNBTPath())) {
            return true;
        }
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (nbtItem.hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), nbtItem, SupportedNBTTagValues.STRING));
        }
        final StringListData stringListData = (StringListData)this.getLoadedNBT(list);
        boolean b2 = false;
        if (stringListData != null) {
            final Iterator<String> iterator = stringListData.getList().iterator();
            while (iterator.hasNext()) {
                String s = iterator.next().toLowerCase().replace(" ", "_").replace("-", "_");
                if (s.startsWith("!")) {
                    b2 = true;
                    s = s.substring(1);
                }
                if (rpgPlayer.getPlayer().getLocation().getBlock().getBiome().getKey().getKey().contains(s)) {
                    return !b2;
                }
            }
            return b2;
        }
        return true;
    }
    
    @Override
    public boolean isDynamic() {
        return true;
    }
}
