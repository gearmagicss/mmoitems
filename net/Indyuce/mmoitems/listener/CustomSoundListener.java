// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import net.Indyuce.mmoitems.api.util.SoundReader;
import org.bukkit.Sound;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

public class CustomSoundListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void a(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player) || !(entityDamageByEntityEvent.getEntity() instanceof LivingEntity)) {
            return;
        }
        final Player player = (Player)entityDamageByEntityEvent.getDamager();
        playSound(player.getInventory().getItemInMainHand(), "ON_ATTACK", player);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void b(final EntityPickupItemEvent entityPickupItemEvent) {
        if (entityPickupItemEvent.getEntityType().equals((Object)EntityType.PLAYER)) {
            playSound(entityPickupItemEvent.getItem().getItemStack(), "ON_PICKUP", (Player)entityPickupItemEvent.getEntity());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void c(final BlockBreakEvent blockBreakEvent) {
        playSound(blockBreakEvent.getPlayer().getInventory().getItemInMainHand(), "ON_BLOCK_BREAK", blockBreakEvent.getPlayer());
    }
    
    @EventHandler
    public void d(final PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() == Action.PHYSICAL || !playerInteractEvent.hasItem()) {
            return;
        }
        if (playerInteractEvent.getAction().name().contains("RIGHT_CLICK")) {
            playSound(playerInteractEvent.getItem(), "ON_RIGHT_CLICK", playerInteractEvent.getPlayer());
        }
        if (playerInteractEvent.getAction().name().contains("LEFT_CLICK")) {
            playSound(playerInteractEvent.getItem(), "ON_LEFT_CLICK", playerInteractEvent.getPlayer());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void e(final CraftItemEvent craftItemEvent) {
        playSound(craftItemEvent.getInventory().getResult(), "ON_CRAFT", craftItemEvent.getWhoClicked().getLocation());
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void f(final FurnaceSmeltEvent furnaceSmeltEvent) {
        playSound(furnaceSmeltEvent.getResult(), "ON_CRAFT", furnaceSmeltEvent.getBlock().getLocation());
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void g(final PlayerItemConsumeEvent playerItemConsumeEvent) {
        playSound(playerItemConsumeEvent.getItem(), "ON_CONSUME", playerItemConsumeEvent.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void h1(final PlayerItemBreakEvent playerItemBreakEvent) {
        playSound(playerItemBreakEvent.getBrokenItem(), "ON_ITEM_BREAK", playerItemBreakEvent.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void i(final BlockPlaceEvent blockPlaceEvent) {
        playSound(blockPlaceEvent.getItemInHand(), "ON_PLACED", blockPlaceEvent.getPlayer());
    }
    
    public static void stationCrafting(final ItemStack itemStack, final Player player) {
        playSound(itemStack, "ON_CRAFT", player);
    }
    
    public static void playConsumableSound(final ItemStack itemStack, final Player player) {
        playSound(itemStack, "ON_CONSUME", player);
    }
    
    private static void playSound(final ItemStack itemStack, final String s, final Player player) {
        playSound(itemStack, s, player.getLocation());
    }
    
    private static void playSound(final ItemStack itemStack, final String s, final Location location) {
        if (itemStack == null) {
            return;
        }
        final NBTItem value = NBTItem.get(itemStack);
        if (value.hasTag("MMOITEMS_SOUND_" + s)) {
            new SoundReader(value.getString("MMOITEMS_SOUND_" + s), Sound.ENTITY_PIG_AMBIENT).play(location, (float)value.getDouble("MMOITEMS_SOUND_" + s + "_VOL"), (float)value.getDouble("MMOITEMS_SOUND_" + s + "_PIT"));
        }
    }
}
