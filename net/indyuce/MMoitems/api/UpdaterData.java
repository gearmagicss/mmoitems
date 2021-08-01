// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import io.lumine.mythic.lib.api.item.NBTItem;
import org.apache.commons.lang.Validate;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.manager.UpdaterManager;
import java.util.Set;
import java.util.UUID;

public class UpdaterData
{
    private final Type type;
    private final String id;
    private UUID uuid;
    private final Set<UpdaterManager.KeepOption> options;
    
    public UpdaterData(final MMOItemTemplate mmoItemTemplate, final ConfigurationSection configurationSection) {
        this(mmoItemTemplate, UUID.fromString(configurationSection.getString("uuid")), new UpdaterManager.KeepOption[0]);
        for (final UpdaterManager.KeepOption keepOption : UpdaterManager.KeepOption.values()) {
            if (configurationSection.getBoolean(keepOption.getPath())) {
                this.options.add(keepOption);
            }
        }
    }
    
    public UpdaterData(final MMOItemTemplate mmoItemTemplate, final UUID uuid, final UpdaterManager.KeepOption... a) {
        this.options = new HashSet<UpdaterManager.KeepOption>();
        this.id = mmoItemTemplate.getId();
        this.type = mmoItemTemplate.getType();
        this.uuid = uuid;
        this.options.addAll(Arrays.asList(a));
    }
    
    public UpdaterData(final MMOItemTemplate mmoItemTemplate, final UUID uuid, final boolean b) {
        this(mmoItemTemplate, uuid, new UpdaterManager.KeepOption[0]);
        if (b) {
            this.options.addAll(Arrays.asList(UpdaterManager.KeepOption.values()));
        }
    }
    
    public void save(final ConfigurationSection configurationSection) {
        for (final UpdaterManager.KeepOption keepOption : UpdaterManager.KeepOption.values()) {
            if (this.options.contains(keepOption)) {
                configurationSection.set(keepOption.getPath(), (Object)true);
            }
        }
        configurationSection.set("uuid", (Object)this.uuid.toString());
    }
    
    public String getPath() {
        return this.type.getId() + "." + this.id;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public UUID getUniqueId() {
        return this.uuid;
    }
    
    public void setUniqueId(final UUID uuid) {
        Validate.notNull((Object)uuid, "UUID cannot be null");
        this.uuid = uuid;
    }
    
    public boolean matches(final NBTItem nbtItem) {
        return this.uuid.toString().equals(nbtItem.getString("MMOITEMS_ITEM_UUID"));
    }
    
    public boolean hasOption(final UpdaterManager.KeepOption keepOption) {
        return this.options.contains(keepOption);
    }
    
    public void addOption(final UpdaterManager.KeepOption keepOption) {
        this.options.add(keepOption);
    }
    
    public void removeOption(final UpdaterManager.KeepOption keepOption) {
        this.options.remove(keepOption);
    }
}
