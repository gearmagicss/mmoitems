// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.util;

import java.lang.reflect.Array;

public class Java15Compat
{
    public static <T> T[] Arrays_copyOfRange(final T[] original, final int start, final int end) {
        if (original.length < start || start < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (start <= end) {
            final int length = end - start;
            final int copyLength = Math.min(length, original.length - start);
            final Object[] copy = (Object[])Array.newInstance(original.getClass().getComponentType(), length);
            System.arraycopy(original, start, copy, 0, copyLength);
            return (T[])copy;
        }
        throw new IllegalArgumentException();
    }
}
