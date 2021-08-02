// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

public class AuthorNagException extends RuntimeException
{
    private final String message;
    
    public AuthorNagException(final String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
}
