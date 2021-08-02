// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.util.io;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.io.Serializable;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BukkitObjectOutputStream extends ObjectOutputStream
{
    protected BukkitObjectOutputStream() throws IOException, SecurityException {
        super.enableReplaceObject(true);
    }
    
    public BukkitObjectOutputStream(final OutputStream out) throws IOException {
        super(out);
        super.enableReplaceObject(true);
    }
    
    @Override
    protected Object replaceObject(Object obj) throws IOException {
        if (!(obj instanceof Serializable) && obj instanceof ConfigurationSerializable) {
            obj = Wrapper.newWrapper((ConfigurationSerializable)obj);
        }
        return super.replaceObject(obj);
    }
}
