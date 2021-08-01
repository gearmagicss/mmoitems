// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.RecipeChoice;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackMessage;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.VanillaIngredient;
import org.bukkit.Material;
import io.lumine.mythic.lib.api.crafting.uifilters.VanillaUIFilter;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.MMOItemIngredient;
import net.Indyuce.mmoitems.api.crafting.MMOItemUIFilter;
import java.util.List;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.AirIngredient;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Player;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.bukkit.Keyed;
import java.util.Collection;
import java.util.Set;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.BurningRecipeInformation;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import java.util.logging.Level;
import io.lumine.mythic.lib.api.util.Ref;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeBrowserGUI;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import org.bukkit.NamespacedKey;
import java.util.HashMap;
import org.bukkit.inventory.Recipe;
import net.Indyuce.mmoitems.api.recipe.workbench.CustomRecipe;
import java.util.HashSet;

public class RecipeManager implements Reloadable
{
    final HashSet<CustomRecipe> legacyCraftingRecipes;
    final HashSet<Recipe> loadedLegacyRecipes;
    final HashMap<NamespacedKey, MythicRecipeBlueprint> customRecipes;
    final ArrayList<MythicRecipeBlueprint> booklessRecipes;
    @NotNull
    ArrayList<NamespacedKey> blacklistedFromAutomaticDiscovery;
    private boolean book;
    private boolean amounts;
    @Nullable
    ArrayList<NamespacedKey> generatedNKs;
    
    public RecipeManager() {
        this.legacyCraftingRecipes = new HashSet<CustomRecipe>();
        this.loadedLegacyRecipes = new HashSet<Recipe>();
        this.customRecipes = new HashMap<NamespacedKey, MythicRecipeBlueprint>();
        this.booklessRecipes = new ArrayList<MythicRecipeBlueprint>();
        this.blacklistedFromAutomaticDiscovery = new ArrayList<NamespacedKey>();
    }
    
    public void load(final boolean book, final boolean amounts) {
        this.book = book;
        this.amounts = amounts;
    }
    
    public boolean isAmounts() {
        return this.amounts;
    }
    
    public void loadRecipes() {
        this.legacyCraftingRecipes.clear();
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Custom Crafting");
        for (final Type type : MMOItems.plugin.getTypes().getAll()) {
            final FileConfiguration config = type.getConfigFile().getConfig();
            for (final MMOItemTemplate mmoItemTemplate : MMOItems.plugin.getTemplates().getTemplates(type)) {
                if (config.contains(mmoItemTemplate.getId() + ".base.crafting")) {
                    final ConfigurationSection section = RecipeMakerGUI.getSection((ConfigurationSection)config, mmoItemTemplate.getId() + ".base.crafting");
                    for (final String s : RecipeBrowserGUI.getRegisteredRecipes()) {
                        if (section.contains(s)) {
                            final RecipeRegistry registeredRecipe = RecipeBrowserGUI.getRegisteredRecipe(s);
                            final ConfigurationSection section2 = RecipeMakerGUI.getSection(section, s);
                            for (final String s2 : section2.getKeys(false)) {
                                final Ref ref = new Ref((Object)this.getRecipeKey(mmoItemTemplate.getType(), mmoItemTemplate.getId(), s, s2));
                                final FriendlyFeedbackProvider friendlyFeedbackProvider2 = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
                                friendlyFeedbackProvider2.activatePrefix(true, "Recipe of $u" + mmoItemTemplate.getType() + " " + mmoItemTemplate.getId());
                                try {
                                    final MythicRecipeBlueprint sendToMythicLib = registeredRecipe.sendToMythicLib(mmoItemTemplate, section2, s2, (Ref<NamespacedKey>)ref, friendlyFeedbackProvider2);
                                    if (ref.getValue() != null) {
                                        this.customRecipes.put((NamespacedKey)ref.getValue(), sendToMythicLib);
                                    }
                                    else {
                                        this.booklessRecipes.add(sendToMythicLib);
                                    }
                                }
                                catch (IllegalArgumentException ex) {
                                    if (ex.getMessage().isEmpty()) {
                                        continue;
                                    }
                                    MMOItems.print(null, "Cannot register custom recipe '$u{2}$b' for $e{0} {1}$b;$f {3}", "Custom Crafting", type.getId(), mmoItemTemplate.getId(), s2, ex.getMessage());
                                    friendlyFeedbackProvider2.sendTo(FriendlyFeedbackCategory.ERROR, MMOItems.getConsole());
                                    friendlyFeedbackProvider2.sendTo(FriendlyFeedbackCategory.FAILURE, MMOItems.getConsole());
                                }
                            }
                        }
                    }
                }
            }
        }
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.ERROR, MMOItems.getConsole());
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.FAILURE, MMOItems.getConsole());
        this.sortRecipes();
        Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> this.getLoadedLegacyRecipes().forEach(Bukkit::addRecipe));
    }
    
    public void registerBurningRecipe(@NotNull final BurningRecipeType burningRecipeType, @NotNull final MMOItem mmoItem, @NotNull final net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.BurningRecipeInformation burningRecipeInformation, final int amount, @NotNull final NamespacedKey e, final boolean b) {
        final ItemStack build = mmoItem.newBuilder().build();
        build.setAmount(amount);
        this.loadedLegacyRecipes.add((Recipe)burningRecipeType.provideRecipe(e, build, burningRecipeInformation.getChoice().toBukkit(), burningRecipeInformation.getExp(), burningRecipeInformation.getBurnTime()));
        if (b) {
            this.blacklistedFromAutomaticDiscovery.add(e);
        }
    }
    
    public void registerRecipeAsCustom(final CustomRecipe e) {
        if (!e.isEmpty()) {
            this.legacyCraftingRecipes.add(e);
        }
    }
    
    public void registerRecipeAsBukkit(final CustomRecipe customRecipe, final String s) {
        final NamespacedKey recipeKey = this.getRecipeKey(customRecipe.getType(), customRecipe.getId(), customRecipe.isShapeless() ? "shapeless" : "shaped", s);
        if (!customRecipe.isEmpty()) {
            this.loadedLegacyRecipes.add(customRecipe.asBukkit(recipeKey));
        }
    }
    
    public Set<Recipe> getLoadedLegacyRecipes() {
        return this.loadedLegacyRecipes;
    }
    
    public Set<CustomRecipe> getLegacyCustomRecipes() {
        return this.legacyCraftingRecipes;
    }
    
    public HashMap<NamespacedKey, MythicRecipeBlueprint> getCustomRecipes() {
        return this.customRecipes;
    }
    
    public ArrayList<NamespacedKey> getNamespacedKeys() {
        if (this.generatedNKs != null) {
            return this.generatedNKs;
        }
        final ArrayList<NamespacedKey> list = new ArrayList<NamespacedKey>(this.customRecipes.keySet());
        list.addAll(this.loadedLegacyRecipes.stream().map(keyed -> keyed.getKey()).distinct().collect((Collector<? super Object, ?, ArrayList<? extends NamespacedKey>>)Collectors.toCollection((Supplier<R>)ArrayList::new)));
        this.generatedNKs = new ArrayList<NamespacedKey>();
        for (final NamespacedKey e : list) {
            if (e != null) {
                this.generatedNKs.add(e);
            }
        }
        return this.generatedNKs;
    }
    
    public void sortRecipes() {
        final ArrayList<Object> list = new ArrayList<Object>(this.legacyCraftingRecipes);
        this.legacyCraftingRecipes.clear();
        this.legacyCraftingRecipes.addAll((Collection<?>)list.stream().sorted().collect((Collector<? super Object, ?, Collection<? extends CustomRecipe>>)Collectors.toList()));
    }
    
    @NotNull
    public NamespacedKey getRecipeKey(@NotNull final Type type, @NotNull final String str, @NotNull final String str2, @NotNull final String str3) {
        return new NamespacedKey((Plugin)MMOItems.plugin, str2 + "_" + type.getId() + "_" + str + "_" + str3);
    }
    
    @Override
    public void reload() {
        final Iterator<NamespacedKey> iterator;
        NamespacedKey namespacedKey;
        final Iterator<NamespacedKey> iterator2;
        NamespacedKey key;
        final Iterator<MythicRecipeBlueprint> iterator3;
        final Iterator<Player> iterator4;
        Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> {
            this.getNamespacedKeys().iterator();
            while (iterator.hasNext()) {
                namespacedKey = iterator.next();
                if (namespacedKey == null) {
                    continue;
                }
                else {
                    try {
                        Bukkit.removeRecipe(namespacedKey);
                    }
                    catch (Throwable t) {
                        MMOItems.print(null, "Could not register crafting book recipe for $r{0}$b:$f {1}", "MMOItems Custom Crafting", namespacedKey.getKey(), t.getMessage());
                    }
                }
            }
            this.loadedLegacyRecipes.clear();
            this.blacklistedFromAutomaticDiscovery.clear();
            this.customRecipes.keySet().iterator();
            while (iterator2.hasNext()) {
                key = iterator2.next();
                if (key == null) {
                    continue;
                }
                else {
                    this.customRecipes.get(key).disable();
                    try {
                        Bukkit.removeRecipe(key);
                    }
                    catch (Throwable t2) {
                        MMOItems.print(null, "Could not register crafting book recipe for $r{0}$b:$f {1}", "MMOItems Custom Crafting", key.getKey(), t2.getMessage());
                    }
                }
            }
            this.customRecipes.clear();
            this.booklessRecipes.iterator();
            while (iterator3.hasNext()) {
                iterator3.next().disable();
            }
            this.booklessRecipes.clear();
            this.generatedNKs = null;
            this.loadRecipes();
            if (this.book) {
                Bukkit.getOnlinePlayers().iterator();
                while (iterator4.hasNext()) {
                    this.refreshRecipeBook(iterator4.next());
                }
            }
        });
    }
    
    public void refreshRecipeBook(final Player player) {
        if (!this.book) {
            for (final NamespacedKey namespacedKey : player.getDiscoveredRecipes()) {
                if ("mmoitems".equals(namespacedKey.getNamespace())) {
                    player.undiscoverRecipe(namespacedKey);
                }
            }
            return;
        }
        if (MythicLib.plugin.getVersion().isStrictlyHigher(new int[] { 1, 16 })) {
            for (final NamespacedKey o : player.getDiscoveredRecipes()) {
                if ("mmoitems".equals(o.getNamespace()) && !this.getNamespacedKeys().contains(o)) {
                    player.undiscoverRecipe(o);
                }
            }
            for (final NamespacedKey namespacedKey2 : this.getNamespacedKeys()) {
                if (namespacedKey2 == null) {
                    continue;
                }
                boolean b = false;
                final Iterator<NamespacedKey> iterator4 = this.blacklistedFromAutomaticDiscovery.iterator();
                while (iterator4.hasNext()) {
                    if (namespacedKey2.equals((Object)iterator4.next())) {
                        b = true;
                        break;
                    }
                }
                if (b) {
                    continue;
                }
                try {
                    if (player.hasDiscoveredRecipe(namespacedKey2)) {
                        continue;
                    }
                    player.discoverRecipe(namespacedKey2);
                }
                catch (Throwable t) {
                    MMOItems.print(null, "Could not register crafting book recipe for $r{0}$b:$f {1}", "MMOItems Custom Crafting", namespacedKey2.getKey(), t.getMessage());
                }
            }
            return;
        }
        for (final NamespacedKey namespacedKey3 : this.getNamespacedKeys()) {
            if (namespacedKey3 == null) {
                continue;
            }
            boolean b2 = false;
            final Iterator<NamespacedKey> iterator6 = this.blacklistedFromAutomaticDiscovery.iterator();
            while (iterator6.hasNext()) {
                if (namespacedKey3.equals((Object)iterator6.next())) {
                    b2 = true;
                    break;
                }
            }
            if (b2) {
                continue;
            }
            try {
                player.discoverRecipe(namespacedKey3);
            }
            catch (Throwable t2) {
                MMOItems.print(null, "Could not register crafting book recipe for $r{0}$b:$f {1}", "MMOItems Custom Crafting", namespacedKey3.getKey(), t2.getMessage());
            }
        }
    }
    
    @NotNull
    public static WorkbenchIngredient getWorkbenchIngredient(@NotNull final String s) {
        final ProvidedUIFilter fromString = ProvidedUIFilter.getFromString(RecipeMakerGUI.poofFromLegacy(s), (FriendlyFeedbackProvider)null);
        if (fromString == null) {
            return new AirIngredient();
        }
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        if (!fromString.isValid(friendlyFeedbackProvider)) {
            throw new IllegalArgumentException(SilentNumbers.collapseList(SilentNumbers.transcribeList((List)friendlyFeedbackProvider.getFeedbackOf(FriendlyFeedbackCategory.ERROR), o -> ((FriendlyFeedbackMessage)o).forConsole((FriendlyFeedbackPalette)FFPMMOItems.get())), ". "));
        }
        final int amount = fromString.getAmount(0);
        if (fromString.getParent() instanceof MMOItemUIFilter) {
            final Type orThrow = MMOItems.plugin.getTypes().getOrThrow(fromString.getArgument());
            return new MMOItemIngredient(orThrow, MMOItems.plugin.getTemplates().getTemplateOrThrow(orThrow, fromString.getData()).getId(), amount);
        }
        if (fromString.getParent() instanceof VanillaUIFilter) {
            return new VanillaIngredient(Material.valueOf(fromString.getArgument().toUpperCase().replace("-", "_").replace(" ", "_")), amount);
        }
        throw new IllegalArgumentException("Unsupported ingredient, you may only specify vanilla or mmoitems.");
    }
    
    public enum BurningRecipeType
    {
        FURNACE(FurnaceRecipe::new), 
        SMOKER(SmokingRecipe::new), 
        CAMPFIRE(CampfireRecipe::new), 
        BLAST(BlastingRecipe::new);
        
        private final RecipeProvider provider;
        
        private BurningRecipeType(final RecipeProvider provider) {
            this.provider = provider;
        }
        
        public CookingRecipe<?> provideRecipe(final NamespacedKey namespacedKey, final ItemStack itemStack, final RecipeChoice recipeChoice, final float n, final int n2) {
            return this.provider.provide(namespacedKey, itemStack, recipeChoice, n, n2);
        }
        
        public String getPath() {
            return this.name().toLowerCase();
        }
        
        private static /* synthetic */ BurningRecipeType[] $values() {
            return new BurningRecipeType[] { BurningRecipeType.FURNACE, BurningRecipeType.SMOKER, BurningRecipeType.CAMPFIRE, BurningRecipeType.BLAST };
        }
        
        static {
            $VALUES = $values();
        }
    }
    
    public class BurningRecipeInformation
    {
        private final WorkbenchIngredient choice;
        private final float exp;
        private final int burnTime;
        
        public BurningRecipeInformation(final ConfigurationSection configurationSection) {
            this.choice = RecipeManager.this.getWorkbenchIngredient(configurationSection.getString("item"));
            this.exp = (float)configurationSection.getDouble("exp", 0.35);
            this.burnTime = configurationSection.getInt("time", 200);
        }
        
        public int getBurnTime() {
            return this.burnTime;
        }
        
        public WorkbenchIngredient getChoice() {
            return this.choice;
        }
        
        public float getExp() {
            return this.exp;
        }
    }
    
    @FunctionalInterface
    public interface RecipeProvider
    {
        CookingRecipe<?> provide(final NamespacedKey p0, final ItemStack p1, final RecipeChoice p2, final float p3, final int p4);
    }
}
