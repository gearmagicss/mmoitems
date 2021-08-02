// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.generator;

import org.bukkit.material.MaterialData;
import org.bukkit.block.Biome;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import java.util.Random;
import org.bukkit.World;

public abstract class ChunkGenerator
{
    @Deprecated
    public byte[] generate(final World world, final Random random, final int x, final int z) {
        throw new UnsupportedOperationException("Custom generator is missing required methods: generate(), generateBlockSections() and generateExtBlockSections()");
    }
    
    @Deprecated
    public short[][] generateExtBlockSections(final World world, final Random random, final int x, final int z, final BiomeGrid biomes) {
        return null;
    }
    
    @Deprecated
    public byte[][] generateBlockSections(final World world, final Random random, final int x, final int z, final BiomeGrid biomes) {
        return null;
    }
    
    public ChunkData generateChunkData(final World world, final Random random, final int x, final int z, final BiomeGrid biome) {
        return null;
    }
    
    protected final ChunkData createChunkData(final World world) {
        return Bukkit.getServer().createChunkData(world);
    }
    
    public boolean canSpawn(final World world, final int x, final int z) {
        final Block highest = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z);
        switch (world.getEnvironment()) {
            case NETHER: {
                return true;
            }
            case THE_END: {
                return highest.getType() != Material.AIR && highest.getType() != Material.WATER && highest.getType() != Material.LAVA;
            }
            default: {
                return highest.getType() == Material.SAND || highest.getType() == Material.GRAVEL;
            }
        }
    }
    
    public List<BlockPopulator> getDefaultPopulators(final World world) {
        return new ArrayList<BlockPopulator>();
    }
    
    public Location getFixedSpawnLocation(final World world, final Random random) {
        return null;
    }
    
    public interface BiomeGrid
    {
        Biome getBiome(final int p0, final int p1);
        
        void setBiome(final int p0, final int p1, final Biome p2);
    }
    
    public interface ChunkData
    {
        int getMaxHeight();
        
        void setBlock(final int p0, final int p1, final int p2, final Material p3);
        
        void setBlock(final int p0, final int p1, final int p2, final MaterialData p3);
        
        void setRegion(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Material p6);
        
        void setRegion(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final MaterialData p6);
        
        Material getType(final int p0, final int p1, final int p2);
        
        MaterialData getTypeAndData(final int p0, final int p1, final int p2);
        
        @Deprecated
        void setRegion(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
        
        @Deprecated
        void setRegion(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
        
        @Deprecated
        void setBlock(final int p0, final int p1, final int p2, final int p3);
        
        @Deprecated
        void setBlock(final int p0, final int p1, final int p2, final int p3, final byte p4);
        
        @Deprecated
        int getTypeId(final int p0, final int p1, final int p2);
        
        @Deprecated
        byte getData(final int p0, final int p1, final int p2);
    }
}
