// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.ability;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.util.Vector;

public class VectorAbilityResult extends AbilityResult
{
    private final Vector target;
    
    public VectorAbilityResult(final AbilityData abilityData, final Player player, final LivingEntity livingEntity) {
        super(abilityData);
        this.target = this.getTargetDirection(player, livingEntity);
    }
    
    public Vector getTarget() {
        return this.target;
    }
    
    @Override
    public boolean isSuccessful() {
        return true;
    }
    
    protected Vector getTargetDirection(final Player player, final LivingEntity livingEntity) {
        return (livingEntity == null) ? player.getEyeLocation().getDirection() : livingEntity.getLocation().add(0.0, livingEntity.getHeight() / 2.0, 0.0).subtract(player.getLocation().add(0.0, 1.3, 0.0)).toVector().normalize();
    }
}
