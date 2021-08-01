// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util;

import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Arrays;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;
import org.bukkit.Material;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class ConfigItem
{
    private final String id;
    private final ItemStack icon;
    private String name;
    private List<String> lore;
    private ItemStack item;
    @Nullable
    protected Material material;
    @Nullable
    protected Integer customModelData;
    
    public ConfigItem(final String s, final Material material) {
        this(s, material, null, new String[0]);
    }
    
    public ConfigItem(final String id, final Material material, final String name, final String... a) {
        this.material = null;
        this.customModelData = null;
        Validate.notNull((Object)id, "ID cannot be null");
        Validate.notNull((Object)material, "Material cannot be null");
        this.id = id;
        this.icon = new ItemStack(material);
        this.name = name;
        this.lore = Arrays.asList(a);
    }
    
    public ConfigItem(final ConfigurationSection configurationSection) {
        this.material = null;
        this.customModelData = null;
        Validate.notNull((Object)configurationSection, "Config cannot be null");
        this.id = configurationSection.getName();
        Validate.isTrue(configurationSection.contains("material"), "Could not find material");
        this.icon = MMOUtils.readIcon(configurationSection.getString("material"));
        this.name = configurationSection.getString("name", "");
        this.lore = (List<String>)configurationSection.getStringList("lore");
        this.updateItem();
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setup(final ConfigurationSection configurationSection) {
        configurationSection.set("name", (Object)this.getName());
        configurationSection.set("lore", (Object)this.getLore());
    }
    
    public void update(final ConfigurationSection configurationSection) {
        Validate.notNull((Object)configurationSection, "Config cannot be null");
        this.setName(configurationSection.getString("name", ""));
        this.setLore(configurationSection.contains("lore") ? configurationSection.getStringList("lore") : new ArrayList<String>());
        final String string = configurationSection.getString("material");
        if (string != null && !string.isEmpty()) {
            try {
                final Material value = Material.valueOf(configurationSection.getString("material"));
                if (value.isItem()) {
                    this.setMaterial(value);
                }
            }
            catch (IllegalArgumentException ex) {}
        }
        this.setModel(SilentNumbers.IntegerParse(configurationSection.getString("model")));
        this.updateItem();
    }
    
    public void updateItem() {
        this.setItem(this.icon);
        if (this.icon.getType() == Material.AIR) {
            return;
        }
        final ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.setDisplayName(MythicLib.plugin.parseColors(this.getName()));
        itemMeta.addItemFlags(ItemFlag.values());
        if (this.hasLore()) {
            final ArrayList<String> lore = new ArrayList<String>();
            this.getLore().forEach(s -> lore.add(ChatColor.GRAY + MythicLib.plugin.parseColors(s)));
            itemMeta.setLore((List)lore);
        }
        this.item.setItemMeta(itemMeta);
        this.item = MythicLib.plugin.getVersion().getWrapper().getNBTItem(this.item).addTag(new ItemTag[] { new ItemTag("ItemId", (Object)this.id) }).toItem();
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<String> getLore() {
        return this.lore;
    }
    
    public boolean hasLore() {
        return this.lore != null;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public ItemStack getNewItem() {
        return this.item.clone();
    }
    
    protected void setName(final String name) {
        this.name = name;
    }
    
    protected void setLore(final List<String> lore) {
        this.lore = lore;
    }
    
    protected void setItem(final ItemStack item) {
        this.item = item;
    }
    
    protected void setMaterial(@Nullable final Material material) {
        this.material = material;
    }
    
    protected void setModel(@Nullable final Integer customModelData) {
        this.customModelData = customModelData;
    }
}
