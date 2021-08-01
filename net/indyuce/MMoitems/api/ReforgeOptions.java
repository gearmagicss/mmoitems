// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class ReforgeOptions
{
    public static boolean dropRestoredGems;
    private final boolean keepName;
    private final boolean keepLore;
    private final boolean keepEnchantments;
    private final boolean keepUpgrades;
    private final boolean keepGemStones;
    private final boolean keepSoulbind;
    private final boolean keepExternalSH;
    private final boolean keepModifications;
    private final boolean reroll;
    private final boolean keepAdvancedEnchantments;
    @NotNull
    String keepCase;
    @NotNull
    ArrayList<String> blacklistedItems;
    
    public void setKeepCase(@NotNull final String keepCase) {
        this.keepCase = keepCase;
    }
    
    @NotNull
    public String getKeepCase() {
        return this.keepCase;
    }
    
    public boolean isBlacklisted(@NotNull final String o) {
        return this.blacklistedItems.contains(o);
    }
    
    public void addToBlacklist(@NotNull final String e) {
        this.blacklistedItems.add(e);
    }
    
    public void clearBlacklist() {
        this.blacklistedItems.clear();
    }
    
    public ReforgeOptions(final ConfigurationSection configurationSection) {
        this.keepCase = ChatColor.GRAY.toString();
        this.blacklistedItems = new ArrayList<String>();
        this.keepName = configurationSection.getBoolean("display-name");
        this.keepLore = configurationSection.getBoolean("lore");
        this.keepEnchantments = configurationSection.getBoolean("enchantments");
        this.keepUpgrades = configurationSection.getBoolean("upgrades");
        this.keepGemStones = configurationSection.getBoolean("gemstones");
        this.keepSoulbind = configurationSection.getBoolean("soulbound");
        this.keepCase = configurationSection.getString("kept-lore-prefix", ChatColor.GRAY.toString());
        this.keepExternalSH = configurationSection.getBoolean("external-sh", true);
        this.keepModifications = configurationSection.getBoolean("modifications");
        this.reroll = configurationSection.getBoolean("reroll");
        this.keepAdvancedEnchantments = configurationSection.getBoolean("advanced-enchantments");
    }
    
    public ReforgeOptions(final boolean... array) {
        this.keepCase = ChatColor.GRAY.toString();
        this.blacklistedItems = new ArrayList<String>();
        this.keepName = this.arr(array, 0);
        this.keepLore = this.arr(array, 1);
        this.keepEnchantments = this.arr(array, 2);
        this.keepUpgrades = this.arr(array, 3);
        this.keepGemStones = this.arr(array, 4);
        this.keepSoulbind = this.arr(array, 5);
        this.keepExternalSH = this.arr(array, 6);
        this.reroll = this.arr(array, 7);
        this.keepModifications = this.arr(array, 8);
        this.keepAdvancedEnchantments = this.arr(array, 9);
    }
    
    boolean arr(@NotNull final boolean[] array, final int n) {
        return array.length > n && array[n];
    }
    
    public boolean shouldReroll() {
        return this.reroll;
    }
    
    public boolean shouldKeepName() {
        return this.keepName;
    }
    
    public boolean shouldKeepMods() {
        return this.keepModifications;
    }
    
    public boolean shouldKeepLore() {
        return this.keepLore;
    }
    
    public boolean shouldKeepEnchantments() {
        return this.keepEnchantments;
    }
    
    public boolean shouldKeepAdvancedEnchants() {
        return this.keepAdvancedEnchantments;
    }
    
    public boolean shouldKeepExternalSH() {
        return this.keepExternalSH;
    }
    
    public boolean shouldKeepUpgrades() {
        return this.keepUpgrades;
    }
    
    public boolean shouldKeepGemStones() {
        return this.keepGemStones;
    }
    
    public boolean shouldKeepSoulbind() {
        return this.keepSoulbind;
    }
}
