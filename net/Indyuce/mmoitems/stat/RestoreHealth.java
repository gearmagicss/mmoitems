// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.SelfConsumable;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RestoreHealth extends DoubleStat implements SelfConsumable
{
    public RestoreHealth() {
        super("RESTORE_HEALTH", VersionMaterial.RED_DYE.toMaterial(), "Health Restoration", new String[] { "Health given when consumed." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean onSelfConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        if (!volatileMMOItem.hasData(ItemStats.RESTORE_HEALTH)) {
            return false;
        }
        final DoubleData doubleData = (DoubleData)volatileMMOItem.getData(ItemStats.RESTORE_HEALTH);
        if (doubleData.getValue() != 0.0) {
            MMOUtils.heal((LivingEntity)player, doubleData.getValue());
            return true;
        }
        return false;
    }
}
