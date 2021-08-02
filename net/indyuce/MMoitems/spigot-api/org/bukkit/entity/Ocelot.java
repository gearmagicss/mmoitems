// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface Ocelot extends Animals, Tameable
{
    Type getCatType();
    
    void setCatType(final Type p0);
    
    boolean isSitting();
    
    void setSitting(final boolean p0);
    
    public enum Type
    {
        WILD_OCELOT("WILD_OCELOT", 0, 0), 
        BLACK_CAT("BLACK_CAT", 1, 1), 
        RED_CAT("RED_CAT", 2, 2), 
        SIAMESE_CAT("SIAMESE_CAT", 3, 3);
        
        private static final Type[] types;
        private final int id;
        
        static {
            types = new Type[values().length];
            Type[] values;
            for (int length = (values = values()).length, i = 0; i < length; ++i) {
                final Type type = values[i];
                Type.types[type.getId()] = type;
            }
        }
        
        private Type(final String name, final int ordinal, final int id) {
            this.id = id;
        }
        
        @Deprecated
        public int getId() {
            return this.id;
        }
        
        @Deprecated
        public static Type getType(final int id) {
            return (id >= Type.types.length) ? null : Type.types[id];
        }
    }
}
