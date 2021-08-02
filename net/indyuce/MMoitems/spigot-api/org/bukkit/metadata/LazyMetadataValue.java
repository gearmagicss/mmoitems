// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.metadata;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import java.lang.ref.SoftReference;
import java.util.concurrent.Callable;

public class LazyMetadataValue extends MetadataValueAdapter implements MetadataValue
{
    private Callable<Object> lazyValue;
    private CacheStrategy cacheStrategy;
    private SoftReference<Object> internalValue;
    private static final Object ACTUALLY_NULL;
    
    static {
        ACTUALLY_NULL = new Object();
    }
    
    public LazyMetadataValue(final Plugin owningPlugin, final Callable<Object> lazyValue) {
        this(owningPlugin, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
    }
    
    public LazyMetadataValue(final Plugin owningPlugin, final CacheStrategy cacheStrategy, final Callable<Object> lazyValue) {
        super(owningPlugin);
        Validate.notNull(cacheStrategy, "cacheStrategy cannot be null");
        Validate.notNull(lazyValue, "lazyValue cannot be null");
        this.internalValue = new SoftReference<Object>(null);
        this.lazyValue = lazyValue;
        this.cacheStrategy = cacheStrategy;
    }
    
    protected LazyMetadataValue(final Plugin owningPlugin) {
        super(owningPlugin);
    }
    
    @Override
    public Object value() {
        this.eval();
        final Object value = this.internalValue.get();
        if (value == LazyMetadataValue.ACTUALLY_NULL) {
            return null;
        }
        return value;
    }
    
    private synchronized void eval() throws MetadataEvaluationException {
        if (this.cacheStrategy != CacheStrategy.NEVER_CACHE) {
            if (this.internalValue.get() != null) {
                return;
            }
        }
        try {
            Object value = this.lazyValue.call();
            if (value == null) {
                value = LazyMetadataValue.ACTUALLY_NULL;
            }
            this.internalValue = new SoftReference<Object>(value);
        }
        catch (Exception e) {
            throw new MetadataEvaluationException(e);
        }
    }
    
    @Override
    public synchronized void invalidate() {
        if (this.cacheStrategy != CacheStrategy.CACHE_ETERNALLY) {
            this.internalValue.clear();
        }
    }
    
    public enum CacheStrategy
    {
        CACHE_AFTER_FIRST_EVAL("CACHE_AFTER_FIRST_EVAL", 0), 
        NEVER_CACHE("NEVER_CACHE", 1), 
        CACHE_ETERNALLY("CACHE_ETERNALLY", 2);
        
        private CacheStrategy(final String name, final int ordinal) {
        }
    }
}
