// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs;

import org.bukkit.Particle;
import org.bukkit.Color;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ItemTier;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Item;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.util.Vector;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Random;
import org.bukkit.event.Listener;

public class LootsplosionListener implements Listener
{
    private static final Random random;
    private final boolean colored;
    
    public LootsplosionListener() {
        this.colored = MMOItems.plugin.getConfig().getBoolean("lootsplosion.color");
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void b(final MythicMobDeathEvent mythicMobDeathEvent) {
        if (mythicMobDeathEvent.getMob().getVariables().has("Lootsplosion")) {
            new LootsplosionHandler(mythicMobDeathEvent);
        }
    }
    
    private Vector randomVector() {
        final double double1 = MMOItems.plugin.getConfig().getDouble("lootsplosion.offset");
        return new Vector(Math.cos(LootsplosionListener.random.nextDouble() * 3.141592653589793 * 2.0) * double1, MMOItems.plugin.getConfig().getDouble("lootsplosion.height"), Math.sin(LootsplosionListener.random.nextDouble() * 3.141592653589793 * 2.0) * double1);
    }
    
    static {
        random = new Random();
    }
    
    public class LootsplosionHandler implements Listener
    {
        private final List<ItemStack> drops;
        private final double offset;
        
        public LootsplosionHandler(final MythicMobDeathEvent mythicMobDeathEvent) {
            this.offset = mythicMobDeathEvent.getEntity().getHeight() / 2.0;
            this.drops = new ArrayList<ItemStack>(mythicMobDeathEvent.getDrops());
            Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
        }
        
        private void close() {
            ItemSpawnEvent.getHandlerList().unregister((Listener)this);
        }
        
        @EventHandler
        public void a(final ItemSpawnEvent itemSpawnEvent) {
            final Item entity = itemSpawnEvent.getEntity();
            if (!this.drops.contains(entity.getItemStack())) {
                this.close();
                return;
            }
            this.drops.remove(entity.getItemStack());
            entity.teleport(entity.getLocation().add(0.0, this.offset, 0.0));
            entity.setVelocity(LootsplosionListener.this.randomVector());
            if (LootsplosionListener.this.colored) {
                final Item item;
                final NBTItem nbtItem;
                final ItemTier itemTier;
                Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> {
                    MythicLib.plugin.getVersion().getWrapper().getNBTItem(item.getItemStack());
                    if (nbtItem.hasTag("MMOITEMS_TIER")) {
                        MMOItems.plugin.getTiers().get(nbtItem.getString("MMOITEMS_TIER"));
                        if (itemTier.hasColor()) {
                            new LootColor(item, itemTier.getColor().toBukkit());
                        }
                    }
                });
            }
        }
    }
    
    public class LootColor extends BukkitRunnable
    {
        private final Item item;
        private final Color color;
        private int j;
        
        public LootColor(final Item item, final Color color) {
            this.j = 0;
            this.item = item;
            this.color = color;
            this.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        }
        
        public void run() {
            if (this.j++ > 100 || this.item.isDead() || this.item.isOnGround()) {
                this.cancel();
                return;
            }
            this.item.getWorld().spawnParticle(Particle.REDSTONE, this.item.getLocation(), 1, (Object)new Particle.DustOptions(this.color, 1.3f));
        }
    }
}
