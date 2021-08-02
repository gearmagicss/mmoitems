// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;

public interface Horse extends Animals, Vehicle, InventoryHolder, Tameable
{
    Variant getVariant();
    
    void setVariant(final Variant p0);
    
    Color getColor();
    
    void setColor(final Color p0);
    
    Style getStyle();
    
    void setStyle(final Style p0);
    
    boolean isCarryingChest();
    
    void setCarryingChest(final boolean p0);
    
    int getDomestication();
    
    void setDomestication(final int p0);
    
    int getMaxDomestication();
    
    void setMaxDomestication(final int p0);
    
    double getJumpStrength();
    
    void setJumpStrength(final double p0);
    
    HorseInventory getInventory();
    
    public enum Color
    {
        WHITE("WHITE", 0), 
        CREAMY("CREAMY", 1), 
        CHESTNUT("CHESTNUT", 2), 
        BROWN("BROWN", 3), 
        BLACK("BLACK", 4), 
        GRAY("GRAY", 5), 
        DARK_BROWN("DARK_BROWN", 6);
        
        private Color(final String name, final int ordinal) {
        }
    }
    
    public enum Style
    {
        NONE("NONE", 0), 
        WHITE("WHITE", 1), 
        WHITEFIELD("WHITEFIELD", 2), 
        WHITE_DOTS("WHITE_DOTS", 3), 
        BLACK_DOTS("BLACK_DOTS", 4);
        
        private Style(final String name, final int ordinal) {
        }
    }
    
    public enum Variant
    {
        HORSE("HORSE", 0), 
        DONKEY("DONKEY", 1), 
        MULE("MULE", 2), 
        UNDEAD_HORSE("UNDEAD_HORSE", 3), 
        SKELETON_HORSE("SKELETON_HORSE", 4);
        
        private Variant(final String name, final int ordinal) {
        }
    }
}
