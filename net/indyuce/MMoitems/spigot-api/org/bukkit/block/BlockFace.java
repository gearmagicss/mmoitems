// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

public enum BlockFace
{
    NORTH("NORTH", 0, 0, 0, -1), 
    EAST("EAST", 1, 1, 0, 0), 
    SOUTH("SOUTH", 2, 0, 0, 1), 
    WEST("WEST", 3, -1, 0, 0), 
    UP("UP", 4, 0, 1, 0), 
    DOWN("DOWN", 5, 0, -1, 0), 
    NORTH_EAST("NORTH_EAST", 6, BlockFace.NORTH, BlockFace.EAST), 
    NORTH_WEST("NORTH_WEST", 7, BlockFace.NORTH, BlockFace.WEST), 
    SOUTH_EAST("SOUTH_EAST", 8, BlockFace.SOUTH, BlockFace.EAST), 
    SOUTH_WEST("SOUTH_WEST", 9, BlockFace.SOUTH, BlockFace.WEST), 
    WEST_NORTH_WEST("WEST_NORTH_WEST", 10, BlockFace.WEST, BlockFace.NORTH_WEST), 
    NORTH_NORTH_WEST("NORTH_NORTH_WEST", 11, BlockFace.NORTH, BlockFace.NORTH_WEST), 
    NORTH_NORTH_EAST("NORTH_NORTH_EAST", 12, BlockFace.NORTH, BlockFace.NORTH_EAST), 
    EAST_NORTH_EAST("EAST_NORTH_EAST", 13, BlockFace.EAST, BlockFace.NORTH_EAST), 
    EAST_SOUTH_EAST("EAST_SOUTH_EAST", 14, BlockFace.EAST, BlockFace.SOUTH_EAST), 
    SOUTH_SOUTH_EAST("SOUTH_SOUTH_EAST", 15, BlockFace.SOUTH, BlockFace.SOUTH_EAST), 
    SOUTH_SOUTH_WEST("SOUTH_SOUTH_WEST", 16, BlockFace.SOUTH, BlockFace.SOUTH_WEST), 
    WEST_SOUTH_WEST("WEST_SOUTH_WEST", 17, BlockFace.WEST, BlockFace.SOUTH_WEST), 
    SELF("SELF", 18, 0, 0, 0);
    
    private final int modX;
    private final int modY;
    private final int modZ;
    
    private BlockFace(final String name, final int ordinal, final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }
    
    private BlockFace(final String name, final int ordinal, final BlockFace face1, final BlockFace face2) {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }
    
    public int getModX() {
        return this.modX;
    }
    
    public int getModY() {
        return this.modY;
    }
    
    public int getModZ() {
        return this.modZ;
    }
    
    public BlockFace getOppositeFace() {
        switch (this) {
            case NORTH: {
                return BlockFace.SOUTH;
            }
            case SOUTH: {
                return BlockFace.NORTH;
            }
            case EAST: {
                return BlockFace.WEST;
            }
            case WEST: {
                return BlockFace.EAST;
            }
            case UP: {
                return BlockFace.DOWN;
            }
            case DOWN: {
                return BlockFace.UP;
            }
            case NORTH_EAST: {
                return BlockFace.SOUTH_WEST;
            }
            case NORTH_WEST: {
                return BlockFace.SOUTH_EAST;
            }
            case SOUTH_EAST: {
                return BlockFace.NORTH_WEST;
            }
            case SOUTH_WEST: {
                return BlockFace.NORTH_EAST;
            }
            case WEST_NORTH_WEST: {
                return BlockFace.EAST_SOUTH_EAST;
            }
            case NORTH_NORTH_WEST: {
                return BlockFace.SOUTH_SOUTH_EAST;
            }
            case NORTH_NORTH_EAST: {
                return BlockFace.SOUTH_SOUTH_WEST;
            }
            case EAST_NORTH_EAST: {
                return BlockFace.WEST_SOUTH_WEST;
            }
            case EAST_SOUTH_EAST: {
                return BlockFace.WEST_NORTH_WEST;
            }
            case SOUTH_SOUTH_EAST: {
                return BlockFace.NORTH_NORTH_WEST;
            }
            case SOUTH_SOUTH_WEST: {
                return BlockFace.NORTH_NORTH_EAST;
            }
            case WEST_SOUTH_WEST: {
                return BlockFace.EAST_NORTH_EAST;
            }
            case SELF: {
                return BlockFace.SELF;
            }
            default: {
                return BlockFace.SELF;
            }
        }
    }
}
