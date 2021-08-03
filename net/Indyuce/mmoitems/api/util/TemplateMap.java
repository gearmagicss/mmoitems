// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.function.Consumer;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.Type;
import java.util.HashMap;
import java.util.Map;

public class TemplateMap<C>
{
    private final Map<String, Submap> typeMap;
    
    public TemplateMap() {
        this.typeMap = new HashMap<String, Submap>();
    }
    
    public boolean hasValue(@Nullable final Type type, @Nullable final String s) {
        return type != null && s != null && this.typeMap.containsKey(type.getId()) && this.typeMap.get(type.getId()).idMap.containsKey(s);
    }
    
    @Nullable
    public C getValue(@Nullable final Type type, @Nullable final String s) {
        if (type == null || s == null) {
            return null;
        }
        final Submap submap = this.typeMap.get(type.getId());
        if (submap == null) {
            return null;
        }
        return (C)submap.idMap.get(s);
    }
    
    public void removeValue(@Nullable final Type type, @Nullable final String s) {
        if (type == null || s == null) {
            return;
        }
        if (this.typeMap.containsKey(type.getId())) {
            this.typeMap.get(type.getId()).idMap.remove(s);
        }
    }
    
    public void setValue(@NotNull final Type type, @NotNull final String s, @NotNull final C c) {
        Validate.notNull((Object)c, "Value cannot be null");
        if (!this.typeMap.containsKey(type.getId())) {
            this.typeMap.put(type.getId(), new Submap());
        }
        this.typeMap.get(type.getId()).idMap.put(s, c);
    }
    
    public void forEach(@NotNull final Consumer<C> action) {
        this.typeMap.values().forEach(submap -> submap.idMap.values().forEach(action));
    }
    
    @NotNull
    public Collection<C> collectValues() {
        final HashSet<Object> set = new HashSet<Object>();
        this.typeMap.values().forEach(submap -> set.addAll(submap.idMap.values()));
        return (Collection<C>)set;
    }
    
    @NotNull
    public Collection<C> collectValues(@NotNull final Type type) {
        return this.typeMap.containsKey(type.getId()) ? this.typeMap.get(type.getId()).idMap.values() : new HashSet<C>();
    }
    
    public void clear() {
        this.typeMap.clear();
    }
    
    private class Submap
    {
        private final Map<String, C> idMap;
        
        private Submap() {
            this.idMap = new LinkedHashMap<String, C>();
        }
    }
}
