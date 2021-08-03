// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class GenerateCommandHandler
{
    private final Set<String> arguments;
    
    public GenerateCommandHandler(final String... array) {
        this.arguments = new HashSet<String>();
        for (int length = array.length, i = 0; i < length; ++i) {
            this.arguments.add(array[i].toLowerCase());
        }
    }
    
    public boolean hasArgument(final String str) {
        final Iterator<String> iterator = this.arguments.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().startsWith("-" + str)) {
                return true;
            }
        }
        return false;
    }
    
    public String getValue(final String s) {
        for (final String s2 : this.arguments) {
            if (s2.startsWith("-" + s + ":")) {
                return s2.substring(s.length() + 2);
            }
        }
        throw new IllegalArgumentException("Command has no argument '" + s + "'");
    }
}
