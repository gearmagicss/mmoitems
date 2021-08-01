// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemMendEvent;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import java.util.Arrays;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.entity.EntityDamageEvent;
import java.util.List;
import org.bukkit.event.Listener;

public class DurabilityListener implements Listener
{
    private final List<EntityDamageEvent.DamageCause> ignoredCauses;
    private final EquipmentSlot[] armorSlots;
    
    public DurabilityListener() {
        this.ignoredCauses = Arrays.asList(EntityDamageEvent.DamageCause.DROWNING, EntityDamageEvent.DamageCause.SUICIDE, EntityDamageEvent.DamageCause.FALL, EntityDamageEvent.DamageCause.VOID, EntityDamageEvent.DamageCause.FIRE_TICK, EntityDamageEvent.DamageCause.SUFFOCATION, EntityDamageEvent.DamageCause.POISON, EntityDamageEvent.DamageCause.WITHER, EntityDamageEvent.DamageCause.STARVATION, EntityDamageEvent.DamageCause.MAGIC);
        this.armorSlots = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerDamage(final EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntityType() != EntityType.PLAYER || this.ignoredCauses.contains(entityDamageEvent.getCause())) {
            return;
        }
        final Player player = (Player)entityDamageEvent.getEntity();
        final int max = Math.max((int)entityDamageEvent.getDamage() / 4, 1);
        for (final EquipmentSlot equipmentSlot : this.armorSlots) {
            if (this.hasItem(player, equipmentSlot)) {
                this.handleVanillaDamage(player.getInventory().getItem(equipmentSlot), player, equipmentSlot, max);
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerMeleeAttack(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamage() == 0.0 || entityDamageByEntityEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || !(entityDamageByEntityEvent.getEntity() instanceof LivingEntity) || !(entityDamageByEntityEvent.getDamager() instanceof Player) || entityDamageByEntityEvent.getEntity().hasMetadata("NPC") || entityDamageByEntityEvent.getDamager().hasMetadata("NPC")) {
            return;
        }
        final Player player = (Player)entityDamageByEntityEvent.getDamager();
        this.handleVanillaDamage(player.getInventory().getItemInMainHand(), player, EquipmentSlot.HAND, 1);
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerBowAttack(final EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getEntity() instanceof Player)) {
            return;
        }
        this.handleVanillaDamage(entityShootBowEvent.getBow(), (Player)entityShootBowEvent.getEntity(), EquipmentSlot.HAND, 1);
    }
    
    @EventHandler(ignoreCancelled = true)
    public void itemDamage(final PlayerItemDamageEvent playerItemDamageEvent) {
        final DurabilityItem durabilityItem = new DurabilityItem(playerItemDamageEvent.getPlayer(), playerItemDamageEvent.getItem());
        if (durabilityItem.isValid()) {
            durabilityItem.decreaseDurability(playerItemDamageEvent.getDamage());
            if (durabilityItem.isBroken() && durabilityItem.isLostWhenBroken()) {
                playerItemDamageEvent.setDamage(999);
                return;
            }
            playerItemDamageEvent.setCancelled(true);
            playerItemDamageEvent.getItem().setItemMeta(durabilityItem.toItem().getItemMeta());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void mendEvent(final PlayerItemMendEvent playerItemMendEvent) {
        final DurabilityItem durabilityItem = new DurabilityItem(playerItemMendEvent.getPlayer(), playerItemMendEvent.getItem());
        if (durabilityItem.isValid()) {
            playerItemMendEvent.getItem().setItemMeta(durabilityItem.addDurability(playerItemMendEvent.getRepairAmount()).toItem().getItemMeta());
            playerItemMendEvent.setCancelled(true);
        }
    }
    
    private void handleVanillaDamage(final ItemStack itemStack, final Player player, final EquipmentSlot equipmentSlot, final int n) {
        final DurabilityItem durabilityItem = new DurabilityItem(player, itemStack);
        if (durabilityItem.isValid() && itemStack.getType().getMaxDurability() == 0) {
            durabilityItem.decreaseDurability(n);
            if (durabilityItem.isBroken() && durabilityItem.isLostWhenBroken()) {
                player.getInventory().setItem(equipmentSlot, (ItemStack)null);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                return;
            }
            player.getInventory().getItem(equipmentSlot).setItemMeta(durabilityItem.toItem().getItemMeta());
        }
    }
    
    private boolean hasItem(final Player player, final EquipmentSlot equipmentSlot) {
        return player.getInventory().getItem(equipmentSlot) != null && player.getInventory().getItem(equipmentSlot).getType() != Material.AIR;
    }
}
