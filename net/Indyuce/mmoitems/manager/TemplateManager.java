// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.configuration.file.FileConfiguration;
import net.Indyuce.mmoitems.api.ItemTier;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.Type;
import java.util.HashMap;
import java.util.Random;
import net.Indyuce.mmoitems.api.item.template.TemplateModifier;
import java.util.Map;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.util.TemplateMap;

public class TemplateManager implements Reloadable
{
    private final TemplateMap<MMOItemTemplate> templates;
    private final Map<String, TemplateModifier> modifiers;
    private static final Random random;
    
    public TemplateManager() {
        this.templates = new TemplateMap<MMOItemTemplate>();
        this.modifiers = new HashMap<String, TemplateModifier>();
    }
    
    public boolean hasTemplate(@Nullable final Type type, @Nullable final String s) {
        return type != null && s != null && this.templates.hasValue(type, s);
    }
    
    public boolean hasTemplate(@Nullable final NBTItem nbtItem) {
        return nbtItem != null && this.hasTemplate(Type.get(nbtItem.getType()), nbtItem.getString("MMOITEMS_ITEM_ID"));
    }
    
    @Nullable
    public MMOItemTemplate getTemplate(@Nullable final Type type, @Nullable final String s) {
        if (type == null || s == null) {
            return null;
        }
        return this.templates.getValue(type, s);
    }
    
    @Nullable
    public MMOItemTemplate getTemplate(@Nullable final NBTItem nbtItem) {
        if (nbtItem == null) {
            return null;
        }
        return this.getTemplate(Type.get(nbtItem.getType()), nbtItem.getString("MMOITEMS_ITEM_ID"));
    }
    
    @NotNull
    public MMOItemTemplate getTemplateOrThrow(@Nullable final Type type, @Nullable final String str) {
        Validate.isTrue(type != null && this.hasTemplate(type, str), "Could not find a template with ID '" + str + "'");
        return this.templates.getValue(type, str);
    }
    
    @NotNull
    public Collection<MMOItemTemplate> getTemplates(@NotNull final Type type) {
        return this.templates.collectValues(type);
    }
    
    @NotNull
    public ArrayList<String> getTemplateNames(@NotNull final Type type) {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<MMOItemTemplate> iterator = this.templates.collectValues(type).iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getId());
        }
        return list;
    }
    
    public void registerTemplate(@NotNull final MMOItemTemplate mmoItemTemplate) {
        Validate.notNull((Object)mmoItemTemplate, "MMOItem template cannot be null");
        this.templates.setValue(mmoItemTemplate.getType(), mmoItemTemplate.getId(), mmoItemTemplate);
    }
    
    public void unregisterTemplate(@NotNull final Type type, @NotNull final String s) {
        this.templates.removeValue(type, s);
    }
    
    public void deleteTemplate(@NotNull final Type type, @NotNull final String s) {
        this.unregisterTemplate(type, s);
        final ConfigFile configFile = type.getConfigFile();
        configFile.getConfig().set(s, (Object)null);
        configFile.save();
    }
    
    public MMOItemTemplate requestTemplateUpdate(@NotNull final Type type, @NotNull final String str) {
        this.templates.removeValue(type, str);
        try {
            final MMOItemTemplate mmoItemTemplate = new MMOItemTemplate(type, type.getConfigFile().getConfig().getConfigurationSection(str));
            mmoItemTemplate.postLoad();
            this.registerTemplate(mmoItemTemplate);
            return mmoItemTemplate;
        }
        catch (IllegalArgumentException ex) {
            MMOItems.plugin.getLogger().log(Level.INFO, "An error occured while trying to reload item gen template '" + str + "': " + ex.getMessage());
            return null;
        }
    }
    
    public Collection<MMOItemTemplate> collectTemplates() {
        return this.templates.collectValues();
    }
    
    public boolean hasModifier(final String s) {
        return this.modifiers.containsKey(s);
    }
    
    public TemplateModifier getModifier(final String s) {
        return this.modifiers.get(s);
    }
    
    public Collection<TemplateModifier> getModifiers() {
        return this.modifiers.values();
    }
    
    public ItemTier rollTier() {
        double n = 0.0;
        for (final ItemTier itemTier : MMOItems.plugin.getTiers().getAll()) {
            if (n >= 1.0 || TemplateManager.random.nextDouble() < itemTier.getGenerationChance() / (1.0 - n)) {
                return itemTier;
            }
            n += itemTier.getGenerationChance();
        }
        return null;
    }
    
    public int rollLevel(final int n) {
        final double levelSpread = MMOItems.plugin.getLanguage().levelSpread;
        return (int)Math.max(Math.min(TemplateManager.random.nextGaussian() * levelSpread * 0.7 + n, n + levelSpread), Math.max(1.0, n - levelSpread));
    }
    
    public void preloadTemplates() {
        for (final Type type : MMOItems.plugin.getTypes().getAll()) {
            final FileConfiguration config = type.getConfigFile().getConfig();
            for (final String str : config.getKeys(false)) {
                try {
                    this.registerTemplate(new MMOItemTemplate(type, config.getConfigurationSection(str)));
                }
                catch (IllegalArgumentException ex) {
                    MMOItems.plugin.getLogger().log(Level.INFO, "Could not preload item template '" + str + "': " + ex.getMessage());
                }
            }
        }
    }
    
    public void postloadTemplates() {
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Item Templates");
        friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Loading template modifiers, please wait..", new String[0]);
        for (final File file : new File(MMOItems.plugin.getDataFolder() + "/modifiers").listFiles()) {
            final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
            friendlyFeedbackProvider.activatePrefix(true, "Item Templates §8($r" + file.getPath() + "§8)");
            for (final String str : ((FileConfiguration)loadConfiguration).getKeys(false)) {
                try {
                    final TemplateModifier templateModifier = new TemplateModifier(((FileConfiguration)loadConfiguration).getConfigurationSection(str));
                    this.modifiers.put(templateModifier.getId(), templateModifier);
                }
                catch (IllegalArgumentException ex) {
                    friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Could not load template modifier '" + str + "': " + ex.getMessage(), new String[0]);
                }
            }
        }
        friendlyFeedbackProvider.activatePrefix(true, "Item Templates");
        friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Loading item templates, please wait...", new String[0]);
        final FriendlyFeedbackProvider friendlyFeedbackProvider2;
        this.templates.forEach(mmoItemTemplate -> {
            try {
                mmoItemTemplate.postLoad();
            }
            catch (IllegalArgumentException ex2) {
                friendlyFeedbackProvider2.activatePrefix(true, "Item Templates §8($r" + mmoItemTemplate.getType().getId() + "§8)");
                friendlyFeedbackProvider2.log(FriendlyFeedbackCategory.INFORMATION, "Could not load item template '" + mmoItemTemplate.getId() + "': " + ex2.getMessage(), new String[0]);
            }
            return;
        });
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.INFORMATION, MMOItems.getConsole());
    }
    
    @Override
    public void reload() {
        this.templates.clear();
        this.modifiers.clear();
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Item Templates");
        friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Loading template modifiers, please wait..", new String[0]);
        for (final File file : new File(MMOItems.plugin.getDataFolder() + "/modifiers").listFiles()) {
            final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
            friendlyFeedbackProvider.activatePrefix(true, "Item Templates §8($r" + file.getPath() + "§8)");
            for (final String str : ((FileConfiguration)loadConfiguration).getKeys(false)) {
                try {
                    final TemplateModifier templateModifier = new TemplateModifier(((FileConfiguration)loadConfiguration).getConfigurationSection(str));
                    this.modifiers.put(templateModifier.getId(), templateModifier);
                }
                catch (IllegalArgumentException ex) {
                    friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Could not load template modifier '" + str + "': " + ex.getMessage(), new String[0]);
                }
            }
        }
        friendlyFeedbackProvider.activatePrefix(true, "Item Templates");
        friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Loading item templates, please wait...", new String[0]);
        for (final Type type : MMOItems.plugin.getTypes().getAll()) {
            final FileConfiguration config = type.getConfigFile().getConfig();
            friendlyFeedbackProvider.activatePrefix(true, "Item Templates §8($r" + type.getId() + "§8)");
            for (final String str2 : config.getKeys(false)) {
                try {
                    final MMOItemTemplate mmoItemTemplate = new MMOItemTemplate(type, config.getConfigurationSection(str2));
                    mmoItemTemplate.postLoad();
                    this.registerTemplate(mmoItemTemplate);
                }
                catch (IllegalArgumentException ex2) {
                    friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Could not load item template '" + str2 + "': " + ex2.getMessage(), new String[0]);
                }
            }
        }
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.INFORMATION, MMOItems.getConsole());
    }
    
    static {
        random = new Random();
    }
}
