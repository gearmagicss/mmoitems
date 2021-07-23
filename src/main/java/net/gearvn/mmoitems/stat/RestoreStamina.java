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
import net.Indyuce.mmoitems.stat.type.SelfConsumable;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RestoreStamina extends DoubleStat implements SelfConsumable
{
    public RestoreStamina() {
        super("RESTORE_STAMINA", VersionMaterial.LIGHT_GRAY_DYE.toMaterial(), "Restore Stamina", new String[] { "The amount of stamina/power", "your consumable restores." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean onSelfConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        if (!volatileMMOItem.hasData(ItemStats.RESTORE_STAMINA)) {
            return false;
        }
        final DoubleData doubleData = (DoubleData)volatileMMOItem.getData(ItemStats.RESTORE_STAMINA);
        if (doubleData.getValue() != 0.0) {
            PlayerData.get((OfflinePlayer)player).getRPG().giveStamina(doubleData.getValue());
            return true;
        }
        return false;
    }
}
