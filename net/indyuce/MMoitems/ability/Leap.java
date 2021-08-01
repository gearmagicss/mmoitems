// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.util.Vector;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Leap extends Ability
{
    public Leap() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("force", 1.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData, ((LivingEntity)cachedStats.getPlayer()).isOnGround());
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDER_DRAGON_FLAP.toSound(), 1.0f, 0.0f);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, cachedStats.getPlayer().getLocation(), 16, 0.0, 0.0, 0.1);
        final Vector multiply = cachedStats.getPlayer().getEyeLocation().getDirection().multiply(2.0 * abilityResult.getModifier("force"));
        multiply.setY(multiply.getY() / 2.0);
        cachedStats.getPlayer().setVelocity(multiply);
        new BukkitRunnable() {
            double ti = 0.0;
            
            public void run() {
                ++this.ti;
                if (this.ti > 20.0) {
                    this.cancel();
                }
                cachedStats.getPlayer().getWorld().spawnParticle(Particle.CLOUD, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0);
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
