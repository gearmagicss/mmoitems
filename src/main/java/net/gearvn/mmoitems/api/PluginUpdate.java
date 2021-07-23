// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import java.util.Arrays;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Consumer;

public class PluginUpdate
{
    private final int id;
    private final Consumer<CommandSender> handler;
    private final List<String> description;
    
    public PluginUpdate(final int id, final String[] a, final Consumer<CommandSender> handler) {
        Validate.notNull((Object)handler, "Update handler must not be null");
        Validate.notNull((Object)a, "Update description must not be null");
        this.id = id;
        this.handler = handler;
        this.description = Arrays.asList(a);
    }
    
    public int getId() {
        return this.id;
    }
    
    public void apply(final CommandSender commandSender) {
        this.handler.accept((Object)commandSender);
    }
    
    public List<String> getDescription() {
        return this.description;
    }
    
    public boolean hasDescription() {
        return !this.description.isEmpty();
    }
}
