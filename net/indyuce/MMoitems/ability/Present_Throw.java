// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.util.NoClipItem;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.lang.reflect.Field;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import com.mojang.authlib.properties.Property;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Present_Throw extends Ability
{
    private final ItemStack present;
    
    public Present_Throw() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.present = VersionMaterial.PLAYER_HEAD.toItem();
        this.addModifier("damage", 6.0);
        this.addModifier("radius", 4.0);
        this.addModifier("force", 1.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
        final ItemMeta itemMeta = this.present.getItemMeta();
        final GameProfile value = new GameProfile(UUID.randomUUID(), (String)null);
        value.getProperties().put((Object)"textures", (Object)new Property("textures", new String(Base64Coder.encodeLines(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/47e55fcc809a2ac1861da2a67f7f31bd7237887d162eca1eda526a7512a64910").getBytes()).getBytes())));
        try {
            final Field declaredField = itemMeta.getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            declaredField.set(itemMeta, value);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load the skull texture for the Present Throw item ability.");
        }
        this.present.setItemMeta(itemMeta);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("damage");
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        final NoClipItem noClipItem = new NoClipItem(cachedStats.getPlayer().getLocation().add(0.0, 1.2, 0.0), this.present);
        noClipItem.getEntity().setVelocity(cachedStats.getPlayer().getEyeLocation().getDirection().multiply(1.5 * abilityResult.getModifier("force")));
        final double n = noClipItem.getEntity().getVelocity().getX() / noClipItem.getEntity().getVelocity().getZ();
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 0.0f);
        new BukkitRunnable() {
            double ti = 0.0;
            
            public void run() {
                final double ti = this.ti;
                this.ti = ti + 1.0;
                if (ti > 70.0 || noClipItem.getEntity().isDead()) {
                    noClipItem.close();
                    this.cancel();
                }
                final double n = noClipItem.getEntity().getVelocity().getX() / noClipItem.getEntity().getVelocity().getZ();
                noClipItem.getEntity().getWorld().spawnParticle(Particle.SPELL_INSTANT, noClipItem.getEntity().getLocation().add(0.0, 0.1, 0.0), 0);
                if (noClipItem.getEntity().isOnGround() || Math.abs(n - n) > 0.1) {
                    noClipItem.getEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, noClipItem.getEntity().getLocation().add(0.0, 0.1, 0.0), 128, 0.0, 0.0, 0.0, 0.25);
                    noClipItem.getEntity().getWorld().playSound(noClipItem.getEntity().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_TWINKLE.toSound(), 2.0f, 1.5f);
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(noClipItem.getEntity().getLocation())) {
                        if (entity.getLocation().distanceSquared(noClipItem.getEntity().getLocation()) < pow && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        }
                    }
                    noClipItem.close();
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
