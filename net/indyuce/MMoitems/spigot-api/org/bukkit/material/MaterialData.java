// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class MaterialData implements Cloneable
{
    private final int type;
    private byte data;
    
    @Deprecated
    public MaterialData(final int type) {
        this(type, (byte)0);
    }
    
    public MaterialData(final Material type) {
        this(type, (byte)0);
    }
    
    @Deprecated
    public MaterialData(final int type, final byte data) {
        this.data = 0;
        this.type = type;
        this.data = data;
    }
    
    @Deprecated
    public MaterialData(final Material type, final byte data) {
        this(type.getId(), data);
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public void setData(final byte data) {
        this.data = data;
    }
    
    public Material getItemType() {
        return Material.getMaterial(this.type);
    }
    
    @Deprecated
    public int getItemTypeId() {
        return this.type;
    }
    
    public ItemStack toItemStack() {
        return new ItemStack(this.type, 0, this.data);
    }
    
    public ItemStack toItemStack(final int amount) {
        return new ItemStack(this.type, amount, this.data);
    }
    
    @Override
    public String toString() {
        return this.getItemType() + "(" + this.getData() + ")";
    }
    
    @Override
    public int hashCode() {
        return this.getItemTypeId() << 8 ^ this.getData();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj instanceof MaterialData) {
            final MaterialData md = (MaterialData)obj;
            return md.getItemTypeId() == this.getItemTypeId() && md.getData() == this.getData();
        }
        return false;
    }
    
    public MaterialData clone() {
        try {
            return (MaterialData)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
}
