// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.util.io;

import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BukkitObjectInputStream extends ObjectInputStream
{
    protected BukkitObjectInputStream() throws IOException, SecurityException {
        super.enableResolveObject(true);
    }
    
    public BukkitObjectInputStream(final InputStream in) throws IOException {
        super(in);
        super.enableResolveObject(true);
    }
    
    @Override
    protected Object resolveObject(Object obj) throws IOException {
        if (obj instanceof Wrapper) {
            try {
                (obj = ConfigurationSerialization.deserializeObject((Map<String, ?>)((Wrapper)obj).map)).getClass();
            }
            catch (Throwable ex) {
                throw newIOException("Failed to deserialize object", ex);
            }
        }
        return super.resolveObject(obj);
    }
    
    private static IOException newIOException(final String string, final Throwable cause) {
        final IOException exception = new IOException(string);
        exception.initCause(cause);
        return exception;
    }
}
