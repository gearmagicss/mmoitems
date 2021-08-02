// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.util.io;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.io.Serializable;

class Wrapper<T extends Map> implements Serializable
{
    private static final long serialVersionUID = -986209235411767547L;
    final T map;
    
    static Wrapper<ImmutableMap<String, ?>> newWrapper(final ConfigurationSerializable obj) {
        return new Wrapper<ImmutableMap<String, ?>>(ImmutableMap.builder().put("==", ConfigurationSerialization.getAlias(obj.getClass())).putAll((java.util.Map<? extends String, ? extends String>)obj.serialize()).build());
    }
    
    private Wrapper(final T map) {
        this.map = map;
    }
}
