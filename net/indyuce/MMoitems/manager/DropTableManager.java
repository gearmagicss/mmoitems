// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventPriority;
import java.util.Optional;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import io.lumine.mythic.lib.UtilityMethods;
import net.Indyuce.mmoitems.listener.CustomBlockListener;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import org.bukkit.GameMode;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.event.ItemDropEvent;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.entity.EntityDeathEvent;
import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.HashMap;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.droptable.DropTable;
import org.bukkit.entity.EntityType;
import java.util.Map;
import org.bukkit.event.Listener;

public class DropTableManager implements Listener, Reloadable
{
    private final Map<EntityType, DropTable> monsters;
    private final Map<Material, DropTable> blocks;
    private final Map<Integer, DropTable> customBlocks;
    
    public DropTableManager() {
        this.monsters = new HashMap<EntityType, DropTable>();
        this.blocks = new HashMap<Material, DropTable>();
        this.customBlocks = new HashMap<Integer, DropTable>();
        this.reload();
    }
    
    public void reload() {
        this.monsters.clear();
        this.blocks.clear();
        this.customBlocks.clear();
        final FileConfiguration config = new ConfigFile("drops").getConfig();
        if (config.contains("monsters")) {
            for (final String s : config.getConfigurationSection("monsters").getKeys(false)) {
                try {
                    this.monsters.put(EntityType.valueOf(s.toUpperCase().replace("-", "_").replace(" ", "_")), new DropTable(config.getConfigurationSection("monsters." + s)));
                }
                catch (IllegalArgumentException ex) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not read drop table with mob type '" + s + "': " + ex.getMessage());
                }
            }
        }
        if (config.contains("blocks")) {
            for (final String s2 : config.getConfigurationSection("blocks").getKeys(false)) {
                try {
                    this.blocks.put(Material.valueOf(s2.toUpperCase().replace("-", "_").replace(" ", "_")), new DropTable(config.getConfigurationSection("blocks." + s2)));
                }
                catch (IllegalArgumentException ex2) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not read drop table with material '" + s2 + "': " + ex2.getMessage());
                }
            }
        }
        if (config.contains("customblocks")) {
            for (final String str : config.getConfigurationSection("customblocks").getKeys(false)) {
                try {
                    this.customBlocks.put(Integer.parseInt(str), new DropTable(config.getConfigurationSection("customblocks." + str)));
                }
                catch (IllegalArgumentException ex3) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not read drop table with custom block '" + str + "': " + ex3.getMessage());
                }
            }
        }
    }
    
    @EventHandler
    public void entityDrops(final EntityDeathEvent entityDeathEvent) {
        final LivingEntity entity = entityDeathEvent.getEntity();
        final Player killer = entity.getKiller();
        if (killer != null && killer.hasMetadata("NPC")) {
            return;
        }
        if (this.monsters.containsKey(entity.getType())) {
            final List<ItemStack> read = this.monsters.get(entity.getType()).read((killer != null) ? PlayerData.get((OfflinePlayer)killer) : null, false);
            final ItemDropEvent itemDropEvent = new ItemDropEvent((LivingEntity)killer, read, (Entity)entity);
            Bukkit.getPluginManager().callEvent((Event)itemDropEvent);
            if (itemDropEvent.isCancelled()) {
                return;
            }
            entityDeathEvent.getDrops().addAll(read);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void blockDrops(final BlockBreakEvent blockBreakEvent) {
        final Player player = blockBreakEvent.getPlayer();
        if (player == null || player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        final Block block = blockBreakEvent.getBlock();
        final Optional<CustomBlock> fromBlock = MMOItems.plugin.getCustomBlocks().getFromBlock(block.getBlockData());
        if (fromBlock.isPresent()) {
            final CustomBlock customBlock = fromBlock.get();
            if (this.customBlocks.containsKey(customBlock.getId()) && CustomBlockListener.getPickaxePower(player) >= customBlock.getRequiredPower()) {
                final ItemDropEvent itemDropEvent = new ItemDropEvent((LivingEntity)player, this.customBlocks.get(customBlock.getId()).read(PlayerData.get((OfflinePlayer)player), this.hasSilkTouchTool(player)), customBlock);
                Bukkit.getPluginManager().callEvent((Event)itemDropEvent);
                if (itemDropEvent.isCancelled()) {
                    return;
                }
                final List<ItemStack> list;
                final Iterator<ItemStack> iterator;
                final Block block2;
                Bukkit.getScheduler().runTaskLater((Plugin)MMOItems.plugin, () -> {
                    list.iterator();
                    while (iterator.hasNext()) {
                        UtilityMethods.dropItemNaturally(block2.getLocation(), (ItemStack)iterator.next());
                    }
                }, 2L);
            }
        }
        else if (this.blocks.containsKey(block.getType())) {
            final ItemDropEvent itemDropEvent2 = new ItemDropEvent((LivingEntity)player, this.blocks.get(block.getType()).read(PlayerData.get((OfflinePlayer)player), this.hasSilkTouchTool(player)), block);
            Bukkit.getPluginManager().callEvent((Event)itemDropEvent2);
            if (itemDropEvent2.isCancelled()) {
                return;
            }
            final List<ItemStack> list2;
            final Iterator<ItemStack> iterator2;
            final Block block3;
            Bukkit.getScheduler().runTaskLater((Plugin)MMOItems.plugin, () -> {
                list2.iterator();
                while (iterator2.hasNext()) {
                    UtilityMethods.dropItemNaturally(block3.getLocation(), (ItemStack)iterator2.next());
                }
            }, 2L);
        }
    }
    
    public boolean hasSilkTouchTool(final Player player) {
        final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        return itemInMainHand != null && itemInMainHand.getType() != Material.AIR && itemInMainHand.hasItemMeta() && itemInMainHand.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH);
    }
}
