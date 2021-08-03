// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.UpgradeTemplate;
import com.google.gson.JsonObject;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class UpgradeData implements StatData, RandomStatData, Cloneable
{
    @Nullable
    private final String reference;
    @Nullable
    private final String template;
    private final boolean workbench;
    private final boolean destroy;
    private final double success;
    private final int max;
    private int level;
    
    @Nullable
    public String getReference() {
        return this.reference;
    }
    
    public boolean isWorkbench() {
        return this.workbench;
    }
    
    public boolean isDestroy() {
        return this.destroy;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public UpgradeData(@Nullable final String reference, @Nullable final String template, final boolean workbench, final boolean destroy, final int max, final double success) {
        this.reference = reference;
        this.template = template;
        this.workbench = workbench;
        this.destroy = destroy;
        this.max = max;
        this.success = success;
    }
    
    public UpgradeData(final ConfigurationSection configurationSection) {
        this.reference = configurationSection.getString("reference");
        this.template = configurationSection.getString("template");
        this.workbench = configurationSection.getBoolean("workbench");
        this.destroy = configurationSection.getBoolean("destroy");
        this.max = configurationSection.getInt("max");
        this.success = configurationSection.getDouble("success") / 100.0;
    }
    
    public UpgradeData(final JsonObject jsonObject) {
        this.workbench = jsonObject.get("Workbench").getAsBoolean();
        this.destroy = jsonObject.get("Destroy").getAsBoolean();
        this.template = (jsonObject.has("Template") ? jsonObject.get("Template").getAsString() : null);
        this.reference = (jsonObject.has("Reference") ? jsonObject.get("Reference").getAsString() : null);
        this.level = jsonObject.get("Level").getAsInt();
        this.max = jsonObject.get("Max").getAsInt();
        this.success = jsonObject.get("Success").getAsDouble();
    }
    
    @Nullable
    public UpgradeTemplate getTemplate() {
        if (this.template == null) {
            return null;
        }
        return MMOItems.plugin.getUpgrades().getTemplate(this.template);
    }
    
    @Nullable
    public String getTemplateName() {
        return this.template;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    public int getMaxUpgrades() {
        return this.max;
    }
    
    public boolean canLevelUp() {
        return this.max == 0 || this.level < this.max;
    }
    
    public boolean destroysOnFail() {
        return this.destroy;
    }
    
    public double getSuccess() {
        return (this.success == 0.0) ? 1.0 : this.success;
    }
    
    public boolean matchesReference(final UpgradeData upgradeData) {
        return this.reference == null || upgradeData.reference == null || this.reference.isEmpty() || upgradeData.reference.isEmpty() || this.reference.equals(upgradeData.reference);
    }
    
    public void upgrade(@NotNull final MMOItem mmoItem) {
        if (this.getTemplate() == null) {
            MMOItems.plugin.getLogger().warning("Couldn't find upgrade template '" + this.template + "'. Does it exist?");
            return;
        }
        this.getTemplate().upgrade(mmoItem);
    }
    
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        if (this.reference != null && !this.reference.isEmpty()) {
            jsonObject.addProperty("Reference", this.reference);
        }
        if (this.template != null && !this.template.isEmpty()) {
            jsonObject.addProperty("Template", this.template);
        }
        jsonObject.addProperty("Workbench", Boolean.valueOf(this.workbench));
        jsonObject.addProperty("Destroy", Boolean.valueOf(this.destroy));
        jsonObject.addProperty("Level", (Number)this.level);
        jsonObject.addProperty("Max", (Number)this.max);
        jsonObject.addProperty("Success", (Number)this.success);
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return this.toJson().toString();
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
    
    public UpgradeData clone() {
        try {
            super.clone();
        }
        catch (CloneNotSupportedException ex) {}
        return new UpgradeData(this.reference, this.template, this.workbench, this.destroy, this.max, this.success);
    }
}
