// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.util.NoClipItem;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Item_Bomb extends Ability implements Listener
{
    public Item_Bomb() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 7.0);
        this.addModifier("radius", 6.0);
        this.addModifier("slow-duration", 4.0);
        this.addModifier("slow-amplifier", 1.0);
        this.addModifier("cooldown", 15.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final ItemStack clone = cachedStats.getPlayer().getInventory().getItemInMainHand().clone();
        if (clone == null || clone.getType() == Material.AIR) {
            itemAttackResult.setSuccessful(false);
            return;
        }
        final NoClipItem noClipItem = new NoClipItem(cachedStats.getPlayer().getLocation().add(0.0, 1.2, 0.0), clone);
        noClipItem.getEntity().setVelocity(((VectorAbilityResult)abilityResult).getTarget().multiply(1.3));
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2.0f, 0.0f);
        new BukkitRunnable() {
            int j = 0;
            
            public void run() {
                if (this.j++ > 40) {
                    final double modifier = abilityResult.getModifier("radius");
                    final double modifier2 = abilityResult.getModifier("damage");
                    final double modifier3 = abilityResult.getModifier("slow-duration");
                    final double modifier4 = abilityResult.getModifier("slow-amplifier");
                    for (final Entity entity : noClipItem.getEntity().getNearbyEntities(modifier, modifier, modifier)) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(modifier2, new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            ((LivingEntity)entity).removePotionEffect(PotionEffectType.SLOW);
                            ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(modifier3 * 20.0), (int)modifier4));
                        }
                    }
                    noClipItem.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, noClipItem.getEntity().getLocation(), 24, 2.0, 2.0, 2.0, 0.0);
                    noClipItem.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, noClipItem.getEntity().getLocation(), 48, 0.0, 0.0, 0.0, 0.2);
                    noClipItem.getEntity().getWorld().playSound(noClipItem.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.0f);
                    noClipItem.close();
                    this.cancel();
                    return;
                }
                noClipItem.getEntity().getWorld().spawnParticle(Particle.SMOKE_LARGE, noClipItem.getEntity().getLocation().add(0.0, 0.2, 0.0), 0);
                noClipItem.getEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, noClipItem.getEntity().getLocation().add(0.0, 0.2, 0.0), 1, 0.0, 0.0, 0.0, 0.1);
                noClipItem.getEntity().getWorld().playSound(noClipItem.getEntity().getLocation(), VersionSound.BLOCK_NOTE_BLOCK_HAT.toSound(), 2.0f, (float)(0.5 + this.j / 40.0 * 1.5));
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
