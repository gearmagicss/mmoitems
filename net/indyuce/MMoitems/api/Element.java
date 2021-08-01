// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import org.bukkit.util.Consumer;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.util.Vector;
import org.bukkit.Sound;
import java.util.Iterator;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.listener.ElementListener;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum Element
{
    FIRE(Material.BLAZE_POWDER, ChatColor.DARK_RED, new ElementParticle(Particle.FLAME, 0.05000000074505806, 8), (p0, itemAttackResult, livingEntity, n, n4) -> {
        livingEntity.getWorld().spawnParticle(Particle.LAVA, livingEntity.getLocation().add(0.0, livingEntity.getHeight() / 2.0, 0.0), 14);
        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_BLAZE_HURT, 2.0f, 0.8f);
        livingEntity.setFireTicks((int)(n * 2.0));
        itemAttackResult.addDamage(n4);
        return;
    }, 19, 25), 
    ICE(VersionMaterial.SNOWBALL.toMaterial(), ChatColor.AQUA, new ElementParticle(Particle.BLOCK_CRACK, 0.07f, 16, Material.ICE), (p0, itemAttackResult2, livingEntity2, n2, n5) -> {
        new BukkitRunnable() {
            double y;
            final Location loc;
            final /* synthetic */ LivingEntity val$target;
            
            {
                this.y = 0.0;
                this.loc = this.val$target.getLocation();
            }
            
            public void run() {
                for (int i = 0; i < 3; ++i) {
                    final double y = this.y + 0.07;
                    this.y = y;
                    if (y >= 3.0) {
                        this.cancel();
                    }
                    for (double n = 0.0; n < 6.283185307179586; n += 2.0943951023931953) {
                        this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc.clone().add(Math.cos(this.y * 3.141592653589793 + n) * (3.0 - this.y) / 2.5, this.y / 1.1, Math.sin(this.y * 3.141592653589793 + n) * (3.0 - this.y) / 2.5), 1, (Object)new Particle.DustOptions(Color.WHITE, 1.0f));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        livingEntity2.getWorld().playSound(livingEntity2.getLocation(), Sound.BLOCK_GLASS_BREAK, 2.0f, 0.0f);
        livingEntity2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(n2 * 1.5), 5));
        itemAttackResult2.addDamage(n5);
        return;
    }, 20, 24), 
    WIND(Material.FEATHER, ChatColor.GRAY, new ElementParticle(Particle.EXPLOSION_NORMAL, 0.05999999865889549, 8), (cachedStats, itemAttackResult3, livingEntity3, p3, n6) -> {
        livingEntity3.getWorld().playSound(livingEntity3.getLocation(), VersionSound.ENTITY_ENDER_DRAGON_GROWL.toSound(), 2.0f, 2.0f);
        livingEntity3.getLocation().subtract(cachedStats.getPlayer().getLocation()).toVector().normalize().multiply(1.7).setY(0.5);
        livingEntity3.setVelocity(vector);
        livingEntity3.getNearbyEntities(3.0, 1.0, 3.0).iterator();
        while (iterator.hasNext()) {
            entity = iterator.next();
            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                entity.playEffect(EntityEffect.HURT);
                entity.setVelocity(vector);
            }
        }
        itemAttackResult3.addDamage(n6);
        while (n9 < 6.283185307179586) {
            livingEntity3.getWorld().spawnParticle(Particle.CLOUD, livingEntity3.getLocation().add(0.0, livingEntity3.getHeight() / 2.0, 0.0), 0, Math.cos(n9), 0.01, Math.sin(n9), 0.15);
            n9 += 0.19634954084936207;
        }
        return;
    }, 28, 34), 
    EARTH(VersionMaterial.OAK_SAPLING.toMaterial(), ChatColor.GREEN, new ElementParticle(Particle.BLOCK_CRACK, 0.05f, 24, Material.DIRT), (cachedStats2, itemAttackResult4, livingEntity4, p3, n7) -> {
        livingEntity4.getWorld().playSound(livingEntity4.getLocation(), Sound.BLOCK_GRASS_BREAK, 2.0f, 0.0f);
        livingEntity4.getWorld().spawnParticle(Particle.BLOCK_CRACK, livingEntity4.getLocation().add(0.0, 0.1, 0.0), 64, 1.0, 0.0, 1.0, (Object)Material.DIRT.createBlockData());
        itemAttackResult4.addDamage(n7);
        livingEntity4.setVelocity(new Vector(0, 1, 0));
        livingEntity4.getNearbyEntities(3.0, 1.0, 3.0).iterator();
        while (iterator2.hasNext()) {
            entity2 = iterator2.next();
            if (MMOUtils.canDamage(cachedStats2.getPlayer(), entity2)) {
                entity2.setVelocity(new Vector(0, 1, 0));
            }
        }
        return;
    }, 29, 33), 
    THUNDER(VersionMaterial.GUNPOWDER.toMaterial(), ChatColor.YELLOW, new ElementParticle(Particle.FIREWORKS_SPARK, 0.05000000074505806, 8), (cachedStats3, itemAttackResult5, livingEntity5, n3, n8) -> {
        livingEntity5.getWorld().playSound(livingEntity5.getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST.toSound(), 2.0f, 0.0f);
        livingEntity5.getNearbyEntities(3.0, 2.0, 3.0).iterator();
        while (iterator3.hasNext()) {
            entity3 = iterator3.next();
            if (MMOUtils.canDamage(cachedStats3.getPlayer(), entity3)) {
                new ItemAttackResult(itemAttackResult5.getDamage() * n3 / 100.0, new DamageType[] { DamageType.WEAPON });
                itemAttackResult6.damage(cachedStats3.getPlayer(), (LivingEntity)entity3);
            }
        }
        itemAttackResult5.addDamage(n8);
        while (n10 < 6.283185307179586) {
            livingEntity5.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, livingEntity5.getLocation().add(0.0, livingEntity5.getHeight() / 2.0, 0.0), 0, Math.cos(n10), 0.01, Math.sin(n10), 0.18);
            n10 += 0.19634954084936207;
        }
        return;
    }, 30, 32), 
    WATER(VersionMaterial.LILY_PAD.toMaterial(), ChatColor.BLUE, new ElementParticle(Particle.BLOCK_CRACK, 0.07f, 32, Material.WATER), (p0, p1, livingEntity6, p3, p4) -> {
        ElementListener.weaken((Entity)livingEntity6);
        new BukkitRunnable() {
            double step;
            final Location loc;
            final /* synthetic */ LivingEntity val$target;
            
            {
                this.step = 1.5707963267948966;
                this.loc = this.val$target.getLocation();
            }
            
            public void run() {
                final double step = this.step - 0.10471975511965977;
                this.step = step;
                if (step <= 0.0) {
                    this.cancel();
                }
                for (double n = 0.0; n < 6.283185307179586; n += 0.19634954084936207) {
                    this.loc.getWorld().spawnParticle(Particle.WATER_DROP, this.loc.clone().add(Math.cos(n) * Math.sin(this.step) * 2.0, Math.cos(this.step) * 2.0, 2.0 * Math.sin(n) * Math.sin(this.step)), 0);
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        return;
    }, 37, 43), 
    LIGHTNESS(Material.GLOWSTONE_DUST, ChatColor.WHITE, new ElementParticle(Particle.BLOCK_CRACK, 0.07f, 32, Material.WHITE_WOOL), (p0, p1, p2, p3, p4) -> {}, 38, 42), 
    DARKNESS(Material.COAL, ChatColor.DARK_GRAY, new ElementParticle(Particle.BLOCK_CRACK, 0.07f, 32, Material.COAL_BLOCK), (p0, p1, p2, p3, p4) -> {}, 39, 41);
    
    private final ItemStack item;
    private final String name;
    private final ChatColor color;
    private final ElementParticle particle;
    private final ElementHandler handler;
    private final int damageGuiSlot;
    private final int defenseGuiSlot;
    
    private Element(final Material material, final ChatColor color, final ElementParticle particle, final ElementHandler handler, final int damageGuiSlot, final int defenseGuiSlot) {
        this.item = new ItemStack(material);
        this.name = MMOUtils.caseOnWords(this.name().toLowerCase());
        this.color = color;
        this.particle = particle;
        this.handler = handler;
        this.damageGuiSlot = damageGuiSlot;
        this.defenseGuiSlot = defenseGuiSlot;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ChatColor getPrefix() {
        return this.color;
    }
    
    public ElementParticle getParticle() {
        return this.particle;
    }
    
    public ElementHandler getHandler() {
        return this.handler;
    }
    
    public int getDamageGuiSlot() {
        return this.damageGuiSlot;
    }
    
    public int getDefenseGuiSlot() {
        return this.defenseGuiSlot;
    }
    
    private static /* synthetic */ Element[] $values() {
        return new Element[] { Element.FIRE, Element.ICE, Element.WIND, Element.EARTH, Element.THUNDER, Element.WATER, Element.LIGHTNESS, Element.DARKNESS };
    }
    
    static {
        $VALUES = $values();
    }
    
    public static class ElementParticle
    {
        public final Consumer<Entity> display;
        
        public ElementParticle(final Particle particle, final double n, final int n2) {
            this.display = (Consumer<Entity>)(entity -> entity.getWorld().spawnParticle(particle, entity.getLocation().add(0.0, entity.getHeight() / 2.0, 0.0), n2, 0.0, 0.0, 0.0, n));
        }
        
        public ElementParticle(final Particle particle, final float n, final int n2, final Material material) {
            this.display = (Consumer<Entity>)(entity -> entity.getWorld().spawnParticle(particle, entity.getLocation().add(0.0, entity.getHeight() / 2.0, 0.0), n2, 0.0, 0.0, 0.0, (double)n, (Object)material.createBlockData()));
        }
        
        public void displayParticle(final Entity entity) {
            this.display.accept((Object)entity);
        }
    }
    
    public interface ElementHandler
    {
        void elementAttack(final PlayerStats.CachedStats p0, final ItemAttackResult p1, final LivingEntity p2, final double p3, final double p4);
    }
}
