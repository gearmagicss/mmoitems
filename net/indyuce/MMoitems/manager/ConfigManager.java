// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import net.Indyuce.mmoitems.api.Type;
import java.util.List;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.GemUpgradeScaling;
import io.lumine.mythic.lib.MythicLib;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.item.util.ConfigItem;
import java.util.Enumeration;
import net.Indyuce.mmoitems.stat.LuteAttackEffectStat;
import net.Indyuce.mmoitems.stat.StaffSpiritStat;
import org.bukkit.potion.PotionEffectType;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.api.util.message.Message;
import java.util.Base64;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.util.ConfigItems;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.util.logging.Level;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.io.File;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ReforgeOptions;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import java.text.DecimalFormat;
import net.Indyuce.mmoitems.api.ConfigFile;

public class ConfigManager implements Reloadable
{
    private ConfigFile abilities;
    private ConfigFile loreFormat;
    private ConfigFile messages;
    private ConfigFile potionEffects;
    private ConfigFile stats;
    private ConfigFile attackEffects;
    private ConfigFile dynLore;
    public boolean abilityPlayerDamage;
    public boolean dodgeKnockbackEnabled;
    public boolean replaceMushroomDrops;
    public boolean worldGenEnabled;
    public boolean upgradeRequirementsCheck;
    public boolean keepSoulboundOnDeath;
    public boolean rerollOnItemUpdate;
    public String healIndicatorFormat;
    public String damageIndicatorFormat;
    public String abilitySplitter;
    public DecimalFormat healIndicatorDecimalFormat;
    public DecimalFormat damageIndicatorDecimalFormat;
    public double dodgeKnockbackForce;
    public double soulboundBaseDamage;
    public double soulboundPerLvlDamage;
    public double levelSpread;
    public NumericStatFormula defaultItemCapacity;
    public ReforgeOptions revisionOptions;
    public ReforgeOptions phatLootsOptions;
    private final String elGrifoReconocimiento = "38841";
    private static final String[] fileNames;
    private static final String[] languages;
    public String elDescargadorLaIdentidad;
    public final boolean arruinarElPrograma = false;
    
    public ConfigManager() {
        this.elDescargadorLaIdentidad = "mudamuda";
        this.mkdir("layouts");
        this.mkdir("item");
        this.mkdir("dynamic");
        this.mkdir("language");
        this.mkdir("language/lore-formats");
        this.mkdir("modifiers");
        final File file = new File(MMOItems.plugin.getDataFolder() + "/crafting-stations");
        if (!file.exists()) {
            if (file.mkdir()) {
                try {
                    final JarFile jarFile = new JarFile(MMOItems.plugin.getJarFile());
                    final Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        final String name = entries.nextElement().getName();
                        if (name.startsWith("default/crafting-stations/") && name.length() > "default/crafting-stations/".length()) {
                            Files.copy(MMOItems.plugin.getResource(name), new File(MMOItems.plugin.getDataFolder() + "/crafting-stations", name.split("/")[2]).toPath(), new CopyOption[0]);
                        }
                    }
                    jarFile.close();
                }
                catch (IOException ex2) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load default crafting stations.");
                }
            }
            else {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not create directory!");
            }
        }
        for (final String s : ConfigManager.languages) {
            final File file2 = new File(MMOItems.plugin.getDataFolder() + "/language/" + s);
            if (!file2.exists()) {
                if (file2.mkdir()) {
                    for (final String str : ConfigManager.fileNames) {
                        if (!new File(MMOItems.plugin.getDataFolder() + "/language/" + s, str + ".yml").exists()) {
                            try {
                                Files.copy(MMOItems.plugin.getResource("language/" + s + "/" + str + ".yml"), new File(MMOItems.plugin.getDataFolder() + "/language/" + s, str + ".yml").getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
                            }
                            catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                else {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load default crafting stations.");
                }
            }
        }
        for (final DefaultFile defaultFile : DefaultFile.values()) {
            if (defaultFile.isAutomatic()) {
                defaultFile.checkFile();
            }
        }
        MMOItems.plugin.getTypes().getAll().forEach(type -> type.getConfigFile().setup());
        final ConfigFile configFile = new ConfigFile("/language", "items");
        for (final ConfigItem configItem : ConfigItems.values) {
            if (!configFile.getConfig().contains(configItem.getId())) {
                configFile.getConfig().createSection(configItem.getId());
                configItem.setup(configFile.getConfig().getConfigurationSection(configItem.getId()));
            }
            configItem.update((ConfigurationSection)configFile.getConfig());
        }
        configFile.save();
        final byte[] decode = Base64.getDecoder().decode("ZWxHcmlmb1JlY29ub2NpbWllbnRv");
        final ConfigFile configFile2 = new ConfigFile("/language", "messages");
        for (final Message message : Message.values()) {
            final String replace = message.name().toLowerCase().replace("_", "-");
            if (!configFile2.getConfig().contains(replace)) {
                configFile2.getConfig().set(replace, (Object)message.getDefault());
            }
        }
        configFile2.save();
        final ConfigFile configFile3 = new ConfigFile("/language", "abilities");
        for (final Ability ability : MMOItems.plugin.getAbilities().getAllAbilities()) {
            final String lowerCaseID = ability.getLowerCaseID();
            if (!configFile3.getConfig().getKeys(true).contains("ability." + lowerCaseID)) {
                configFile3.getConfig().set("ability." + lowerCaseID, (Object)ability.getName());
            }
            for (final String s2 : ability.getModifiers()) {
                if (!configFile3.getConfig().getKeys(true).contains("modifier." + s2)) {
                    configFile3.getConfig().set("modifier." + s2, (Object)MMOUtils.caseOnWords(s2.replace("-", " ")));
                }
            }
        }
        for (final Ability.CastingMode castingMode : Ability.CastingMode.values()) {
            if (!configFile3.getConfig().contains("cast-mode." + castingMode.getLowerCaseId())) {
                configFile3.getConfig().set("cast-mode." + castingMode.getLowerCaseId(), (Object)castingMode.getName());
            }
        }
        configFile3.save();
        final ConfigFile configFile4 = new ConfigFile("/language", "potion-effects");
        for (final PotionEffectType potionEffectType : PotionEffectType.values()) {
            if (potionEffectType != null) {
                final String replace2 = potionEffectType.getName().toLowerCase().replace("_", "-");
                if (!configFile4.getConfig().contains(replace2)) {
                    configFile4.getConfig().set(replace2, (Object)MMOUtils.caseOnWords(potionEffectType.getName().toLowerCase().replace("_", " ")));
                }
            }
        }
        configFile4.save();
        final String name2 = new String(decode);
        try {
            this.elDescargadorLaIdentidad = Base64.getEncoder().encodeToString(((String)this.getClass().getDeclaredField(name2).get(this)).getBytes());
        }
        catch (IllegalAccessException | NoSuchFieldException ex3) {
            final Throwable t;
            t.printStackTrace();
        }
        final ConfigFile configFile5 = new ConfigFile("/language", "attack-effects");
        for (final StaffSpiritStat.StaffSpirit staffSpirit : StaffSpiritStat.StaffSpirit.values()) {
            final String replace3 = staffSpirit.name().toLowerCase().replace("_", "-");
            if (!configFile5.getConfig().contains("staff-spirit." + replace3)) {
                configFile5.getConfig().set("staff-spirit." + replace3, (Object)("&7\u25a0 " + staffSpirit.getDefaultName()));
            }
        }
        for (final LuteAttackEffectStat.LuteAttackEffect luteAttackEffect : LuteAttackEffectStat.LuteAttackEffect.values()) {
            final String replace4 = luteAttackEffect.name().toLowerCase().replace("_", "-");
            if (!configFile5.getConfig().contains("lute-attack." + replace4)) {
                configFile5.getConfig().set("lute-attack." + replace4, (Object)("&7\u25a0 " + luteAttackEffect.getDefaultName() + " Attacks"));
            }
        }
        configFile5.save();
        this.reload();
    }
    
    @Override
    public void reload() {
        MMOItems.plugin.reloadConfig();
        this.abilities = new ConfigFile("/language", "abilities");
        this.loreFormat = new ConfigFile("/language", "lore-format");
        this.messages = new ConfigFile("/language", "messages");
        this.potionEffects = new ConfigFile("/language", "potion-effects");
        this.stats = new ConfigFile("/language", "stats");
        this.attackEffects = new ConfigFile("/language", "attack-effects");
        this.dynLore = new ConfigFile("/language", "dynamic-lore");
        this.replaceMushroomDrops = MMOItems.plugin.getConfig().getBoolean("custom-blocks.replace-mushroom-drops");
        this.worldGenEnabled = MMOItems.plugin.getConfig().getBoolean("custom-blocks.enable-world-gen");
        this.abilityPlayerDamage = MMOItems.plugin.getConfig().getBoolean("ability-player-damage");
        this.healIndicatorFormat = MythicLib.plugin.parseColors(MMOItems.plugin.getConfig().getString("game-indicators.heal.format"));
        this.damageIndicatorFormat = MythicLib.plugin.parseColors(MMOItems.plugin.getConfig().getString("game-indicators.damage.format"));
        final String string = MMOItems.plugin.getConfig().getString("game-indicators.heal.decimal-format");
        final String string2 = MMOItems.plugin.getConfig().getString("game-indicators.damage.decimal-format");
        this.healIndicatorDecimalFormat = ((string != null) ? new DecimalFormat(string) : new DecimalFormat("0.#"));
        this.damageIndicatorDecimalFormat = ((string2 != null) ? new DecimalFormat(string2) : new DecimalFormat("0.#"));
        this.abilitySplitter = this.getStatFormat("ability-splitter");
        this.dodgeKnockbackForce = MMOItems.plugin.getConfig().getDouble("mitigation.dodge.knockback.force");
        this.dodgeKnockbackEnabled = MMOItems.plugin.getConfig().getBoolean("mitigation.dodge.knockback.enabled");
        this.soulboundBaseDamage = MMOItems.plugin.getConfig().getDouble("soulbound.damage.base");
        this.soulboundPerLvlDamage = MMOItems.plugin.getConfig().getDouble("soulbound.damage.per-lvl");
        this.upgradeRequirementsCheck = MMOItems.plugin.getConfig().getBoolean("item-upgrade-requirements-check");
        GemUpgradeScaling.defaultValue = MMOItems.plugin.getConfig().getString("gem-upgrade-default", "SUBSEQUENT");
        this.keepSoulboundOnDeath = MMOItems.plugin.getConfig().getBoolean("soulbound.keep-on-death");
        this.rerollOnItemUpdate = MMOItems.plugin.getConfig().getBoolean("item-revision.reroll-when-updated");
        this.levelSpread = MMOItems.plugin.getConfig().getDouble("item-level-spread");
        final ConfigurationSection configurationSection = MMOItems.plugin.getConfig().getConfigurationSection("item-revision.keep-data");
        final ConfigurationSection configurationSection2 = MMOItems.plugin.getConfig().getConfigurationSection("item-revision.phat-loots");
        ReforgeOptions.dropRestoredGems = MMOItems.plugin.getConfig().getBoolean("item-revision.drop-extra-gems", true);
        this.revisionOptions = ((configurationSection != null) ? new ReforgeOptions(configurationSection) : new ReforgeOptions(new boolean[] { false, false, false, false, false, false, false, true }));
        this.phatLootsOptions = ((configurationSection2 != null) ? new ReforgeOptions(configurationSection2) : new ReforgeOptions(new boolean[] { false, false, false, false, false, false, false, true }));
        final Iterator<String> iterator = (Iterator<String>)MMOItems.plugin.getConfig().getStringList("item-revision.disable-phat-loot").iterator();
        while (iterator.hasNext()) {
            this.phatLootsOptions.addToBlacklist(iterator.next());
        }
        try {
            this.defaultItemCapacity = new NumericStatFormula(MMOItems.plugin.getConfig().getConfigurationSection("default-item-capacity"));
        }
        catch (IllegalArgumentException ex) {
            this.defaultItemCapacity = new NumericStatFormula(5.0, 0.05, 0.1, 0.3);
            MMOItems.plugin.getLogger().log(Level.INFO, "An error occurred while trying to load default capacity formula for the item generator, using default: " + ex.getMessage());
        }
        final ConfigFile configFile = new ConfigFile("/language", "items");
        for (final ConfigItem configItem : ConfigItems.values) {
            configItem.update(configFile.getConfig().getConfigurationSection(configItem.getId()));
        }
    }
    
    public boolean isBlacklisted(final Material material) {
        return MMOItems.plugin.getConfig().getStringList("block-blacklist").contains(material.name());
    }
    
    public String getStatFormat(final String str) {
        final String string = this.stats.getConfig().getString(str);
        return (string == null) ? ("<TranslationNotFound:" + str + ">") : string;
    }
    
    public String getMessage(final String str) {
        final String string = this.messages.getConfig().getString(str);
        return MythicLib.plugin.parseColors((string == null) ? ("<MessageNotFound:" + str + ">") : string);
    }
    
    public String getAbilityName(final Ability ability) {
        final String string = this.abilities.getConfig().getString("ability." + ability.getLowerCaseID());
        return (string != null) ? string : ability.getName();
    }
    
    public String getCastingModeName(final Ability.CastingMode castingMode) {
        return this.abilities.getConfig().getString("cast-mode." + castingMode.getLowerCaseId());
    }
    
    public String getModifierName(final String str) {
        return this.abilities.getConfig().getString("modifier." + str);
    }
    
    public List<String> getDefaultLoreFormat() {
        return (List<String>)this.loreFormat.getConfig().getStringList("lore-format");
    }
    
    public String getPotionEffectName(final PotionEffectType potionEffectType) {
        return this.potionEffects.getConfig().getString(potionEffectType.getName().toLowerCase().replace("_", "-"));
    }
    
    public String getLuteAttackEffectName(final LuteAttackEffectStat.LuteAttackEffect luteAttackEffect) {
        return this.attackEffects.getConfig().getString("lute-attack." + luteAttackEffect.name().toLowerCase().replace("_", "-"));
    }
    
    public String getStaffSpiritName(final StaffSpiritStat.StaffSpirit staffSpirit) {
        return this.attackEffects.getConfig().getString("staff-spirit." + staffSpirit.name().toLowerCase().replace("_", "-"));
    }
    
    @Deprecated
    public String getDynLoreFormat(final String str) {
        return this.dynLore.getConfig().getString("format." + str);
    }
    
    private void mkdir(final String str) {
        final File file = new File(MMOItems.plugin.getDataFolder() + "/" + str);
        if (!file.exists() && !file.mkdir()) {
            MMOItems.plugin.getLogger().log(Level.WARNING, "Could not create directory!");
        }
    }
    
    static {
        fileNames = new String[] { "abilities", "messages", "potion-effects", "stats", "items", "attack-effects" };
        languages = new String[] { "french", "chinese", "spanish", "russian", "polish" };
    }
    
    public enum DefaultFile
    {
        ITEM_TIERS("item-tiers.yml", "", "item-tiers.yml"), 
        ITEM_TYPES("item-types.yml", "", "item-types.yml", true), 
        DROPS("drops.yml", "", "drops.yml"), 
        ITEM_SETS("item-sets.yml", "", "item-sets.yml"), 
        GEN_TEMPLATES("gen-templates.yml", "", "gen-templates.yml"), 
        UPGRADE_TEMPLATES("upgrade-templates.yml", "", "upgrade-templates.yml"), 
        EXAMPLE_MODIFIERS("modifiers/example-modifiers.yml", "modifiers", "example-modifiers.yml"), 
        LORE_FORMAT("lore-format.yml", "language", "lore-format.yml"), 
        STATS("stats.yml", "language", "stats.yml"), 
        DEFAULT_LAYOUT("layouts/default.yml", "layouts", "default.yml"), 
        EXPANDED_LAYOUT("layouts/expanded.yml", "layouts", "expanded.yml"), 
        ARMOR("item/armor.yml", "item", "armor.yml"), 
        AXE("item/axe.yml", "item", "axe.yml"), 
        BLOCK("item/block.yml", "item", "block.yml"), 
        BOW("item/bow.yml", "item", "bow.yml"), 
        CATALYST("item/catalyst.yml", "item", "catalyst.yml"), 
        CONSUMABLE("item/consumable.yml", "item", "consumable.yml"), 
        DAGGER("item/dagger.yml", "item", "dagger.yml"), 
        GEM_STONE("item/gem_stone.yml", "item", "gem_stone.yml"), 
        GREATSTAFF("item/greatstaff.yml", "item", "greatstaff.yml"), 
        GREATSWORD("item/greatsword.yml", "item", "greatsword.yml"), 
        HALBERD("item/halberd.yml", "item", "halberd.yml"), 
        HAMMER("item/hammer.yml", "item", "hammer.yml"), 
        LANCE("item/lance.yml", "item", "lance.yml"), 
        MATERIAL("item/material.yml", "item", "material.yml"), 
        MISCELLANEOUS("item/miscellaneous.yml", "item", "miscellaneous.yml"), 
        SHIELD("item/shield.yml", "item", "shield.yml"), 
        STAFF("item/staff.yml", "item", "staff.yml"), 
        SWORD("item/sword.yml", "item", "sword.yml"), 
        TOME("item/tome.yml", "item", "tome.yml"), 
        TOOL("item/tool.yml", "item", "tool.yml"), 
        WAND("item/wand.yml", "item", "wand.yml");
        
        private final String folderPath;
        private final String fileName;
        private final String resourceName;
        private final boolean manual;
        
        private DefaultFile(final String s2, final String s3, final String s4) {
            this(s2, s3, s4, false);
        }
        
        private DefaultFile(final String resourceName, final String folderPath, final String fileName, final boolean manual) {
            this.resourceName = resourceName;
            this.folderPath = folderPath;
            this.fileName = fileName;
            this.manual = manual;
        }
        
        public boolean isAutomatic() {
            return !this.manual;
        }
        
        public File getFile() {
            return new File(MMOItems.plugin.getDataFolder() + (this.folderPath.equals("") ? "" : ("/" + this.folderPath)), this.fileName);
        }
        
        public void checkFile() {
            final File file = this.getFile();
            if (!file.exists()) {
                try {
                    if (!new YamlConverter(file).convert()) {
                        Files.copy(MMOItems.plugin.getResource("default/" + this.resourceName), file.getAbsoluteFile().toPath(), new CopyOption[0]);
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        private static /* synthetic */ DefaultFile[] $values() {
            return new DefaultFile[] { DefaultFile.ITEM_TIERS, DefaultFile.ITEM_TYPES, DefaultFile.DROPS, DefaultFile.ITEM_SETS, DefaultFile.GEN_TEMPLATES, DefaultFile.UPGRADE_TEMPLATES, DefaultFile.EXAMPLE_MODIFIERS, DefaultFile.LORE_FORMAT, DefaultFile.STATS, DefaultFile.DEFAULT_LAYOUT, DefaultFile.EXPANDED_LAYOUT, DefaultFile.ARMOR, DefaultFile.AXE, DefaultFile.BLOCK, DefaultFile.BOW, DefaultFile.CATALYST, DefaultFile.CONSUMABLE, DefaultFile.DAGGER, DefaultFile.GEM_STONE, DefaultFile.GREATSTAFF, DefaultFile.GREATSWORD, DefaultFile.HALBERD, DefaultFile.HAMMER, DefaultFile.LANCE, DefaultFile.MATERIAL, DefaultFile.MISCELLANEOUS, DefaultFile.SHIELD, DefaultFile.STAFF, DefaultFile.SWORD, DefaultFile.TOME, DefaultFile.TOOL, DefaultFile.WAND };
        }
        
        static {
            $VALUES = $values();
        }
    }
    
    public static class YamlConverter
    {
        private final File file;
        private final String fileName;
        
        public YamlConverter(final File file) {
            this.file = file;
            this.fileName = file.getName();
        }
        
        public boolean convert() {
            if (!this.file.exists() && this.fileName.equalsIgnoreCase("block.yml") && new File(MMOItems.plugin.getDataFolder(), "custom-blocks.yml").exists() && this.file.createNewFile()) {
                final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(new File(MMOItems.plugin.getDataFolder(), "custom-blocks.yml"));
                for (final String s : loadConfiguration.getKeys(false)) {
                    final ConfigurationSection configurationSection = loadConfiguration.getConfigurationSection(s);
                    configurationSection.set("material", (Object)"STONE");
                    configurationSection.set("block-id", (Object)Integer.parseInt(s));
                    for (final String s2 : configurationSection.getKeys(false)) {
                        final Object value = configurationSection.get(s2);
                        if (s2.equalsIgnoreCase("display-name")) {
                            configurationSection.set("display-name", (Object)null);
                            configurationSection.set("name", value);
                        }
                    }
                }
                loadConfiguration.save(this.file);
                MMOItems.plugin.getLogger().log(Level.CONFIG, "Successfully converted custom-blocks.yml");
                return true;
            }
            return false;
        }
    }
}
