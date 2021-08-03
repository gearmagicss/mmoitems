// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.holograms;

import java.util.Iterator;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Random;

public abstract class HologramSupport
{
    private static final Random random;
    
    public HologramSupport() {
        if (MMOItems.plugin.getConfig().getBoolean("game-indicators.damage.enabled")) {
            Bukkit.getPluginManager().registerEvents((Listener)new Listener() {
                @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
                public void a(final EntityDamageEvent entityDamageEvent) {
                    final Entity entity = entityDamageEvent.getEntity();
                    if (!(entity instanceof LivingEntity) || entityDamageEvent.getEntity() instanceof ArmorStand || entityDamageEvent.getDamage() <= 0.0) {
                        return;
                    }
                    if (entity instanceof Player && HologramSupport.this.isVanished((Player)entity)) {
                        return;
                    }
                    HologramSupport.this.displayIndicator(entity, MMOItems.plugin.getLanguage().damageIndicatorFormat.replace("#", MMOItems.plugin.getLanguage().damageIndicatorDecimalFormat.format(entityDamageEvent.getFinalDamage())));
                }
            }, (Plugin)MMOItems.plugin);
        }
        if (MMOItems.plugin.getConfig().getBoolean("game-indicators.heal.enabled")) {
            Bukkit.getPluginManager().registerEvents((Listener)new Listener() {
                @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
                public void a(final EntityRegainHealthEvent entityRegainHealthEvent) {
                    final Entity entity = entityRegainHealthEvent.getEntity();
                    if (!(entity instanceof LivingEntity) || entityRegainHealthEvent.getAmount() <= 0.0 || ((LivingEntity)entity).getHealth() >= ((LivingEntity)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
                        return;
                    }
                    if (entity instanceof Player && HologramSupport.this.isVanished((Player)entity)) {
                        return;
                    }
                    HologramSupport.this.displayIndicator(entity, MMOItems.plugin.getLanguage().healIndicatorFormat.replace("#", MMOItems.plugin.getLanguage().healIndicatorDecimalFormat.format(entityRegainHealthEvent.getAmount())));
                }
            }, (Plugin)MMOItems.plugin);
        }
    }
    
    public void displayIndicator(final Entity entity, final String s) {
        this.displayIndicator(entity.getLocation().add((HologramSupport.random.nextDouble() - 0.5) * 1.2, entity.getHeight() * 0.75, (HologramSupport.random.nextDouble() - 0.5) * 1.2), s, (entity instanceof Player) ? entity : null);
    }
    
    public abstract void displayIndicator(final Location p0, final String p1, final Player p2);
    
    private boolean isVanished(final Player player) {
        final Iterator<MetadataValue> iterator = player.getMetadata("vanished").iterator();
        while (iterator.hasNext()) {
            if (iterator.next().asBoolean()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        random = new Random();
    }
}
