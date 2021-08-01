// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.Particle;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Random;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.interaction.weapon.Weapon;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import io.lumine.mythic.lib.api.stat.modifier.ModifierSource;

public enum TypeSet
{
    SLASHING(ModifierSource.MELEE_WEAPON, (cachedStats, obj, weapon, itemAttackResult) -> {
        if (!MMOItems.plugin.getConfig().getBoolean("item-ability.slashing.enabled") || cachedStats.getData().isOnCooldown(PlayerData.CooldownType.SET_TYPE_ATTACK)) {
            return;
        }
        else {
            cachedStats.getData().applyCooldown(PlayerData.CooldownType.SET_TYPE_ATTACK, MMOItems.plugin.getConfig().getDouble("item-ability.slashing.cooldown"));
            cachedStats.getPlayer().getLocation().clone().add(0.0, 1.3, 0.0);
            n = (location.getYaw() + 90.0f) / 180.0f * 3.141592653589793;
            a = -location.getPitch() / 180.0f * 3.141592653589793;
            while (n2 < 5.0) {
                while (n3 < 0.5235987755982988) {
                    location.getWorld().spawnParticle(Particle.CRIT, location.clone().add(Math.cos(n3 + n) * n2, Math.sin(a) * n2, Math.sin(n3 + n) * n2), 0);
                    n3 += 0.39269908169872414 / n2;
                }
                n2 += 0.3;
            }
            MMOUtils.getNearbyChunkEntities(location).iterator();
            while (iterator.hasNext()) {
                entity = iterator.next();
                if (entity.getLocation().distanceSquared(location) < 40.0 && cachedStats.getPlayer().getEyeLocation().getDirection().angle(entity.getLocation().subtract(cachedStats.getPlayer().getLocation()).toVector()) < 1.0471975511965976 && MMOUtils.canDamage(cachedStats.getPlayer(), entity) && !entity.equals(obj)) {
                    itemAttackResult.clone().multiplyDamage(0.4).applyEffectsAndDamage(cachedStats, weapon.getNBTItem(), (LivingEntity)entity);
                }
            }
            return;
        }
    }), 
    PIERCING(ModifierSource.MELEE_WEAPON, (cachedStats2, obj2, weapon2, itemAttackResult2) -> {
        if (!MMOItems.plugin.getConfig().getBoolean("item-ability.piercing.enabled") || cachedStats2.getData().isOnCooldown(PlayerData.CooldownType.SET_TYPE_ATTACK)) {
            return;
        }
        else {
            cachedStats2.getData().applyCooldown(PlayerData.CooldownType.SET_TYPE_ATTACK, MMOItems.plugin.getConfig().getDouble("item-ability.piercing.cooldown"));
            cachedStats2.getPlayer().getLocation().clone().add(0.0, 1.3, 0.0);
            n4 = (location2.getYaw() + 90.0f) / 180.0f * 3.141592653589793;
            a2 = -location2.getPitch() / 180.0f * 3.141592653589793;
            while (n5 < 5.0) {
                while (n6 < 0.2617993877991494) {
                    location2.getWorld().spawnParticle(Particle.CRIT, location2.clone().add(Math.cos(n6 + n4) * n5, Math.sin(a2) * n5, Math.sin(n6 + n4) * n5), 0);
                    n6 += 0.19634954084936207 / n5;
                }
                n5 += 0.3;
            }
            MMOUtils.getNearbyChunkEntities(location2).iterator();
            while (iterator2.hasNext()) {
                entity2 = iterator2.next();
                if (entity2.getLocation().distanceSquared(cachedStats2.getPlayer().getLocation()) < 40.0 && cachedStats2.getPlayer().getEyeLocation().getDirection().angle(entity2.getLocation().toVector().subtract(cachedStats2.getPlayer().getLocation().toVector())) < 0.17453292519943295 && MMOUtils.canDamage(cachedStats2.getPlayer(), entity2) && !entity2.equals(obj2)) {
                    itemAttackResult2.clone().multiplyDamage(0.4).applyEffectsAndDamage(cachedStats2, weapon2.getNBTItem(), (LivingEntity)entity2);
                }
            }
            return;
        }
    }), 
    BLUNT(ModifierSource.MELEE_WEAPON, (cachedStats3, obj3, weapon3, itemAttackResult3) -> {
        random = new Random();
        n7 = 0.7f + random.nextFloat() * 0.19999999f;
        if (MMOItems.plugin.getConfig().getBoolean("item-ability.blunt.aoe.enabled") && !cachedStats3.getData().isOnCooldown(PlayerData.CooldownType.SPECIAL_ATTACK)) {
            cachedStats3.getData().applyCooldown(PlayerData.CooldownType.SPECIAL_ATTACK, MMOItems.plugin.getConfig().getDouble("item-ability.blunt.aoe.cooldown"));
            obj3.getWorld().playSound(obj3.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.6f, n7);
            obj3.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, obj3.getLocation().add(0.0, 1.0, 0.0), 0);
            cachedStats3.getStat(ItemStats.BLUNT_POWER);
            if (n8 > 0.0) {
                n9 = weapon3.getValue(cachedStats3.getStat(ItemStats.BLUNT_RATING), MMOItems.plugin.getConfig().getDouble("default.blunt-rating")) / 100.0;
                obj3.getNearbyEntities(n8, n8, n8).iterator();
                while (iterator3.hasNext()) {
                    entity3 = iterator3.next();
                    if (MMOUtils.canDamage(cachedStats3.getPlayer(), entity3) && !entity3.equals(obj3)) {
                        itemAttackResult3.clone().multiplyDamage(n9).applyEffectsAndDamage(cachedStats3, weapon3.getNBTItem(), (LivingEntity)entity3);
                    }
                }
            }
        }
        if (MMOItems.plugin.getConfig().getBoolean("item-ability.blunt.stun.enabled") && !cachedStats3.getData().isOnCooldown(PlayerData.CooldownType.SPECIAL_ATTACK) && random.nextDouble() < MMOItems.plugin.getConfig().getDouble("item-ability.blunt.stun.chance") / 100.0) {
            cachedStats3.getData().applyCooldown(PlayerData.CooldownType.SPECIAL_ATTACK, MMOItems.plugin.getConfig().getDouble("item-ability.blunt.stun.cooldown"));
            obj3.getWorld().playSound(obj3.getLocation(), VersionSound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR.toSound(), 1.0f, 2.0f);
            obj3.removePotionEffect(PotionEffectType.SLOW);
            obj3.removePotionEffect(PotionEffectType.BLINDNESS);
            obj3.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
            obj3.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(30.0 * MMOItems.plugin.getConfig().getDouble("item-ability.blunt.stun.power")), 1));
            obj3.getLocation();
            location3.setYaw((float)(location3.getYaw() + 2.0 * (random.nextDouble() - 0.5) * 90.0));
            location3.setPitch((float)(location3.getPitch() + 2.0 * (random.nextDouble() - 0.5) * 30.0));
        }
        return;
    }), 
    OFFHAND(ModifierSource.OTHER), 
    RANGE(ModifierSource.RANGED_WEAPON), 
    EXTRA(ModifierSource.OTHER);
    
    private final ModifierSource modifierSource;
    private final SetAttackHandler<PlayerStats.CachedStats, LivingEntity, Weapon, ItemAttackResult> attackHandler;
    private final String name;
    
    private TypeSet(final ModifierSource modifierSource) {
        this(modifierSource, null);
    }
    
    private TypeSet(final ModifierSource modifierSource, final SetAttackHandler<PlayerStats.CachedStats, LivingEntity, Weapon, ItemAttackResult> attackHandler) {
        this.attackHandler = attackHandler;
        this.modifierSource = modifierSource;
        this.name = MMOUtils.caseOnWords(this.name().toLowerCase());
    }
    
    public ModifierSource getModifierSource() {
        return this.modifierSource;
    }
    
    public boolean hasAttackEffect() {
        return this.attackHandler != null;
    }
    
    public void applyAttackEffect(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final Weapon weapon, final ItemAttackResult itemAttackResult) {
        this.attackHandler.apply(cachedStats, livingEntity, weapon, itemAttackResult);
    }
    
    public String getName() {
        return this.name;
    }
    
    private static /* synthetic */ TypeSet[] $values() {
        return new TypeSet[] { TypeSet.SLASHING, TypeSet.PIERCING, TypeSet.BLUNT, TypeSet.OFFHAND, TypeSet.RANGE, TypeSet.EXTRA };
    }
    
    static {
        $VALUES = $values();
    }
    
    @FunctionalInterface
    interface SetAttackHandler<A, B, C, D>
    {
        void apply(final A p0, final B p1, final C p2, final D p3);
    }
}
