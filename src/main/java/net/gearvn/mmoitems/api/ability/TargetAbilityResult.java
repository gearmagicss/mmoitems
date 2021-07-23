// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.ability;

import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;

public class TargetAbilityResult extends AbilityResult
{
    private final LivingEntity target;
    
    public TargetAbilityResult(final AbilityData abilityData, final Player player, final LivingEntity livingEntity) {
        super(abilityData);
        this.target = ((livingEntity != null) ? livingEntity : MythicLib.plugin.getVersion().getWrapper().rayTrace(player, 50.0, entity -> MMOUtils.canDamage(player, entity)).getHit());
    }
    
    public LivingEntity getTarget() {
        return this.target;
    }
    
    @Override
    public boolean isSuccessful() {
        return this.target != null;
    }
}
