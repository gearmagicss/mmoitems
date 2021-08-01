// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Frog_Mode extends Ability implements Listener
{
    public Frog_Mode() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 20.0);
        this.addModifier("jump-force", 1.0);
        this.addModifier("speed", 1.0);
        this.addModifier("cooldown", 50.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            int j = 0;
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration") * 20.0;
            final /* synthetic */ double val$xz = abilityResult.getModifier("speed");
            final /* synthetic */ double val$y = abilityResult.getModifier("jump-force");
            
            public void run() {
                ++this.j;
                if (this.j > this.val$duration) {
                    this.cancel();
                }
                if (cachedStats.getPlayer().getLocation().getBlock().getType() == Material.WATER) {
                    cachedStats.getPlayer().setVelocity(cachedStats.getPlayer().getEyeLocation().getDirection().setY(0).normalize().multiply(0.8 * this.val$xz).setY(0.5 / this.val$xz * this.val$y));
                    cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDER_DRAGON_FLAP.toSound(), 2.0f, 1.0f);
                    for (double n = 0.0; n < 6.283185307179586; n += 0.2617993877991494) {
                        cachedStats.getPlayer().getWorld().spawnParticle(Particle.CLOUD, cachedStats.getPlayer().getLocation(), 0, Math.cos(n), 0.0, Math.sin(n), 0.2);
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
