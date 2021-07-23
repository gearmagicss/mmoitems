// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.Sound;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Magma_Fissure extends Ability
{
    public Magma_Fissure() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
        this.addModifier("ignite", 4.0);
        this.addModifier("damage", 4.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new TargetAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            int j = 0;
            final Location loc = cachedStats.getPlayer().getLocation().add(0.0, 0.2, 0.0);
            final /* synthetic */ LivingEntity val$target = ((TargetAbilityResult)abilityResult).getTarget();
            
            public void run() {
                ++this.j;
                if (this.val$target.isDead() || !this.val$target.getWorld().equals(this.loc.getWorld()) || this.j > 200) {
                    this.cancel();
                    return;
                }
                this.loc.add(this.val$target.getLocation().add(0.0, 0.2, 0.0).subtract(this.loc).toVector().normalize().multiply(0.6));
                this.loc.getWorld().spawnParticle(Particle.LAVA, this.loc, 2, 0.2, 0.0, 0.2, 0.0);
                this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 2, 0.2, 0.0, 0.2, 0.0);
                this.loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, this.loc, 2, 0.2, 0.0, 0.2, 0.0);
                this.loc.getWorld().playSound(this.loc, VersionSound.BLOCK_NOTE_BLOCK_HAT.toSound(), 1.0f, 1.0f);
                if (this.val$target.getLocation().distanceSquared(this.loc) < 1.0) {
                    this.loc.getWorld().playSound(this.loc, Sound.ENTITY_BLAZE_HURT, 2.0f, 1.0f);
                    this.val$target.setFireTicks((int)(this.val$target.getFireTicks() + abilityResult.getModifier("ignite") * 20.0));
                    new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), this.val$target);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
