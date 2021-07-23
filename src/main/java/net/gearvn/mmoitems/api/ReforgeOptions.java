// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
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
    private final boolean regenerate;
    @NotNull
    String keepCase;
    
    public boolean isRegenerate() {
        return this.regenerate;
    }
    
    public void setKeepCase(@NotNull final String keepCase) {
        this.keepCase = keepCase;
    }
    
    @NotNull
    public String getKeepCase() {
        return this.keepCase;
    }
    
    public ReforgeOptions(final ConfigurationSection configurationSection) {
        this.keepCase = ChatColor.GRAY.toString();
        this.keepName = configurationSection.getBoolean("display-name");
        this.keepLore = configurationSection.getBoolean("lore");
        this.keepEnchantments = configurationSection.getBoolean("enchantments");
        this.keepUpgrades = configurationSection.getBoolean("upgrades");
        this.keepGemStones = configurationSection.getBoolean("gemstones");
        this.keepSoulbind = configurationSection.getBoolean("soulbound");
        this.keepCase = configurationSection.getString("kept-lore-prefix", ChatColor.GRAY.toString());
        this.keepExternalSH = configurationSection.getBoolean("external-sh", true);
        this.regenerate = false;
    }
    
    public ReforgeOptions(final boolean keepName, final boolean keepLore, final boolean keepEnchantments, final boolean keepUpgrades, final boolean keepGemStones, final boolean keepSoulbind, final boolean keepExternalSH, final boolean regenerate) {
        this.keepCase = ChatColor.GRAY.toString();
        this.keepName = keepName;
        this.keepLore = keepLore;
        this.keepEnchantments = keepEnchantments;
        this.keepUpgrades = keepUpgrades;
        this.keepGemStones = keepGemStones;
        this.keepSoulbind = keepSoulbind;
        this.keepExternalSH = keepExternalSH;
        this.regenerate = regenerate;
    }
    
    public boolean shouldKeepName() {
        return this.keepName;
    }
    
    public boolean shouldKeepLore() {
        return this.keepLore;
    }
    
    public boolean shouldKeepEnchantments() {
        return this.keepEnchantments;
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
