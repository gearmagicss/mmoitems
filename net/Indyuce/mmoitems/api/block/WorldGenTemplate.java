// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.block;

import org.bukkit.block.BlockFace;
import org.bukkit.block.Biome;
import org.bukkit.Location;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Material;
import java.util.List;

public class WorldGenTemplate
{
    private final String id;
    private final double chunkChance;
    private final int minDepth;
    private final int maxDepth;
    private final int veinSize;
    private final int veinCount;
    private final List<Material> replaceable;
    private final List<Material> bordering;
    private final List<Material> notBordering;
    private final List<String> worldWhitelist;
    private final List<String> worldBlacklist;
    private final List<String> biomeWhitelist;
    private final List<String> biomeBlacklist;
    private final boolean slimeChunk;
    
    public WorldGenTemplate(final ConfigurationSection configurationSection) {
        this.replaceable = new ArrayList<Material>();
        this.bordering = new ArrayList<Material>();
        this.notBordering = new ArrayList<Material>();
        this.worldWhitelist = new ArrayList<String>();
        this.worldBlacklist = new ArrayList<String>();
        this.biomeWhitelist = new ArrayList<String>();
        this.biomeBlacklist = new ArrayList<String>();
        Validate.notNull((Object)configurationSection, "Could not read gen template config");
        this.id = configurationSection.getName().toLowerCase().replace(" ", "-").replace("_", "-");
        configurationSection.getStringList("replace").forEach(s -> this.replaceable.add(Material.valueOf(s.toUpperCase().replace("-", "_").replace(" ", "_"))));
        configurationSection.getStringList("bordering").forEach(s2 -> this.bordering.add(Material.valueOf(s2.toUpperCase().replace("-", "_").replace(" ", "_"))));
        configurationSection.getStringList("not-bordering").forEach(s3 -> this.notBordering.add(Material.valueOf(s3.toUpperCase().replace("-", "_").replace(" ", "_"))));
        for (final String s4 : configurationSection.getStringList("worlds")) {
            (s4.startsWith("!") ? this.worldBlacklist : this.worldWhitelist).add(s4.toLowerCase().replace("_", "-"));
        }
        for (final String s5 : configurationSection.getStringList("biomes")) {
            (s5.startsWith("!") ? this.biomeBlacklist : this.biomeWhitelist).add(s5.toUpperCase().replace("-", "_").replace(" ", "_"));
        }
        this.chunkChance = configurationSection.getDouble("chunk-chance");
        this.slimeChunk = configurationSection.getBoolean("slime-chunk", false);
        final String[] split = configurationSection.getString("depth").split("=");
        this.minDepth = Integer.parseInt(split[0]);
        this.maxDepth = Integer.parseInt(split[1]);
        Validate.isTrue(this.minDepth >= 0, "Min depth must be greater than 0");
        Validate.isTrue(this.maxDepth < 256, "Max depth must be at most 255");
        this.veinSize = configurationSection.getInt("vein-size");
        this.veinCount = configurationSection.getInt("vein-count");
        Validate.isTrue(this.veinSize > 0 && this.veinCount > 0, "Vein size and count must be at least 1");
    }
    
    public String getId() {
        return this.id;
    }
    
    public double getChunkChance() {
        return this.chunkChance;
    }
    
    public int getVeinSize() {
        return this.veinSize;
    }
    
    public int getVeinCount() {
        return this.veinCount;
    }
    
    public int getMinDepth() {
        return this.minDepth;
    }
    
    public int getMaxDepth() {
        return this.maxDepth;
    }
    
    public boolean canGenerate(final Location location) {
        final String replace = location.getWorld().getName().toLowerCase().replace("_", "-");
        if (!this.worldWhitelist.isEmpty() && !this.worldWhitelist.contains(replace)) {
            return false;
        }
        if (!this.worldBlacklist.isEmpty() && this.worldBlacklist.contains(replace)) {
            return false;
        }
        final Biome biome = location.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return (this.biomeWhitelist.isEmpty() || this.biomeWhitelist.contains(biome.name())) && (this.biomeBlacklist.isEmpty() || !this.biomeBlacklist.contains(biome.name())) && (!this.slimeChunk || location.getChunk().isSlimeChunk()) && (this.bordering.isEmpty() || this.checkIfBorderingBlocks(location)) && (this.notBordering.isEmpty() || this.checkIfNotBorderingBlocks(location));
    }
    
    public boolean canReplace(final Material material) {
        return this.replaceable.isEmpty() || this.replaceable.contains(material);
    }
    
    public boolean canBorder(final Material material) {
        return this.bordering.isEmpty() || this.bordering.contains(material);
    }
    
    public boolean checkIfBorderingBlocks(final Location location) {
        return this.canBorder(location.getBlock().getRelative(BlockFace.NORTH).getType()) && this.canBorder(location.getBlock().getRelative(BlockFace.EAST).getType()) && this.canBorder(location.getBlock().getRelative(BlockFace.SOUTH).getType()) && this.canBorder(location.getBlock().getRelative(BlockFace.WEST).getType()) && this.canBorder(location.getBlock().getRelative(BlockFace.UP).getType()) && this.canBorder(location.getBlock().getRelative(BlockFace.DOWN).getType());
    }
    
    public boolean canNotBorder(final Material material) {
        return !this.notBordering.isEmpty() && this.notBordering.contains(material);
    }
    
    public boolean checkIfNotBorderingBlocks(final Location location) {
        return !this.canNotBorder(location.getBlock().getRelative(BlockFace.NORTH).getType()) && !this.canNotBorder(location.getBlock().getRelative(BlockFace.EAST).getType()) && !this.canNotBorder(location.getBlock().getRelative(BlockFace.SOUTH).getType()) && !this.canNotBorder(location.getBlock().getRelative(BlockFace.WEST).getType()) && !this.canNotBorder(location.getBlock().getRelative(BlockFace.UP).getType()) && !this.canNotBorder(location.getBlock().getRelative(BlockFace.DOWN).getType());
    }
}
