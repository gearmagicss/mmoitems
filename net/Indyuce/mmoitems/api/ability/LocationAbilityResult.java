// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.ability;

import org.bukkit.Material;
import java.util.Set;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.Location;

public class LocationAbilityResult extends AbilityResult
{
    private final Location target;
    
    public LocationAbilityResult(final AbilityData abilityData, final Player player, final LivingEntity livingEntity) {
        super(abilityData);
        this.target = this.getTargetLocation(player, livingEntity);
    }
    
    public Location getTarget() {
        return this.target;
    }
    
    @Override
    public boolean isSuccessful() {
        return this.target != null;
    }
    
    private Location getTargetLocation(final Player player, final LivingEntity livingEntity) {
        if (livingEntity != null) {
            return livingEntity.getLocation();
        }
        final Location location = player.getTargetBlock((Set)null, 50).getLocation();
        return (location.getBlock().getType() == Material.AIR) ? null : location.add(0.5, 1.0, 0.5);
    }
}
