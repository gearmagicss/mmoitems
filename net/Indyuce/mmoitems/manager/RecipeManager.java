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
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.VanillaIngredient;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.AirIngredient;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.MMOItemIngredient;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Player;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.bukkit.Keyed;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import io.lumine.mythic.lib.api.util.Ref;
import net.Indyuce.mmoitems.api.crafting.recipe.SmithingCombinationType;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import java.util.List;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
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
    private boolean book;
    private boolean amounts;
    ArrayList<NamespacedKey> generatedNKs;
    
    public RecipeManager() {
        this.legacyCraftingRecipes = new HashSet<CustomRecipe>();
        this.loadedLegacyRecipes = new HashSet<Recipe>();
        this.customRecipes = new HashMap<NamespacedKey, MythicRecipeBlueprint>();
        this.booklessRecipes = new ArrayList<MythicRecipeBlueprint>();
        this.generatedNKs = null;
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
                    try {
                        final ConfigurationSection configurationSection = config.getConfigurationSection(mmoItemTemplate.getId() + ".base.crafting");
                        if (configurationSection.contains("shaped")) {
                            configurationSection.getConfigurationSection("shaped").getKeys(false).forEach(str -> this.registerRecipe(type, mmoItemTemplate.getId(), configurationSection.getStringList("shaped." + str), false, str));
                        }
                        if (configurationSection.contains("shapeless")) {
                            configurationSection.getConfigurationSection("shapeless").getKeys(false).forEach(str2 -> this.registerRecipe(type, mmoItemTemplate.getId(), configurationSection.getStringList("shapeless." + str2), true, str2));
                        }
                        if (configurationSection.contains("furnace")) {
                            final BurningRecipeType furnace;
                            final MMOItemTemplate mmoItemTemplate2;
                            final BurningRecipeInformation burningRecipeInformation;
                            final ConfigurationSection configurationSection2;
                            final Type type2;
                            final String s;
                            configurationSection.getConfigurationSection("furnace").getKeys(false).forEach(str3 -> {
                                furnace = BurningRecipeType.FURNACE;
                                mmoItemTemplate2.getId();
                                new BurningRecipeInformation(configurationSection2.getConfigurationSection("furnace." + str3));
                                this.registerBurningRecipe(furnace, type2, s, burningRecipeInformation, str3);
                                return;
                            });
                        }
                        if (configurationSection.contains("blast")) {
                            final BurningRecipeType blast;
                            final MMOItemTemplate mmoItemTemplate3;
                            final BurningRecipeInformation burningRecipeInformation2;
                            final ConfigurationSection configurationSection3;
                            final Type type3;
                            final String s2;
                            configurationSection.getConfigurationSection("blast").getKeys(false).forEach(str4 -> {
                                blast = BurningRecipeType.BLAST;
                                mmoItemTemplate3.getId();
                                new BurningRecipeInformation(configurationSection3.getConfigurationSection("blast." + str4));
                                this.registerBurningRecipe(blast, type3, s2, burningRecipeInformation2, str4);
                                return;
                            });
                        }
                        if (configurationSection.contains("smoker")) {
                            final BurningRecipeType smoker;
                            final MMOItemTemplate mmoItemTemplate4;
                            final BurningRecipeInformation burningRecipeInformation3;
                            final ConfigurationSection configurationSection4;
                            final Type type4;
                            final String s3;
                            configurationSection.getConfigurationSection("smoker").getKeys(false).forEach(str5 -> {
                                smoker = BurningRecipeType.SMOKER;
                                mmoItemTemplate4.getId();
                                new BurningRecipeInformation(configurationSection4.getConfigurationSection("smoker." + str5));
                                this.registerBurningRecipe(smoker, type4, s3, burningRecipeInformation3, str5);
                                return;
                            });
                        }
                        if (configurationSection.contains("campfire")) {
                            final BurningRecipeType campfire;
                            final MMOItemTemplate mmoItemTemplate5;
                            final BurningRecipeInformation burningRecipeInformation4;
                            final ConfigurationSection configurationSection5;
                            final Type type5;
                            final String s4;
                            configurationSection.getConfigurationSection("campfire").getKeys(false).forEach(str6 -> {
                                campfire = BurningRecipeType.CAMPFIRE;
                                mmoItemTemplate5.getId();
                                new BurningRecipeInformation(configurationSection5.getConfigurationSection("campfire." + str6));
                                this.registerBurningRecipe(campfire, type5, s4, burningRecipeInformation4, str6);
                                return;
                            });
                        }
                        if (!configurationSection.contains("smithing")) {
                            continue;
                        }
                        configurationSection.getConfigurationSection("smithing").getKeys(false).forEach(str7 -> this.registerSmithingRecipe(type, mmoItemTemplate.getId(), configurationSection.getConfigurationSection("smithing." + str7), str7));
                    }
                    catch (IllegalArgumentException ex) {
                        friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Could not load recipe of $f{0} {1}$b: {2}", new String[] { type.getId(), mmoItemTemplate.getId(), ex.getMessage() });
                    }
                }
            }
        }
        friendlyFeedbackProvider.sendAllTo(MMOItems.getConsole());
        this.sortRecipes();
        Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> this.getLoadedLegacyRecipes().forEach(Bukkit::addRecipe));
    }
    
    public void registerBurningRecipe(final BurningRecipeType burningRecipeType, final Type type, final String s, final BurningRecipeInformation burningRecipeInformation, final String s2) {
        final NamespacedKey recipeKey = this.getRecipeKey(type, s, burningRecipeType.getPath(), s2);
        final MMOItem mmoItem = MMOItems.plugin.getMMOItem(type, s);
        final int amount = mmoItem.hasData(ItemStats.CRAFT_AMOUNT) ? ((int)((DoubleData)mmoItem.getData(ItemStats.CRAFT_AMOUNT)).getValue()) : 1;
        final ItemStack build = mmoItem.newBuilder().build();
        build.setAmount(amount);
        this.loadedLegacyRecipes.add((Recipe)burningRecipeType.provideRecipe(recipeKey, build, burningRecipeInformation.getChoice().toBukkit(), burningRecipeInformation.getExp(), burningRecipeInformation.getBurnTime()));
    }
    
    public void registerSmithingRecipe(@NotNull final Type type, @NotNull final String str, @NotNull final ConfigurationSection configurationSection, @NotNull final String s) {
        Validate.isTrue(configurationSection.isString("input1") && configurationSection.isString("input2"), "Invalid smithing recipe for '" + type.getId() + " . " + str + "'");
        String string = configurationSection.getString("input1");
        String string2 = configurationSection.getString("input2");
        final boolean boolean1 = configurationSection.getBoolean("drop-gems", false);
        String s2 = configurationSection.getString("upgrades");
        String s3 = configurationSection.getString("enchantments");
        if (string == null) {
            string = "";
        }
        if (string2 == null) {
            string2 = "";
        }
        if (s2 == null) {
            s2 = SmithingCombinationType.MAXIMUM.toString();
        }
        if (s3 == null) {
            s3 = SmithingCombinationType.MAXIMUM.toString();
        }
        final MythicRecipeBlueprint generateSmithing = CustomRecipe.generateSmithing(type, str, string, string2, boolean1, s3, s2, this.getRecipeKey(type, str, "smithing", s).getKey());
        final Ref ref = new Ref();
        generateSmithing.deploy(MythicRecipeStation.SMITHING, ref);
        if (ref.getValue() != null) {
            this.customRecipes.put((NamespacedKey)ref.getValue(), generateSmithing);
        }
        else {
            this.booklessRecipes.add(generateSmithing);
        }
    }
    
    public void registerRecipe(@NotNull final Type type, @NotNull final String s, @NotNull final List<String> list, final boolean b, @NotNull final String s2) {
        MythicRecipeBlueprint mythicRecipeBlueprint;
        if (b) {
            mythicRecipeBlueprint = CustomRecipe.generateShapeless(type, s, list, this.getRecipeKey(type, s, "shapeless", s2).getKey());
        }
        else {
            mythicRecipeBlueprint = CustomRecipe.generateShaped(type, s, list, this.getRecipeKey(type, s, "shaped", s2).getKey());
        }
        final Ref ref = new Ref();
        mythicRecipeBlueprint.deploy(MythicRecipeStation.WORKBENCH, ref);
        if (ref.getValue() != null) {
            this.customRecipes.put((NamespacedKey)ref.getValue(), mythicRecipeBlueprint);
        }
        else {
            this.booklessRecipes.add(mythicRecipeBlueprint);
            if (this.book) {
                MMOItems.print(null, "Cannot register custom {2} recipe for $e{0} {1}$b into crafting book", "Custom Crafting", type.getId(), s, b ? "shapeless" : "shaped");
            }
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
                if (namespacedKey.getNamespace().equals("mmoitems")) {
                    player.undiscoverRecipe(namespacedKey);
                }
            }
            return;
        }
        if (MythicLib.plugin.getVersion().isStrictlyHigher(new int[] { 1, 16 })) {
            for (final NamespacedKey o : player.getDiscoveredRecipes()) {
                if (o.getNamespace().equals("mmoitems") && !this.getNamespacedKeys().contains(o)) {
                    player.undiscoverRecipe(o);
                }
            }
            for (final NamespacedKey namespacedKey2 : this.getNamespacedKeys()) {
                if (namespacedKey2 == null) {
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
            try {
                player.discoverRecipe(namespacedKey3);
            }
            catch (Throwable t2) {
                MMOItems.print(null, "Could not register crafting book recipe for $r{0}$b:$f {1}", "MMOItems Custom Crafting", namespacedKey3.getKey(), t2.getMessage());
            }
        }
    }
    
    public WorkbenchIngredient getWorkbenchIngredient(final String s) {
        final String[] split = s.split(":");
        final int n = (split.length > 1) ? Integer.parseInt(split[1]) : 1;
        if (split[0].contains(".")) {
            final String[] split2 = split[0].split("\\.");
            final Type orThrow = MMOItems.plugin.getTypes().getOrThrow(split2[0].toUpperCase().replace("-", "_").replace(" ", "_"));
            return new MMOItemIngredient(orThrow, MMOItems.plugin.getTemplates().getTemplateOrThrow(orThrow, split2[1].toUpperCase().replace("-", "_").replace(" ", "_")).getId(), n);
        }
        if (split[0].equalsIgnoreCase("air")) {
            return new AirIngredient();
        }
        return new VanillaIngredient(Material.valueOf(split[0].toUpperCase().replace("-", "_").replace(" ", "_")), n);
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
