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
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Targeted_Fireball extends Ability
{
    public Targeted_Fireball() {
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
            final Location loc = cachedStats.getPlayer().getLocation().add(0.0, 1.3, 0.0);
            final /* synthetic */ LivingEntity val$target = ((TargetAbilityResult)abilityResult).getTarget();
            
            public void run() {
                ++this.j;
                if (this.val$target.isDead() || !this.val$target.getWorld().equals(this.loc.getWorld()) || this.j > 200) {
                    this.cancel();
                    return;
                }
                final Vector normalize = this.val$target.getLocation().add(0.0, this.val$target.getHeight() / 2.0, 0.0).subtract(this.loc).toVector().normalize();
                this.loc.add(normalize.multiply(0.6));
                this.loc.setDirection(normalize);
                for (double n = 0.0; n < 6.283185307179586; n += 0.5235987755982988) {
                    final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(Math.cos(n), Math.sin(n), 0.0), this.loc);
                    this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 0, rotateFunc.getX(), rotateFunc.getY(), rotateFunc.getZ(), 0.06);
                }
                this.loc.getWorld().playSound(this.loc, VersionSound.BLOCK_NOTE_BLOCK_HAT.toSound(), 1.0f, 1.0f);
                if (this.val$target.getLocation().add(0.0, this.val$target.getHeight() / 2.0, 0.0).distanceSquared(this.loc) < 1.3) {
                    this.loc.getWorld().spawnParticle(Particle.LAVA, this.loc, 8);
                    this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 32, 0.0, 0.0, 0.0, 0.1);
                    this.loc.getWorld().playSound(this.loc, Sound.ENTITY_BLAZE_HURT, 2.0f, 1.0f);
                    this.val$target.setFireTicks((int)(this.val$target.getFireTicks() + abilityResult.getModifier("ignite") * 20.0));
                    new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), this.val$target);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
