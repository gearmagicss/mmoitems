// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import java.util.HashMap;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.Particle;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Entity;
import java.util.Map;
import org.bukkit.event.Listener;

public class ElementListener implements Listener
{
    private static final Map<Integer, Long> WATER_WEAKNESS;
    private static final long WATER_WEAKNESS_DURATION = 6000L;
    private static final double WATER_WEAKNESS_DAMAGE_INCREASE = 0.3;
    
    public static void weaken(final Entity entity) {
        ElementListener.WATER_WEAKNESS.put(entity.getEntityId(), System.currentTimeMillis());
    }
    
    boolean isWeakened(final Entity entity) {
        return ElementListener.WATER_WEAKNESS.containsKey(entity.getEntityId()) && ElementListener.WATER_WEAKNESS.get(entity.getEntityId()) + 6000L > System.currentTimeMillis();
    }
    
    void flush() {
        ElementListener.WATER_WEAKNESS.entrySet().removeIf(entry -> entry.getValue() + 6000L < System.currentTimeMillis());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void a(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        final Entity entity = entityDamageByEntityEvent.getEntity();
        if (this.isWeakened(entity)) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * 1.3);
            entity.getWorld().spawnParticle(Particle.WATER_SPLASH, entityDamageByEntityEvent.getEntity().getLocation().add(0.0, entity.getHeight() / 2.0, 0.0), 16, 0.3, 0.3, 0.3, 0.0);
        }
    }
    
    static {
        WATER_WEAKNESS = new HashMap<Integer, Long>();
    }
}
