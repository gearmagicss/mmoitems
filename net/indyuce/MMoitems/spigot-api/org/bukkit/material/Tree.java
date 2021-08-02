// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Tree extends Wood
{
    protected static final Material DEFAULT_TYPE;
    protected static final BlockFace DEFAULT_DIRECTION;
    
    static {
        DEFAULT_TYPE = Material.LOG;
        DEFAULT_DIRECTION = BlockFace.UP;
    }
    
    public Tree() {
        this(Tree.DEFAULT_TYPE, Tree.DEFAULT_SPECIES, Tree.DEFAULT_DIRECTION);
    }
    
    public Tree(final TreeSpecies species) {
        this(Tree.DEFAULT_TYPE, species, Tree.DEFAULT_DIRECTION);
    }
    
    public Tree(final TreeSpecies species, final BlockFace dir) {
        this(Tree.DEFAULT_TYPE, species, dir);
    }
    
    @Deprecated
    public Tree(final int type) {
        super(type);
    }
    
    public Tree(final Material type) {
        this(type, Tree.DEFAULT_SPECIES, Tree.DEFAULT_DIRECTION);
    }
    
    public Tree(final Material type, final TreeSpecies species) {
        this(type, species, Tree.DEFAULT_DIRECTION);
    }
    
    public Tree(final Material type, final TreeSpecies species, final BlockFace dir) {
        super(type, species);
        this.setDirection(dir);
    }
    
    @Deprecated
    public Tree(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Tree(final Material type, final byte data) {
        super(type, data);
    }
    
    public BlockFace getDirection() {
        switch (this.getData() >> 2 & 0x3) {
            default: {
                return BlockFace.UP;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SELF;
            }
        }
    }
    
    public void setDirection(final BlockFace dir) {
        int dat = 0;
        switch (dir) {
            default: {
                dat = 0;
                break;
            }
            case EAST:
            case WEST: {
                dat = 4;
                break;
            }
            case NORTH:
            case SOUTH: {
                dat = 8;
                break;
            }
            case SELF: {
                dat = 12;
                break;
            }
        }
        this.setData((byte)((this.getData() & 0x3) | dat));
    }
    
    @Override
    public String toString() {
        return this.getSpecies() + " " + this.getDirection() + " " + super.toString();
    }
    
    @Override
    public Tree clone() {
        return (Tree)super.clone();
    }
}
