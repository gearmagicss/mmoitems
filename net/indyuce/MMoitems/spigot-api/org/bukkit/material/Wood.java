// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.Material;

public class Wood extends MaterialData
{
    protected static final Material DEFAULT_TYPE;
    protected static final TreeSpecies DEFAULT_SPECIES;
    
    static {
        DEFAULT_TYPE = Material.WOOD;
        DEFAULT_SPECIES = TreeSpecies.GENERIC;
    }
    
    public Wood() {
        this(Wood.DEFAULT_TYPE, Wood.DEFAULT_SPECIES);
    }
    
    public Wood(final TreeSpecies species) {
        this(Wood.DEFAULT_TYPE, species);
    }
    
    @Deprecated
    public Wood(final int type) {
        super(type);
    }
    
    public Wood(final Material type) {
        this(type, Wood.DEFAULT_SPECIES);
    }
    
    public Wood(final Material type, final TreeSpecies species) {
        super(getSpeciesType(type, species));
        this.setSpecies(species);
    }
    
    @Deprecated
    public Wood(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Wood(final Material type, final byte data) {
        super(type, data);
    }
    
    public TreeSpecies getSpecies() {
        switch (this.getItemType()) {
            case WOOD:
            case WOOD_DOUBLE_STEP: {
                return TreeSpecies.getByData(this.getData());
            }
            case LOG:
            case LEAVES: {
                return TreeSpecies.getByData((byte)(this.getData() & 0x3));
            }
            case LEAVES_2:
            case LOG_2: {
                return TreeSpecies.getByData((byte)((this.getData() & 0x3) | 0x4));
            }
            case SAPLING:
            case WOOD_STEP: {
                return TreeSpecies.getByData((byte)(this.getData() & 0x7));
            }
            default: {
                throw new IllegalArgumentException("Invalid block type for tree species");
            }
        }
    }
    
    private static Material getSpeciesType(final Material type, final TreeSpecies species) {
        Label_0128: {
            switch (species) {
                case GENERIC:
                case REDWOOD:
                case BIRCH:
                case JUNGLE: {
                    switch (type) {
                        case LOG_2: {
                            return Material.LOG;
                        }
                        case LEAVES_2: {
                            return Material.LEAVES;
                        }
                        default: {
                            break Label_0128;
                        }
                    }
                    break;
                }
                case ACACIA:
                case DARK_OAK: {
                    switch (type) {
                        case LOG: {
                            return Material.LOG_2;
                        }
                        case LEAVES: {
                            return Material.LEAVES_2;
                        }
                        default: {
                            break Label_0128;
                        }
                    }
                    break;
                }
            }
        }
        return type;
    }
    
    public void setSpecies(final TreeSpecies species) {
        boolean firstType = false;
        switch (this.getItemType()) {
            case WOOD:
            case WOOD_DOUBLE_STEP: {
                this.setData(species.getData());
                break;
            }
            case LOG:
            case LEAVES: {
                firstType = true;
            }
            case LEAVES_2:
            case LOG_2: {
                switch (species) {
                    case GENERIC:
                    case REDWOOD:
                    case BIRCH:
                    case JUNGLE: {
                        if (!firstType) {
                            throw new IllegalArgumentException("Invalid tree species for block type, use block type 2 instead");
                        }
                        break;
                    }
                    case ACACIA:
                    case DARK_OAK: {
                        if (firstType) {
                            throw new IllegalArgumentException("Invalid tree species for block type 2, use block type instead");
                        }
                        break;
                    }
                }
                this.setData((byte)((this.getData() & 0xC) | (species.getData() & 0x3)));
                break;
            }
            case SAPLING:
            case WOOD_STEP: {
                this.setData((byte)((this.getData() & 0x8) | species.getData()));
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid block type for tree species");
            }
        }
    }
    
    @Override
    public String toString() {
        return this.getSpecies() + " " + super.toString();
    }
    
    @Override
    public Wood clone() {
        return (Wood)super.clone();
    }
}
