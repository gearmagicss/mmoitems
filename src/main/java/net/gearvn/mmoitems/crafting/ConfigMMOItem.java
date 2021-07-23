// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import net.Indyuce.mmoitems.api.ItemTier;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;

public class ConfigMMOItem
{
    private final MMOItemTemplate template;
    private final int amount;
    private ItemStack preview;
    
    public ConfigMMOItem(final ConfigurationSection configurationSection) {
        Validate.notNull((Object)configurationSection, "Could not read MMOItem config");
        Validate.isTrue(configurationSection.contains("type") && configurationSection.contains("id"), "Config must contain type and ID");
        this.template = MMOItems.plugin.getTemplates().getTemplateOrThrow(MMOItems.plugin.getTypes().getOrThrow(configurationSection.getString("type").toUpperCase().replace("-", "_").replace(" ", "_")), configurationSection.getString("id"));
        this.amount = Math.max(1, configurationSection.getInt("amount"));
    }
    
    public ConfigMMOItem(final MMOItemTemplate template, final int b) {
        Validate.notNull((Object)template, "Could not register recipe output");
        this.template = template;
        this.amount = Math.max(1, b);
    }
    
    @NotNull
    public ItemStack generate(@NotNull final RPGPlayer rpgPlayer) {
        final ItemStack build = this.template.newBuilder(rpgPlayer).build().newBuilder().build();
        build.setAmount(this.amount);
        return build;
    }
    
    public MMOItemTemplate getTemplate() {
        return this.template;
    }
    
    public ItemStack getPreview() {
        ItemStack itemStack;
        if (this.preview == null) {
            final ItemStack build = this.template.newBuilder(0, null).build().newBuilder().build(true);
            this.preview = build;
            itemStack = build.clone();
        }
        else {
            itemStack = this.preview.clone();
        }
        return itemStack;
    }
    
    public int getAmount() {
        return this.amount;
    }
}
