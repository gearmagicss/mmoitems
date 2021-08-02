// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.permissions;

import java.util.HashMap;
import java.util.Map;

public enum PermissionDefault
{
    TRUE("TRUE", 0, new String[] { "true" }), 
    FALSE("FALSE", 1, new String[] { "false" }), 
    OP("OP", 2, new String[] { "op", "isop", "operator", "isoperator", "admin", "isadmin" }), 
    NOT_OP("NOT_OP", 3, new String[] { "!op", "notop", "!operator", "notoperator", "!admin", "notadmin" });
    
    private final String[] names;
    private static final Map<String, PermissionDefault> lookup;
    
    static {
        lookup = new HashMap<String, PermissionDefault>();
        PermissionDefault[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PermissionDefault value = values[i];
            String[] names;
            for (int length2 = (names = value.names).length, j = 0; j < length2; ++j) {
                final String name = names[j];
                PermissionDefault.lookup.put(name, value);
            }
        }
    }
    
    private PermissionDefault(final String name, final int ordinal, final String... names) {
        this.names = names;
    }
    
    public boolean getValue(final boolean op) {
        switch (this) {
            case TRUE: {
                return true;
            }
            case FALSE: {
                return false;
            }
            case OP: {
                return op;
            }
            case NOT_OP: {
                return !op;
            }
            default: {
                return false;
            }
        }
    }
    
    public static PermissionDefault getByName(final String name) {
        return PermissionDefault.lookup.get(name.toLowerCase().replaceAll("[^a-z!]", ""));
    }
    
    @Override
    public String toString() {
        return this.names[0];
    }
}
