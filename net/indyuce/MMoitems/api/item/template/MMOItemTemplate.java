// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template;

import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import java.util.Iterator;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.apache.commons.lang.Validate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Set;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.ItemReference;
import io.lumine.mythic.lib.api.util.PostLoadObject;

public class MMOItemTemplate extends PostLoadObject implements ItemReference
{
    private final Type type;
    private final String id;
    private final int revId;
    private final Map<ItemStat, RandomStatData> base;
    private final Map<String, TemplateModifier> modifiers;
    private final Set<TemplateOption> options;
    
    public MMOItemTemplate(final Type type, final String id) {
        super((ConfigurationSection)null);
        this.base = new HashMap<ItemStat, RandomStatData>();
        this.modifiers = new LinkedHashMap<String, TemplateModifier>();
        this.options = new HashSet<TemplateOption>();
        this.type = type;
        this.id = id;
        this.revId = 1;
    }
    
    public MMOItemTemplate(final Type type, final ConfigurationSection configurationSection) {
        super(configurationSection);
        this.base = new HashMap<ItemStat, RandomStatData>();
        this.modifiers = new LinkedHashMap<String, TemplateModifier>();
        this.options = new HashSet<TemplateOption>();
        Validate.notNull((Object)configurationSection, "Could not load template config");
        this.type = type;
        this.id = configurationSection.getName().toUpperCase().replace("-", "_").replace(" ", "_");
        this.revId = configurationSection.getInt("base.revision-id", 1);
    }
    
    protected void whenPostLoaded(final ConfigurationSection configurationSection) {
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, this.getType().toString() + " " + this.getId());
        if (configurationSection.contains("option")) {
            for (final TemplateOption templateOption : TemplateOption.values()) {
                if (configurationSection.getBoolean("option." + templateOption.name().toLowerCase().replace("_", "-"))) {
                    this.options.add(templateOption);
                }
            }
        }
        if (configurationSection.contains("modifiers")) {
            for (final String str : configurationSection.getConfigurationSection("modifiers").getKeys(false)) {
                try {
                    final TemplateModifier templateModifier = new TemplateModifier(MMOItems.plugin.getTemplates(), configurationSection.getConfigurationSection("modifiers." + str));
                    this.modifiers.put(templateModifier.getId(), templateModifier);
                }
                catch (IllegalArgumentException ex) {
                    friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Could not load modifier '$f{0}$b': {1}", new String[] { str, ex.getMessage() });
                }
            }
        }
        Validate.notNull((Object)configurationSection.getConfigurationSection("base"), FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Could not find base item data", new String[0]));
        for (final String str2 : configurationSection.getConfigurationSection("base").getKeys(false)) {
            try {
                final String replace = str2.toUpperCase().replace("-", "_");
                Validate.isTrue(MMOItems.plugin.getStats().has(replace), FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Could not find stat with ID '$i{0}$b'", new String[] { replace }));
                final ItemStat value = MMOItems.plugin.getStats().get(replace);
                final RandomStatData whenInitialized = value.whenInitialized(configurationSection.get("base." + str2));
                if (whenInitialized == null) {
                    continue;
                }
                this.base.put(value, whenInitialized);
            }
            catch (IllegalArgumentException ex2) {
                if (ex2.getMessage().isEmpty()) {
                    continue;
                }
                friendlyFeedbackProvider.log(FriendlyFeedbackCategory.INFORMATION, "Could not load base item data '$f{0}$b': {1}", new String[] { str2, ex2.getMessage() });
            }
        }
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.INFORMATION, MMOItems.getConsole());
    }
    
    public Map<ItemStat, RandomStatData> getBaseItemData() {
        return this.base;
    }
    
    public Map<String, TemplateModifier> getModifiers() {
        return this.modifiers;
    }
    
    public boolean hasModifier(final String s) {
        return this.modifiers.containsKey(s);
    }
    
    public TemplateModifier getModifier(final String s) {
        return this.modifiers.get(s);
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public int getRevisionId() {
        return this.revId;
    }
    
    public boolean hasOption(final TemplateOption templateOption) {
        return this.options.contains(templateOption);
    }
    
    public MMOItemBuilder newBuilder(@Nullable final Player player) {
        if (player != null) {
            return this.newBuilder(PlayerData.get((OfflinePlayer)player).getRPG());
        }
        return this.newBuilder((RPGPlayer)null);
    }
    
    public MMOItemBuilder newBuilder() {
        return this.newBuilder((RPGPlayer)null);
    }
    
    public MMOItemBuilder newBuilder(@Nullable final PlayerData playerData) {
        if (playerData != null) {
            return this.newBuilder(playerData.getRPG());
        }
        return this.newBuilder((RPGPlayer)null);
    }
    
    public MMOItemBuilder newBuilder(@Nullable final RPGPlayer rpgPlayer) {
        if (rpgPlayer == null) {
            return this.newBuilder(0, null);
        }
        return new MMOItemBuilder(this, this.hasOption(TemplateOption.LEVEL_ITEM) ? MMOItems.plugin.getTemplates().rollLevel(rpgPlayer.getLevel()) : 0, this.hasOption(TemplateOption.TIERED) ? MMOItems.plugin.getTemplates().rollTier() : null);
    }
    
    public MMOItemBuilder newBuilder(final int n, @Nullable final ItemTier itemTier) {
        return new MMOItemBuilder(this, n, itemTier);
    }
    
    @Deprecated
    public int getCraftedAmount() {
        return 1;
    }
    
    public enum TemplateOption
    {
        ROLL_MODIFIER_CHECK_ORDER, 
        TIERED, 
        LEVEL_ITEM;
        
        private static /* synthetic */ TemplateOption[] $values() {
            return new TemplateOption[] { TemplateOption.ROLL_MODIFIER_CHECK_ORDER, TemplateOption.TIERED, TemplateOption.LEVEL_ITEM };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
