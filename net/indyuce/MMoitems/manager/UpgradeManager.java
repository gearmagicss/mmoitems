// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.UpgradeTemplate;
import java.util.Map;

public class UpgradeManager implements Reloadable
{
    private final Map<String, UpgradeTemplate> templates;
    
    public UpgradeManager() {
        this.templates = new HashMap<String, UpgradeTemplate>();
        this.reload();
    }
    
    @Override
    public void reload() {
        this.templates.clear();
        final FileConfiguration config = new ConfigFile("upgrade-templates").getConfig();
        final Iterator iterator = config.getKeys(false).iterator();
        while (iterator.hasNext()) {
            this.registerTemplate(new UpgradeTemplate(config.getConfigurationSection((String)iterator.next())));
        }
    }
    
    public Collection<UpgradeTemplate> getAll() {
        return this.templates.values();
    }
    
    @Nullable
    public UpgradeTemplate getTemplate(@NotNull final String s) {
        return this.templates.get(s);
    }
    
    public boolean hasTemplate(final String s) {
        return this.templates.containsKey(s);
    }
    
    public void registerTemplate(final UpgradeTemplate upgradeTemplate) {
        this.templates.put(upgradeTemplate.getId(), upgradeTemplate);
    }
}
