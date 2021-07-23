// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.enchantments.Enchantment;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Projectile;
import net.Indyuce.mmoitems.api.ArrowParticles;
import org.bukkit.entity.Arrow;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ProjectileData;
import java.util.WeakHashMap;
import java.util.Map;
import org.bukkit.event.Listener;

public class EntityManager implements Listener
{
    private final Map<Integer, Object[]> entities;
    private final WeakHashMap<Integer, ProjectileData> projectiles;
    
    public EntityManager() {
        this.entities = new HashMap<Integer, Object[]>();
        this.projectiles = new WeakHashMap<Integer, ProjectileData>();
    }
    
    public void registerCustomProjectile(final NBTItem nbtItem, final PlayerStats.CachedStats cachedStats, final Entity entity, final boolean b) {
        this.registerCustomProjectile(nbtItem, cachedStats, entity, b, 1.0);
    }
    
    public void registerCustomProjectile(final NBTItem nbtItem, final PlayerStats.CachedStats cachedStats, final Entity entity, final boolean b, final double n) {
        final double stat = cachedStats.getStat(ItemStats.ATTACK_DAMAGE);
        cachedStats.setStat(ItemStats.ATTACK_DAMAGE, ((stat == 0.0) ? 7.0 : stat) * n);
        if (entity instanceof Arrow && nbtItem.hasTag("MMOITEMS_ARROW_PARTICLES")) {
            new ArrowParticles((Arrow)entity, nbtItem);
        }
        this.projectiles.put(entity.getEntityId(), new ProjectileData(nbtItem, cachedStats, b));
    }
    
    public void registerCustomEntity(final Entity entity, final Object... array) {
        this.entities.put(entity.getEntityId(), array);
    }
    
    public boolean isCustomProjectile(final Projectile projectile) {
        return this.projectiles.containsKey(projectile.getEntityId());
    }
    
    public boolean isCustomEntity(final Entity entity) {
        return this.entities.containsKey(entity.getEntityId());
    }
    
    public ProjectileData getProjectileData(final Projectile projectile) {
        return this.projectiles.get(projectile.getEntityId());
    }
    
    public Object[] getEntityData(final Entity entity) {
        return this.entities.get(entity.getEntityId());
    }
    
    public void unregisterCustomProjectile(final Projectile projectile) {
        this.projectiles.remove(projectile.getEntityId());
    }
    
    public void unregisterCustomEntity(final Entity entity) {
        this.entities.remove(entity.getEntityId());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void a(final EntityDeathEvent entityDeathEvent) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)MMOItems.plugin, () -> this.unregisterCustomEntity((Entity)entityDeathEvent.getEntity()));
    }
    
    @EventHandler(ignoreCancelled = true)
    public void b(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Projectile) || !(entityDamageByEntityEvent.getEntity() instanceof LivingEntity) || entityDamageByEntityEvent.getEntity().hasMetadata("NPC")) {
            return;
        }
        final Projectile projectile = (Projectile)entityDamageByEntityEvent.getDamager();
        if (!this.isCustomProjectile(projectile)) {
            return;
        }
        final ProjectileData projectileData = this.getProjectileData(projectile);
        final LivingEntity livingEntity = (LivingEntity)entityDamageByEntityEvent.getEntity();
        final PlayerStats.CachedStats playerStats = projectileData.getPlayerStats();
        final ItemAttackResult applyOnHitEffects = new ItemAttackResult(projectileData.isCustomWeapon() ? playerStats.getStat(ItemStats.ATTACK_DAMAGE) : entityDamageByEntityEvent.getDamage(), new DamageType[] { DamageType.WEAPON, DamageType.PROJECTILE, DamageType.PHYSICAL }).applyOnHitEffects(playerStats, livingEntity);
        if (projectileData.isCustomWeapon()) {
            projectileData.applyPotionEffects(livingEntity);
            applyOnHitEffects.applyElementalEffects(playerStats, projectileData.getSourceItem(), livingEntity);
            if (projectile instanceof Arrow && projectileData.getSourceItem().getItem().hasItemMeta() && projectileData.getSourceItem().getItem().getItemMeta().getEnchants().containsKey(Enchantment.ARROW_DAMAGE)) {
                applyOnHitEffects.multiplyDamage(1.25 + 0.25 * projectileData.getSourceItem().getItem().getItemMeta().getEnchantLevel(Enchantment.ARROW_DAMAGE));
            }
        }
        entityDamageByEntityEvent.setDamage(applyOnHitEffects.getDamage());
        this.unregisterCustomProjectile(projectile);
    }
}
