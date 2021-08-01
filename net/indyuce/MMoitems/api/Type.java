// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import javax.annotation.Nullable;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.Material;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.manager.TypeManager;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.List;
import net.Indyuce.mmoitems.api.item.util.identify.UnidentifiedItem;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.player.EquipmentSlot;

public class Type
{
    public static final Type SWORD;
    public static final Type DAGGER;
    public static final Type SPEAR;
    public static final Type HAMMER;
    public static final Type GAUNTLET;
    public static final Type WHIP;
    public static final Type STAFF;
    public static final Type BOW;
    public static final Type CROSSBOW;
    public static final Type MUSKET;
    public static final Type LUTE;
    public static final Type CATALYST;
    public static final Type OFF_CATALYST;
    public static final Type ORNAMENT;
    public static final Type ARMOR;
    public static final Type TOOL;
    public static final Type CONSUMABLE;
    public static final Type MISCELLANEOUS;
    public static final Type GEM_STONE;
    public static final Type SKIN;
    public static final Type ACCESSORY;
    public static final Type BLOCK;
    private final String id;
    private String name;
    private final TypeSet set;
    private final boolean weapon;
    private final io.lumine.mythic.lib.api.player.EquipmentSlot equipType;
    private ItemStack item;
    private Type parent;
    private UnidentifiedItem unidentifiedTemplate;
    private final List<ItemStat> available;
    
    public Type(final TypeSet set, final String s, final boolean weapon, final io.lumine.mythic.lib.api.player.EquipmentSlot equipType) {
        this.available = new ArrayList<ItemStat>();
        this.set = set;
        this.id = s.toUpperCase().replace("-", "_").replace(" ", "_");
        this.equipType = equipType;
        this.weapon = weapon;
    }
    
    public Type(final TypeManager typeManager, final ConfigurationSection configurationSection) {
        this.available = new ArrayList<ItemStat>();
        this.id = configurationSection.getName().toUpperCase().replace("-", "_").replace(" ", "_");
        this.parent = typeManager.get(configurationSection.getString("parent").toUpperCase().replace("-", "_").replace(" ", "_"));
        this.set = this.parent.set;
        this.weapon = this.parent.weapon;
        this.equipType = this.parent.equipType;
    }
    
    public void load(final ConfigurationSection configurationSection) {
        Validate.notNull((Object)configurationSection, "Could not find config for " + this.getId());
        Validate.notNull((Object)(this.name = configurationSection.getString("name")), "Could not read name");
        Validate.notNull((Object)(this.item = this.read(configurationSection.getString("display"))), "Could not read item");
        (this.unidentifiedTemplate = new UnidentifiedItem(this)).update(configurationSection.getConfigurationSection("unident-item"));
    }
    
    @Deprecated
    public String name() {
        return this.getId();
    }
    
    public String getId() {
        return this.id;
    }
    
    public TypeSet getItemSet() {
        return this.set;
    }
    
    public boolean isWeapon() {
        return this.weapon;
    }
    
    public String getName() {
        return this.name;
    }
    
    public io.lumine.mythic.lib.api.player.EquipmentSlot getEquipmentType() {
        return this.equipType;
    }
    
    public ItemStack getItem() {
        return this.item.clone();
    }
    
    public boolean isSubtype() {
        return this.parent != null;
    }
    
    public Type getParent() {
        return this.parent;
    }
    
    public boolean corresponds(final Type type) {
        return this.equals(type) || (this.isSubtype() && this.getParent().equals(type));
    }
    
    public boolean corresponds(final TypeSet set) {
        return this.getItemSet() == set;
    }
    
    public List<ItemStat> getAvailableStats() {
        return this.available;
    }
    
    public ConfigFile getConfigFile() {
        return new ConfigFile("/item", this.getId().toLowerCase());
    }
    
    public UnidentifiedItem getUnidentifiedTemplate() {
        return this.unidentifiedTemplate;
    }
    
    @Deprecated
    public boolean canHave(final ItemStat itemStat) {
        return itemStat.isCompatible(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Type && ((Type)o).id.equals(this.id);
    }
    
    private ItemStack read(final String s) {
        Validate.notNull((Object)s, "Input must not be null");
        final String[] split = s.split(":");
        final Material value = Material.valueOf(split[0]);
        return (split.length > 1) ? MythicLib.plugin.getVersion().getWrapper().textureItem(value, Integer.parseInt(split[1])) : new ItemStack(value);
    }
    
    @Override
    public String toString() {
        return this.getId();
    }
    
    @Nullable
    public static Type get(@Nullable final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return get(NBTItem.get(itemStack).getType());
    }
    
    @Nullable
    public static Type get(@Nullable final String s) {
        if (s == null) {
            return null;
        }
        final String replace = s.toUpperCase().replace("-", "_").replace(" ", "_");
        return MMOItems.plugin.getTypes().has(replace) ? MMOItems.plugin.getTypes().get(replace) : null;
    }
    
    public static boolean isValid(@Nullable final String s) {
        return s != null && MMOItems.plugin.getTypes().has(s.toUpperCase().replace("-", "_").replace(" ", "_"));
    }
    
    static {
        SWORD = new Type(TypeSet.SLASHING, "SWORD", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        DAGGER = new Type(TypeSet.PIERCING, "DAGGER", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        SPEAR = new Type(TypeSet.PIERCING, "SPEAR", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        HAMMER = new Type(TypeSet.BLUNT, "HAMMER", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        GAUNTLET = new Type(TypeSet.BLUNT, "GAUNTLET", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        WHIP = new Type(TypeSet.RANGE, "WHIP", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        STAFF = new Type(TypeSet.RANGE, "STAFF", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        BOW = new Type(TypeSet.RANGE, "BOW", true, io.lumine.mythic.lib.api.player.EquipmentSlot.BOTH_HANDS);
        CROSSBOW = new Type(TypeSet.RANGE, "CROSSBOW", false, io.lumine.mythic.lib.api.player.EquipmentSlot.BOTH_HANDS);
        MUSKET = new Type(TypeSet.RANGE, "MUSKET", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        LUTE = new Type(TypeSet.RANGE, "LUTE", true, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        CATALYST = new Type(TypeSet.OFFHAND, "CATALYST", false, io.lumine.mythic.lib.api.player.EquipmentSlot.BOTH_HANDS);
        OFF_CATALYST = new Type(TypeSet.OFFHAND, "OFF_CATALYST", false, io.lumine.mythic.lib.api.player.EquipmentSlot.OFF_HAND);
        ORNAMENT = new Type(TypeSet.EXTRA, "ORNAMENT", false, io.lumine.mythic.lib.api.player.EquipmentSlot.ANY);
        ARMOR = new Type(TypeSet.EXTRA, "ARMOR", false, io.lumine.mythic.lib.api.player.EquipmentSlot.ARMOR);
        TOOL = new Type(TypeSet.EXTRA, "TOOL", false, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        CONSUMABLE = new Type(TypeSet.EXTRA, "CONSUMABLE", false, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        MISCELLANEOUS = new Type(TypeSet.EXTRA, "MISCELLANEOUS", false, io.lumine.mythic.lib.api.player.EquipmentSlot.MAIN_HAND);
        GEM_STONE = new Type(TypeSet.EXTRA, "GEM_STONE", false, io.lumine.mythic.lib.api.player.EquipmentSlot.OTHER);
        SKIN = new Type(TypeSet.EXTRA, "SKIN", false, io.lumine.mythic.lib.api.player.EquipmentSlot.OTHER);
        ACCESSORY = new Type(TypeSet.EXTRA, "ACCESSORY", false, io.lumine.mythic.lib.api.player.EquipmentSlot.ACCESSORY);
        BLOCK = new Type(TypeSet.EXTRA, "BLOCK", false, io.lumine.mythic.lib.api.player.EquipmentSlot.OTHER);
    }
    
    public enum EquipmentSlot
    {
        ARMOR, 
        ACCESSORY, 
        OTHER, 
        ANY, 
        MAIN_HAND, 
        OFF_HAND, 
        BOTH_HANDS, 
        EITHER_HAND;
        
        public boolean isHand() {
            return this == EquipmentSlot.MAIN_HAND || this == EquipmentSlot.OFF_HAND || this == EquipmentSlot.BOTH_HANDS;
        }
        
        private static /* synthetic */ EquipmentSlot[] $values() {
            return new EquipmentSlot[] { EquipmentSlot.ARMOR, EquipmentSlot.ACCESSORY, EquipmentSlot.OTHER, EquipmentSlot.ANY, EquipmentSlot.MAIN_HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.BOTH_HANDS, EquipmentSlot.EITHER_HAND };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
