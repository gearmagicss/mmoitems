// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.BlockData;
import org.bukkit.Material;

public class MushroomState
{
    private final Material material;
    private final boolean up;
    private final boolean down;
    private final boolean west;
    private final boolean east;
    private final boolean south;
    private final boolean north;
    
    public MushroomState(final Material material, final boolean up, final boolean down, final boolean west, final boolean east, final boolean south, final boolean north) {
        this.material = material;
        this.up = up;
        this.down = down;
        this.west = west;
        this.east = east;
        this.south = south;
        this.north = north;
    }
    
    public int getUniqueId() {
        return Integer.parseInt(((this.material == Material.BROWN_MUSHROOM_BLOCK) ? "0" : ((this.material == Material.RED_MUSHROOM_BLOCK) ? "1" : "2")) + (this.up ? "1" : "0") + (this.down ? "1" : "0") + (this.west ? "1" : "0") + (this.east ? "1" : "0") + (this.south ? "1" : "0") + (this.north ? "1" : "0"));
    }
    
    public boolean matches(final MushroomState mushroomState) {
        return this.up == mushroomState.up && this.down == mushroomState.down && this.west == mushroomState.west && this.east == mushroomState.east && this.south == mushroomState.south && this.north == mushroomState.north;
    }
    
    public Material getType() {
        return this.material;
    }
    
    public BlockData getBlockData() {
        final MultipleFacing multipleFacing = (MultipleFacing)this.material.createBlockData();
        multipleFacing.setFace(BlockFace.UP, this.up);
        multipleFacing.setFace(BlockFace.DOWN, this.down);
        multipleFacing.setFace(BlockFace.NORTH, this.north);
        multipleFacing.setFace(BlockFace.SOUTH, this.south);
        multipleFacing.setFace(BlockFace.EAST, this.east);
        multipleFacing.setFace(BlockFace.WEST, this.west);
        return (BlockData)multipleFacing;
    }
    
    public boolean getSide(String s) {
        final String lowerCase;
        s = (lowerCase = s.toLowerCase());
        switch (lowerCase) {
            case "up": {
                return this.up;
            }
            case "down": {
                return this.down;
            }
            case "north": {
                return this.north;
            }
            case "south": {
                return this.south;
            }
            case "east": {
                return this.east;
            }
            case "west": {
                return this.west;
            }
            default: {
                return false;
            }
        }
    }
}
