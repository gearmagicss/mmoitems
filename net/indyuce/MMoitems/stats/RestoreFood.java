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
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.PlayerConsumable;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RestoreFood extends DoubleStat implements PlayerConsumable
{
    public RestoreFood() {
        super("RESTORE_FOOD", VersionMaterial.PORKCHOP.toMaterial(), "Food Restoration", new String[] { "Food units given when consumed." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public void onConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        if (!volatileMMOItem.hasData(ItemStats.RESTORE_FOOD)) {
            return;
        }
        final DoubleData doubleData = (DoubleData)volatileMMOItem.getData(ItemStats.RESTORE_FOOD);
        if (doubleData.getValue() != 0.0) {
            MMOUtils.feed(player, SilentNumbers.ceil(doubleData.getValue()));
        }
    }
}
