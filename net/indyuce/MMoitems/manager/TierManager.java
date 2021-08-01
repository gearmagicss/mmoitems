// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.api.ConfigFile;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ItemTier;
import java.util.Map;

public class TierManager implements Reloadable
{
    private final Map<String, ItemTier> tiers;
    
    public TierManager() {
        this.tiers = new HashMap<String, ItemTier>();
        this.reload();
    }
    
    @Override
    public void reload() {
        this.tiers.clear();
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Tiers");
        final ConfigFile configFile = new ConfigFile("item-tiers");
        for (final String s : configFile.getConfig().getKeys(false)) {
            final ConfigurationSection section = RecipeMakerGUI.getSection((ConfigurationSection)configFile.getConfig(), s);
            try {
                this.register(new ItemTier(section));
            }
            catch (IllegalArgumentException ex) {
                friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Cannot register tier '$u{0}$b';$f {1}", new String[] { s, ex.getMessage() });
            }
        }
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.ERROR, MMOItems.getConsole());
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.FAILURE, MMOItems.getConsole());
    }
    
    public void register(@NotNull final ItemTier itemTier) {
        this.tiers.put(itemTier.getId(), itemTier);
    }
    
    public boolean has(@Nullable final String s) {
        return s != null && this.tiers.containsKey(s);
    }
    
    @NotNull
    public ItemTier getOrThrow(@Nullable final String s) {
        Validate.isTrue(this.tiers.containsKey(s), FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Could not find tier with ID '$r{0}$b'", new String[] { s }));
        return this.tiers.get(s);
    }
    
    @Nullable
    public ItemTier get(@Nullable final String s) {
        if (s == null) {
            return null;
        }
        return this.tiers.get(s);
    }
    
    @NotNull
    public Collection<ItemTier> getAll() {
        return this.tiers.values();
    }
    
    @Nullable
    public ItemTier findTier(@NotNull final MMOItem mmoItem) {
        try {
            return mmoItem.hasData(ItemStats.TIER) ? this.get(mmoItem.getData(ItemStats.TIER).toString()) : null;
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
