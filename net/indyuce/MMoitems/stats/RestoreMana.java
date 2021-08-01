// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.PlayerConsumable;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RestoreMana extends DoubleStat implements PlayerConsumable
{
    public RestoreMana() {
        super("RESTORE_MANA", VersionMaterial.LAPIS_LAZULI.toMaterial(), "Restore Mana", new String[] { "The amount of mana", "your consumable restores." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public void onConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        if (!volatileMMOItem.hasData(ItemStats.RESTORE_MANA)) {
            return;
        }
        final DoubleData doubleData = (DoubleData)volatileMMOItem.getData(ItemStats.RESTORE_MANA);
        if (doubleData.getValue() != 0.0) {
            PlayerData.get((OfflinePlayer)player).getRPG().giveMana(doubleData.getValue());
        }
    }
}
