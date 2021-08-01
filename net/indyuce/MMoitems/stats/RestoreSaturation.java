// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.PlayerConsumable;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RestoreSaturation extends DoubleStat implements PlayerConsumable
{
    public RestoreSaturation() {
        super("RESTORE_SATURATION", Material.GOLDEN_CARROT, "Saturation Restoration", new String[] { "Saturation given when consumed." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public void onConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        int ceil = 6;
        if (volatileMMOItem.hasData(ItemStats.RESTORE_SATURATION)) {
            ceil = SilentNumbers.ceil(((DoubleData)volatileMMOItem.getData(ItemStats.RESTORE_SATURATION)).getValue());
        }
        if (ceil != 0) {
            MMOUtils.saturate(player, ceil);
        }
    }
}
