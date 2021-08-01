// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.api.interaction.weapon.Weapon;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.interaction.util.InteractItem;
import org.bukkit.Material;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import io.lumine.mythic.utils.events.extra.ArmorEquipEvent;
import io.lumine.mythic.utils.Schedulers;
import org.bukkit.event.player.PlayerRespawnEvent;
import java.util.Iterator;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.SoulboundInfo;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import java.util.Map;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    private final Map<Player, ArrayList<ItemStack>> deathItems;
    
    public PlayerListener() {
        this.deathItems = new HashMap<Player, ArrayList<ItemStack>>();
    }
    
    @EventHandler
    public void loadPlayerData(final PlayerJoinEvent playerJoinEvent) {
        MMOItems.plugin.getRecipes().refreshRecipeBook(playerJoinEvent.getPlayer());
        PlayerData.load(playerJoinEvent.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void savePlayerData(final PlayerQuitEvent playerQuitEvent) {
        PlayerData.get((OfflinePlayer)playerQuitEvent.getPlayer()).save();
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void castWhenHitAbilities(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player) || entityDamageByEntityEvent.getEntity().hasMetadata("NPC")) {
            return;
        }
        final LivingEntity damager = MMOUtils.getDamager(entityDamageByEntityEvent);
        if (damager == null) {
            return;
        }
        PlayerData.get((OfflinePlayer)entityDamageByEntityEvent.getEntity()).castAbilities(damager, new ItemAttackResult(entityDamageByEntityEvent.getDamage(), new DamageType[] { DamageType.SKILL }), Ability.CastingMode.WHEN_HIT);
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void castClickAbilities(final PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() == Action.PHYSICAL) {
            return;
        }
        final Player player = playerInteractEvent.getPlayer();
        final boolean b = playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK;
        PlayerData.get((OfflinePlayer)player).castAbilities(null, new ItemAttackResult(true, new DamageType[] { DamageType.SKILL }), player.isSneaking() ? (b ? Ability.CastingMode.SHIFT_LEFT_CLICK : Ability.CastingMode.SHIFT_RIGHT_CLICK) : (b ? Ability.CastingMode.LEFT_CLICK : Ability.CastingMode.RIGHT_CLICK));
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(final PlayerDeathEvent playerDeathEvent) {
        if (playerDeathEvent.getKeepInventory() || !MMOItems.plugin.getLanguage().keepSoulboundOnDeath) {
            return;
        }
        final Player entity = playerDeathEvent.getEntity();
        final SoulboundInfo soulboundInfo = new SoulboundInfo(entity);
        final Iterator<ItemStack> iterator = (Iterator<ItemStack>)playerDeathEvent.getDrops().iterator();
        while (iterator.hasNext()) {
            final ItemStack e = iterator.next();
            final NBTItem value = NBTItem.get(e);
            if (value.hasTag("MMOITEMS_DISABLE_DEATH_DROP") && value.getBoolean("MMOITEMS_DISABLE_DEATH_DROP")) {
                iterator.remove();
                if (!this.deathItems.containsKey(entity)) {
                    this.deathItems.put(entity, new ArrayList<ItemStack>());
                }
                this.deathItems.get(entity).add(e);
            }
            else {
                if (!value.hasTag("MMOITEMS_SOULBOUND") || !value.getString("MMOITEMS_SOULBOUND").contains(entity.getUniqueId().toString())) {
                    continue;
                }
                iterator.remove();
                soulboundInfo.add(e);
            }
        }
        if (soulboundInfo.hasItems()) {
            soulboundInfo.setup();
        }
    }
    
    @EventHandler
    public void onRespawn(final PlayerRespawnEvent playerRespawnEvent) {
        final Player player = playerRespawnEvent.getPlayer();
        if (MMOItems.plugin.getLanguage().keepSoulboundOnDeath) {
            SoulboundInfo.read(player);
        }
        if (this.deathItems.containsKey(player)) {
            final Player player2;
            Schedulers.sync().runLater(() -> {
                player2.getInventory().addItem((ItemStack[])this.deathItems.get(player2).toArray(new ItemStack[0]));
                this.deathItems.remove(player2);
            }, 10L);
        }
    }
    
    @EventHandler
    public void onArmorEquip(final ArmorEquipEvent armorEquipEvent) {
        if (!PlayerData.get(armorEquipEvent.getPlayer().getUniqueId()).getRPG().canUse(NBTItem.get(armorEquipEvent.getNewArmorPiece()), true)) {
            armorEquipEvent.setCancelled(true);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void registerTridents(final ProjectileLaunchEvent projectileLaunchEvent) {
        if (!(projectileLaunchEvent.getEntity() instanceof Trident) || !(projectileLaunchEvent.getEntity().getShooter() instanceof Player)) {
            return;
        }
        final InteractItem interactItem = new InteractItem((Player)projectileLaunchEvent.getEntity().getShooter(), Material.TRIDENT);
        if (!interactItem.hasItem()) {
            return;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(interactItem.getItem());
        final Type value = Type.get(nbtItem.getType());
        final PlayerData value2 = PlayerData.get((OfflinePlayer)projectileLaunchEvent.getEntity().getShooter());
        if (value != null) {
            final Weapon weapon = new Weapon(value2, nbtItem);
            if (!weapon.checkItemRequirements() || !weapon.applyWeaponCosts()) {
                projectileLaunchEvent.setCancelled(true);
                return;
            }
        }
        MMOItems.plugin.getEntities().registerCustomProjectile(nbtItem, value2.getStats().newTemporary(EquipmentSlot.fromBukkit(interactItem.getSlot())), (Entity)projectileLaunchEvent.getEntity(), value != null);
    }
}
