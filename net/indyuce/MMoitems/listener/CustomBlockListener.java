// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlot;
import java.util.logging.Level;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventPriority;
import java.util.Optional;
import org.bukkit.GameMode;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Random;
import org.bukkit.event.Listener;

public class CustomBlockListener implements Listener
{
    private static final Random RANDOM;
    
    public CustomBlockListener() {
        if (MMOItems.plugin.getLanguage().replaceMushroomDrops) {
            Bukkit.getPluginManager().registerEvents((Listener)new MushroomReplacer(), (Plugin)MMOItems.plugin);
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void a(final BlockPhysicsEvent blockPhysicsEvent) {
        if (MMOItems.plugin.getCustomBlocks().isMushroomBlock(blockPhysicsEvent.getChangedType())) {
            blockPhysicsEvent.setCancelled(true);
            blockPhysicsEvent.getBlock().getState().update(true, false);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void b(final BlockBreakEvent blockBreakEvent) {
        final Optional<CustomBlock> fromBlock = MMOItems.plugin.getCustomBlocks().getFromBlock(blockBreakEvent.getBlock().getBlockData());
        if (!fromBlock.isPresent()) {
            return;
        }
        final CustomBlock customBlock = fromBlock.get();
        final int pickaxePower = getPickaxePower(blockBreakEvent.getPlayer());
        if (customBlock.requirePowerToBreak() && pickaxePower < customBlock.getRequiredPower()) {
            blockBreakEvent.setCancelled(true);
            return;
        }
        blockBreakEvent.setDropItems(false);
        blockBreakEvent.setExpToDrop((blockBreakEvent.getPlayer().getGameMode() == GameMode.CREATIVE) ? 0 : ((getPickaxePower(blockBreakEvent.getPlayer()) >= customBlock.getRequiredPower()) ? ((customBlock.getMaxExpDrop() == 0 && customBlock.getMinExpDrop() == 0) ? 0 : (CustomBlockListener.RANDOM.nextInt(customBlock.getMaxExpDrop() - customBlock.getMinExpDrop() + 1) + customBlock.getMinExpDrop())) : 0));
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void c(final BlockPlaceEvent blockPlaceEvent) {
        if (!blockPlaceEvent.isCancelled() && !this.isMushroomBlock(blockPlaceEvent.getBlockPlaced().getType())) {
            final int integer = MythicLib.plugin.getVersion().getWrapper().getNBTItem(blockPlaceEvent.getItemInHand()).getInteger("MMOITEMS_BLOCK_ID");
            if (integer > 160 || integer < 1 || integer == 54) {
                return;
            }
            if (MMOItems.plugin.getCustomBlocks().getBlock(integer) == null) {
                MMOItems.plugin.getLogger().log(Level.SEVERE, "Could not load custom block '" + integer + "':  Block is not registered.");
                MMOItems.plugin.getLogger().log(Level.SEVERE, "Try reloading the plugin to solve the issue.");
                blockPlaceEvent.setCancelled(true);
                return;
            }
            final CustomBlock block = MMOItems.plugin.getCustomBlocks().getBlock(integer);
            final Block blockPlaced = blockPlaceEvent.getBlockPlaced();
            blockPlaced.setType(block.getState().getType(), false);
            blockPlaced.setBlockData(block.getState().getBlockData(), false);
            Bukkit.getServer().getPluginManager().callEvent((Event)new BlockPlaceEvent(blockPlaced, blockPlaced.getState(), blockPlaceEvent.getBlockAgainst(), blockPlaceEvent.getItemInHand(), blockPlaceEvent.getPlayer(), true, EquipmentSlot.HAND));
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void d(final BlockIgniteEvent blockIgniteEvent) {
        if (blockIgniteEvent.getCause() == BlockIgniteEvent.IgniteCause.LAVA || blockIgniteEvent.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            final BlockFace[] array = { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST };
            for (int length = array.length, i = 0; i < length; ++i) {
                if (MMOItems.plugin.getCustomBlocks().getFromBlock(blockIgniteEvent.getBlock().getRelative(array[i]).getBlockData()).isPresent()) {
                    blockIgniteEvent.setCancelled(true);
                }
            }
        }
    }
    
    public static int getPickaxePower(final Player player) {
        final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand == null || itemInMainHand.getType() == Material.AIR) {
            return 0;
        }
        final NBTItem value = NBTItem.get(itemInMainHand);
        if (value.hasType()) {
            return value.getInteger("MMOITEMS_PICKAXE_POWER");
        }
        final String name = itemInMainHand.getType().name();
        switch (name) {
            case "WOODEN_PICKAXE":
            case "WOOD_PICKAXE": {
                return 5;
            }
            case "STONE_PICKAXE": {
                return 10;
            }
            case "GOLDEN_PICKAXE":
            case "GOLD_PICKAXE": {
                return 15;
            }
            case "IRON_PICKAXE": {
                return 20;
            }
            case "DIAMOND_PICKAXE": {
                return 25;
            }
            case "NETHERITE_PICKAXE": {
                return 30;
            }
            default: {
                return 0;
            }
        }
    }
    
    private boolean isMushroomBlock(final Material material) {
        return material == Material.BROWN_MUSHROOM_BLOCK || material == Material.MUSHROOM_STEM || material == Material.RED_MUSHROOM_BLOCK;
    }
    
    static {
        RANDOM = new Random();
    }
    
    public static class MushroomReplacer implements Listener
    {
        @EventHandler(ignoreCancelled = true)
        public void d(final BlockBreakEvent blockBreakEvent) {
            if (MMOItems.plugin.getCustomBlocks().isMushroomBlock(blockBreakEvent.getBlock().getType()) && MMOItems.plugin.getDropTables().hasSilkTouchTool(blockBreakEvent.getPlayer())) {
                blockBreakEvent.setDropItems(false);
            }
        }
    }
}
