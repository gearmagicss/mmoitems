// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.logging.Level;
import net.Indyuce.mmoitems.api.ConfigFile;
import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.event.world.ChunkLoadEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.block.BlockFace;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import net.Indyuce.mmoitems.api.block.WorldGenTemplate;
import java.util.Map;
import org.bukkit.event.Listener;

public class WorldGenManager implements Listener, Reloadable
{
    private final Map<String, WorldGenTemplate> templates;
    private final Map<CustomBlock, WorldGenTemplate> assigned;
    private static final BlockFace[] faces;
    private static final Random random;
    
    public WorldGenManager() {
        this.templates = new HashMap<String, WorldGenTemplate>();
        this.assigned = new HashMap<CustomBlock, WorldGenTemplate>();
        this.reload();
        if (MMOItems.plugin.getLanguage().worldGenEnabled) {
            Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
        }
    }
    
    public WorldGenTemplate getOrThrow(final String str) {
        Validate.isTrue(this.templates.containsKey(str), "Could not find gen template with ID '" + str + "'");
        return this.templates.get(str);
    }
    
    public void assign(final CustomBlock customBlock, final WorldGenTemplate worldGenTemplate) {
        Validate.notNull((Object)worldGenTemplate, "Cannot assign a null template to a custom block");
        this.assigned.put(customBlock, worldGenTemplate);
    }
    
    @EventHandler
    public void a(final ChunkLoadEvent chunkLoadEvent) {
        if (chunkLoadEvent.isNewChunk()) {
            int i = 0;
            final Location location;
            int j = 0;
            final Block block;
            final Block block2;
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)MMOItems.plugin, () -> this.assigned.forEach((customBlock, worldGenTemplate) -> {
                if (WorldGenManager.random.nextDouble() < worldGenTemplate.getChunkChance()) {
                    while (i < worldGenTemplate.getVeinCount()) {
                        chunkLoadEvent.getChunk().getBlock(WorldGenManager.random.nextInt(16), WorldGenManager.random.nextInt(worldGenTemplate.getMaxDepth() - worldGenTemplate.getMinDepth() + 1) + worldGenTemplate.getMinDepth(), WorldGenManager.random.nextInt(16)).getLocation();
                        if (worldGenTemplate.canGenerate(location)) {
                            location.getWorld().getBlockAt(location);
                            while (j < worldGenTemplate.getVeinSize()) {
                                if (worldGenTemplate.canReplace(block.getType())) {
                                    Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> {
                                        block2.setType(customBlock.getState().getType(), false);
                                        block2.setBlockData(customBlock.getState().getBlockData(), false);
                                        return;
                                    });
                                }
                                block.getRelative(WorldGenManager.faces[WorldGenManager.random.nextInt(WorldGenManager.faces.length)]);
                                ++j;
                            }
                        }
                        ++i;
                    }
                }
            }));
        }
    }
    
    public void reload() {
        this.assigned.clear();
        this.templates.clear();
        final FileConfiguration config = new ConfigFile("gen-templates").getConfig();
        for (final String str : config.getKeys(false)) {
            try {
                final WorldGenTemplate worldGenTemplate = new WorldGenTemplate(config.getConfigurationSection(str));
                this.templates.put(worldGenTemplate.getId(), worldGenTemplate);
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "An error occured when loading gen template '" + str + "': " + ex.getMessage());
            }
        }
    }
    
    static {
        faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST, BlockFace.DOWN, BlockFace.UP };
        random = new Random();
    }
}
