// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface Rabbit extends Animals
{
    Type getRabbitType();
    
    void setRabbitType(final Type p0);
    
    public enum Type
    {
        BROWN("BROWN", 0), 
        WHITE("WHITE", 1), 
        BLACK("BLACK", 2), 
        BLACK_AND_WHITE("BLACK_AND_WHITE", 3), 
        GOLD("GOLD", 4), 
        SALT_AND_PEPPER("SALT_AND_PEPPER", 5), 
        THE_KILLER_BUNNY("THE_KILLER_BUNNY", 6);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
