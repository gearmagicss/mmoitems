// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import org.bukkit.event.player.PlayerItemConsumeEvent;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.interaction.GemStone;
import net.Indyuce.mmoitems.api.interaction.ItemSkin;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.api.interaction.weapon.Gauntlet;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.Staff;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.block.Block;
import net.Indyuce.mmoitems.api.interaction.Tool;
import org.bukkit.GameMode;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventPriority;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.api.TypeSet;
import net.Indyuce.mmoitems.api.interaction.weapon.Weapon;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.UntargetedWeapon;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.interaction.UseItem;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.player.PlayerInteractEvent;
import java.text.DecimalFormat;
import org.bukkit.event.Listener;

public class ItemUse implements Listener
{
    private static final DecimalFormat DIGIT;
    
    @EventHandler
    public void rightClickEffects(final PlayerInteractEvent playerInteractEvent) {
        if (!playerInteractEvent.hasItem()) {
            return;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(playerInteractEvent.getItem());
        if (!nbtItem.hasType()) {
            return;
        }
        final Player player = playerInteractEvent.getPlayer();
        final UseItem item = UseItem.getItem(player, nbtItem, nbtItem.getType());
        if (item instanceof Consumable && ((Consumable)item).hasVanillaEating()) {
            return;
        }
        if (!item.checkItemRequirements()) {
            playerInteractEvent.setCancelled(true);
            return;
        }
        if (playerInteractEvent.getAction().name().contains("RIGHT_CLICK")) {
            if (!item.getPlayerData().isOnCooldown(item.getMMOItem().getId())) {
                Message.ITEM_ON_COOLDOWN.format(ChatColor.RED, "#left#", ItemUse.DIGIT.format(item.getPlayerData().getItemCooldown(item.getMMOItem().getId()))).send(player, "item-cooldown");
                playerInteractEvent.setCancelled(true);
                return;
            }
            if (item instanceof Consumable) {
                playerInteractEvent.setCancelled(true);
                final Consumable.ConsumableConsumeResult useOnPlayer = ((Consumable)item).useOnPlayer();
                if (useOnPlayer == Consumable.ConsumableConsumeResult.CANCEL) {
                    return;
                }
                if (useOnPlayer == Consumable.ConsumableConsumeResult.CONSUME) {
                    playerInteractEvent.getItem().setAmount(playerInteractEvent.getItem().getAmount() - 1);
                }
            }
            item.getPlayerData().applyItemCooldown(item.getMMOItem().getId(), item.getNBTItem().getStat("ITEM_COOLDOWN"));
            item.executeCommands();
        }
        if (item instanceof UntargetedWeapon) {
            final UntargetedWeapon untargetedWeapon = (UntargetedWeapon)item;
            if ((playerInteractEvent.getAction().name().contains("RIGHT_CLICK") && untargetedWeapon.getWeaponType() == UntargetedWeapon.WeaponType.RIGHT_CLICK) || (playerInteractEvent.getAction().name().contains("LEFT_CLICK") && untargetedWeapon.getWeaponType() == UntargetedWeapon.WeaponType.LEFT_CLICK)) {
                untargetedWeapon.untargetedAttack(playerInteractEvent.getHand());
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void meleeAttacks(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamage() == 0.0 || entityDamageByEntityEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || !(entityDamageByEntityEvent.getEntity() instanceof LivingEntity) || !(entityDamageByEntityEvent.getDamager() instanceof Player) || entityDamageByEntityEvent.getEntity().hasMetadata("NPC") || entityDamageByEntityEvent.getDamager().hasMetadata("NPC")) {
            return;
        }
        final LivingEntity livingEntity = (LivingEntity)entityDamageByEntityEvent.getEntity();
        if (MythicLib.plugin.getDamage().findInfo((Entity)livingEntity) != null) {
            return;
        }
        final Player player = (Player)entityDamageByEntityEvent.getDamager();
        PlayerStats.CachedStats temporary = null;
        final PlayerData value = PlayerData.get((OfflinePlayer)player);
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand());
        final ItemAttackResult itemAttackResult = new ItemAttackResult(entityDamageByEntityEvent.getDamage(), new DamageType[] { DamageType.WEAPON, DamageType.PHYSICAL });
        if (nbtItem.hasType() && Type.get(nbtItem.getType()) != Type.BLOCK) {
            final Weapon weapon = new Weapon(value, nbtItem);
            if (weapon.getMMOItem().getType().getItemSet() == TypeSet.RANGE) {
                entityDamageByEntityEvent.setCancelled(true);
                return;
            }
            if (!weapon.checkItemRequirements()) {
                entityDamageByEntityEvent.setCancelled(true);
                return;
            }
            weapon.handleTargetedAttack(temporary = value.getStats().newTemporary(EquipmentSlot.MAIN_HAND), livingEntity, itemAttackResult);
            if (!itemAttackResult.isSuccessful()) {
                entityDamageByEntityEvent.setCancelled(true);
                return;
            }
        }
        itemAttackResult.applyEffects((temporary == null) ? value.getStats().newTemporary(EquipmentSlot.MAIN_HAND) : temporary, nbtItem, livingEntity);
        entityDamageByEntityEvent.setDamage(itemAttackResult.getDamage());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void specialToolAbilities(final BlockBreakEvent blockBreakEvent) {
        final Player player = blockBreakEvent.getPlayer();
        final Block block = blockBreakEvent.getBlock();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand());
        if (!nbtItem.hasType()) {
            return;
        }
        final Tool tool = new Tool(player, nbtItem);
        if (!tool.checkItemRequirements()) {
            blockBreakEvent.setCancelled(true);
            return;
        }
        if (tool.miningEffects(block)) {
            blockBreakEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void rightClickWeaponInteractions(final PlayerInteractEntityEvent playerInteractEntityEvent) {
        final Player player = playerInteractEntityEvent.getPlayer();
        if (!(playerInteractEntityEvent.getRightClicked() instanceof LivingEntity)) {
            return;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInMainHand());
        if (!nbtItem.hasType()) {
            return;
        }
        final LivingEntity livingEntity = (LivingEntity)playerInteractEntityEvent.getRightClicked();
        if (!MMOUtils.canDamage(player, (Entity)livingEntity)) {
            return;
        }
        final UseItem item = UseItem.getItem(player, nbtItem, nbtItem.getType());
        if (!item.checkItemRequirements()) {
            return;
        }
        if (item instanceof Staff) {
            ((Staff)item).specialAttack(livingEntity);
        }
        if (item instanceof Gauntlet) {
            ((Gauntlet)item).specialAttack(livingEntity);
        }
    }
    
    @EventHandler
    public void gemStonesAndItemStacks(final InventoryClickEvent inventoryClickEvent) {
        final Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (inventoryClickEvent.getAction() != InventoryAction.SWAP_WITH_CURSOR) {
            return;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCursor());
        if (!nbtItem.hasType()) {
            return;
        }
        final UseItem item = UseItem.getItem(player, nbtItem, nbtItem.getType());
        if (!item.checkItemRequirements()) {
            return;
        }
        if (item instanceof ItemSkin) {
            final NBTItem nbtItem2 = MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCurrentItem());
            if (!nbtItem2.hasType()) {
                return;
            }
            final ItemSkin.ApplyResult applyOntoItem = ((ItemSkin)item).applyOntoItem(nbtItem2, Type.get(nbtItem2.getType()));
            if (applyOntoItem.getType() == ItemSkin.ResultType.NONE) {
                return;
            }
            inventoryClickEvent.setCancelled(true);
            nbtItem.getItem().setAmount(nbtItem.getItem().getAmount() - 1);
            if (applyOntoItem.getType() == ItemSkin.ResultType.FAILURE) {
                return;
            }
            inventoryClickEvent.setCurrentItem(applyOntoItem.getResult());
        }
        if (item instanceof GemStone) {
            final NBTItem nbtItem3 = MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCurrentItem());
            if (!nbtItem3.hasType()) {
                return;
            }
            final GemStone.ApplyResult applyOntoItem2 = ((GemStone)item).applyOntoItem(nbtItem3, Type.get(nbtItem3.getType()));
            if (applyOntoItem2.getType() == GemStone.ResultType.NONE) {
                return;
            }
            inventoryClickEvent.setCancelled(true);
            nbtItem.getItem().setAmount(nbtItem.getItem().getAmount() - 1);
            if (applyOntoItem2.getType() == GemStone.ResultType.FAILURE) {
                return;
            }
            inventoryClickEvent.setCurrentItem(applyOntoItem2.getResult());
        }
        if (item instanceof Consumable && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getType() != Material.AIR && ((Consumable)item).useOnItem(inventoryClickEvent, MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCurrentItem()))) {
            inventoryClickEvent.setCancelled(true);
            inventoryClickEvent.getCursor().setAmount(inventoryClickEvent.getCursor().getAmount() - 1);
        }
    }
    
    @EventHandler
    public void handleCustomBows(final EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getProjectile() instanceof Arrow) || !(entityShootBowEvent.getEntity() instanceof Player)) {
            return;
        }
        final NBTItem value = NBTItem.get(entityShootBowEvent.getBow());
        final Type value2 = Type.get(value.getType());
        final PlayerData value3 = PlayerData.get((OfflinePlayer)entityShootBowEvent.getEntity());
        if (value2 != null) {
            final Weapon weapon = new Weapon(value3, value);
            if (!weapon.checkItemRequirements() || !weapon.applyWeaponCosts()) {
                entityShootBowEvent.setCancelled(true);
                return;
            }
        }
        final EquipmentSlot equipmentSlot = value3.getPlayer().getInventory().getItemInMainHand().isSimilar(entityShootBowEvent.getBow()) ? EquipmentSlot.MAIN_HAND : EquipmentSlot.OFF_HAND;
        final Arrow arrow = (Arrow)entityShootBowEvent.getProjectile();
        if (value.getStat("ARROW_VELOCITY") > 0.0) {
            arrow.setVelocity(arrow.getVelocity().multiply(value.getStat("ARROW_VELOCITY")));
        }
        MMOItems.plugin.getEntities().registerCustomProjectile(value, value3.getStats().newTemporary(equipmentSlot), entityShootBowEvent.getProjectile(), value2 != null, entityShootBowEvent.getForce());
    }
    
    @EventHandler
    public void handleVanillaEatenConsumables(final PlayerItemConsumeEvent playerItemConsumeEvent) {
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(playerItemConsumeEvent.getItem());
        if (!nbtItem.hasType()) {
            return;
        }
        final Player player = playerItemConsumeEvent.getPlayer();
        final UseItem item = UseItem.getItem(player, nbtItem, nbtItem.getType());
        if (!item.checkItemRequirements()) {
            playerItemConsumeEvent.setCancelled(true);
            return;
        }
        if (item instanceof Consumable) {
            if (!item.getPlayerData().isOnCooldown(item.getMMOItem().getId())) {
                Message.ITEM_ON_COOLDOWN.format(ChatColor.RED, "#left#", ItemUse.DIGIT.format(item.getPlayerData().getItemCooldown(item.getMMOItem().getId()))).send(player, "item-cooldown");
                playerItemConsumeEvent.setCancelled(true);
                return;
            }
            if (!((Consumable)item).useWithoutItem()) {
                playerItemConsumeEvent.setCancelled(true);
                return;
            }
            item.getPlayerData().applyItemCooldown(item.getMMOItem().getId(), item.getNBTItem().getStat("ITEM_COOLDOWN"));
            item.executeCommands();
        }
    }
    
    static {
        DIGIT = new DecimalFormat("0.#");
    }
}
