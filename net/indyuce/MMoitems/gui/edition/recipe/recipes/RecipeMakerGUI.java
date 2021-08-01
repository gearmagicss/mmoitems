// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.recipes;

import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.crafting.uimanager.UIFilterManager;
import io.lumine.mythic.lib.api.crafting.uifilters.UIFilter;
import io.lumine.mythic.lib.api.crafting.uifilters.VanillaUIFilter;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import org.jetbrains.annotations.Nullable;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMG_RecipeInterpreter;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import java.util.UUID;
import java.util.ArrayList;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;
import java.util.HashMap;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_AmountOutput;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;

public abstract class RecipeMakerGUI extends EditionInventory
{
    @NotNull
    final ItemStack nextButtonPage;
    @NotNull
    final ItemStack prevButtonPage;
    @NotNull
    public final ItemStack noButton;
    @NotNull
    public final ItemStack emptySlot;
    @NotNull
    public final ItemStack airSlot;
    @NotNull
    final Inventory myInventory;
    @NotNull
    final ConfigurationSection craftingSection;
    @NotNull
    final ConfigurationSection typeSection;
    @NotNull
    final ConfigurationSection nameSection;
    @NotNull
    final RecipeRegistry recipeRegistry;
    @NotNull
    final RBA_AmountOutput amountButton;
    @NotNull
    final String recipeName;
    int buttonsPage;
    @NotNull
    final HashMap<Integer, RecipeButtonAction> buttonsMap;
    @NotNull
    final ArrayList<RecipeButtonAction> buttons;
    @NotNull
    public final String[] recipeLog;
    @NotNull
    static final HashMap<UUID, Boolean> showingInput;
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public static final int PRIMARY = 2;
    public static final int SECONDARY = 3;
    public static final String INPUT_INGREDIENTS = "input";
    public static final String OUTPUT_INGREDIENTS = "output";
    public static final ProvidedUIFilter AIR;
    
    public RecipeMakerGUI(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final String recipeName, @NotNull final RecipeRegistry recipeRegistry) {
        super(player, mmoItemTemplate);
        this.nextButtonPage = ItemFactory.of(Material.SPECTRAL_ARROW).name("§eMore Options §c»").build();
        this.prevButtonPage = ItemFactory.of(Material.SPECTRAL_ARROW).name("§c« §eMore Options").build();
        this.noButton = ItemFactory.of(Material.IRON_BARS).name("§8---").build();
        this.emptySlot = ItemFactory.of(Material.BARRIER).name("§7No Item").build();
        this.airSlot = ItemFactory.of(Material.STRUCTURE_VOID).name("§7No Item").build();
        this.buttonsMap = new HashMap<Integer, RecipeButtonAction>();
        this.buttons = new ArrayList<RecipeButtonAction>();
        this.recipeLog = new String[] { FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Write in the chat the item you want, follow any format:", new String[0]), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Vanilla: $e[MATERIAL] [AMOUNT] $bex $eDIAMOND 2..", new String[0]), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "MMOItem: $e[TYPE].[ID] [AMOUNT] $bex $eSWORD.CUTLASS 1..", new String[0]), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Other: $e[KEY] [ARG] [DAT] [AMOUNT]$b (check wiki)", new String[0]), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "§8Amount is in the range format, $e[min]..[max]§8, assumed to be $r1..§8 if unspecified.", new String[0]) };
        this.recipeName = recipeName;
        this.recipeRegistry = recipeRegistry;
        this.myInventory = Bukkit.createInventory((InventoryHolder)this, 54, "Edit " + this.getRecipeRegistry().getRecipeTypeName() + " Recipe");
        this.moveInput();
        this.craftingSection = getSection(this.getEditedSection(), "crafting");
        this.typeSection = getSection(this.craftingSection, this.getRecipeRegistry().getRecipeConfigPath());
        this.nameSection = getSection(this.typeSection, recipeName);
        this.addButton(this.amountButton = new RBA_AmountOutput(this, this.getCachedItem().clone()));
    }
    
    @NotNull
    public Inventory getMyInventory() {
        return this.myInventory;
    }
    
    @NotNull
    public ConfigurationSection getCraftingSection() {
        return this.craftingSection;
    }
    
    @NotNull
    public ConfigurationSection getTypeSection() {
        return this.typeSection;
    }
    
    @NotNull
    public ConfigurationSection getNameSection() {
        return this.nameSection;
    }
    
    @NotNull
    public RecipeRegistry getRecipeRegistry() {
        return this.recipeRegistry;
    }
    
    @NotNull
    public RBA_AmountOutput getAmountButton() {
        return this.amountButton;
    }
    
    @NotNull
    public String getRecipeName() {
        return this.recipeName;
    }
    
    public void putButtons(@NotNull final Inventory inventory) {
        this.buttonsMap.clear();
        if (this.buttonsPage > 0) {
            this.myInventory.setItem(this.getButtonsRow() * 9 + 8, this.prevButtonPage);
        }
        if (this.buttonsMap.size() >= (this.buttonsPage + 1) * 7) {
            this.myInventory.setItem(this.getButtonsRow() * 9, this.nextButtonPage);
        }
        for (int i = 7 * this.buttonsPage; i < 7 * (this.buttonsPage + 1); ++i) {
            final int buttonRowPageClamp = this.buttonRowPageClamp(i);
            if (i >= this.buttons.size()) {
                inventory.setItem(buttonRowPageClamp, this.noButton);
            }
            else {
                final RecipeButtonAction value = this.buttons.get(i);
                inventory.setItem(buttonRowPageClamp, value.getButton());
                this.buttonsMap.put(buttonRowPageClamp, value);
            }
        }
    }
    
    public int buttonRowPageClamp(int n) {
        n -= SilentNumbers.floor(n / 7.0) * 7;
        return this.getButtonsRow() * 9 + (n + 1);
    }
    
    public abstract int getButtonsRow();
    
    abstract int getInputSlot(final int p0);
    
    public void refreshInventory() {
        this.addEditionInventoryItems(this.getMyInventory(), true);
        this.putButtons(this.getMyInventory());
        this.putRecipe(this.getMyInventory());
    }
    
    public abstract void putRecipe(@NotNull final Inventory p0);
    
    @NotNull
    public ItemStack getDisplay(final boolean b, final int n) {
        final ProvidedUIFilter providedUIFilter = b ? this.getInterpreter().getInput(n) : this.getInterpreter().getOutput(n);
        if (providedUIFilter == null || providedUIFilter.isAir()) {
            return this.isShowingInput() ? this.emptySlot : this.airSlot;
        }
        return providedUIFilter.getDisplayStack((FriendlyFeedbackProvider)null);
    }
    
    public void addButton(@NotNull final RecipeButtonAction e) {
        this.buttons.add(e);
    }
    
    @NotNull
    public abstract RMG_RecipeInterpreter getInterpreter();
    
    @NotNull
    @Override
    public Inventory getInventory() {
        this.refreshInventory();
        return this.myInventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getView().getTopInventory() != inventoryClickEvent.getClickedInventory()) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        final int inputSlot = this.getInputSlot(inventoryClickEvent.getRawSlot());
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            if (inputSlot >= 0) {
                if (this.isShowingInput()) {
                    new StatEdition(this, ItemStats.CRAFTING, new Object[] { 0, this.getInterpreter(), inputSlot }).enable(this.recipeLog);
                }
                else {
                    new StatEdition(this, ItemStats.CRAFTING, new Object[] { 1, this.getInterpreter(), inputSlot }).enable(this.recipeLog);
                }
            }
            else {
                final RecipeButtonAction recipeButtonAction = this.buttonsMap.get(inventoryClickEvent.getRawSlot());
                if (recipeButtonAction != null) {
                    recipeButtonAction.runPrimary();
                }
            }
        }
        else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            if (inputSlot >= 0) {
                if (this.isShowingInput()) {
                    this.getInterpreter().deleteInput(getSection(this.getEditedSection(), "crafting"), inputSlot);
                }
                else {
                    this.getInterpreter().deleteOutput(getSection(this.getEditedSection(), "crafting"), this.getInputSlot(inventoryClickEvent.getRawSlot()));
                }
                this.refreshInventory();
            }
            else {
                final RecipeButtonAction recipeButtonAction2 = this.buttonsMap.get(inventoryClickEvent.getRawSlot());
                if (recipeButtonAction2 != null) {
                    recipeButtonAction2.runSecondary();
                }
            }
        }
    }
    
    public static void switchInputFor(@NotNull final UUID key) {
        RecipeMakerGUI.showingInput.put(key, !isShowingInputFor(key));
    }
    
    public static boolean isShowingInputFor(@NotNull final UUID key) {
        return RecipeMakerGUI.showingInput.getOrDefault(key, true);
    }
    
    public boolean isShowingInput() {
        return isShowingInputFor(this.getPlayer().getUniqueId());
    }
    
    public void switchInput() {
        switchInputFor(this.getPlayer().getUniqueId());
    }
    
    @NotNull
    public static ItemStack rename(@NotNull final ItemStack itemStack, @NotNull final String s) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MythicLib.plugin.parseColors(s));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    @NotNull
    public static ItemStack addLore(@NotNull final ItemStack itemStack, @NotNull final ArrayList<String> list) {
        if (!itemStack.hasItemMeta()) {
            return itemStack;
        }
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return itemStack;
        }
        List<String> lore = (List<String>)itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            lore.add(MythicLib.plugin.parseColors((String)iterator.next()));
        }
        itemMeta.setLore((List)lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    @NotNull
    public static ConfigurationSection getSection(@NotNull final ConfigurationSection configurationSection, @NotNull final String s) {
        ConfigurationSection configurationSection2 = configurationSection.getConfigurationSection(s);
        if (configurationSection2 == null) {
            configurationSection2 = configurationSection.createSection(s);
        }
        return configurationSection2;
    }
    
    public void moveInput() {
        moveInput(getSection(getSection(this.getEditedSection(), "crafting"), this.getRecipeRegistry().getRecipeConfigPath()), this.recipeName);
    }
    
    public static void tripleDebug(@NotNull final ConfigurationSection configurationSection) {
        MMOItems.print(null, "§d-§7 Section §5" + configurationSection.getCurrentPath(), null, new String[0]);
        for (final String s : configurationSection.getKeys(false)) {
            MMOItems.print(null, "§d +§7 " + s, null, new String[0]);
            MMOItems.print(null, "§d-§e-§7 As List §5" + configurationSection.getCurrentPath() + "." + s + "§7 {§d" + configurationSection.getStringList(s).size() + "§7}", null, new String[0]);
            final Iterator iterator2 = configurationSection.getStringList(s).iterator();
            while (iterator2.hasNext()) {
                MMOItems.print(null, "§d +§e-§7" + iterator2.next(), null, new String[0]);
            }
            final ConfigurationSection section = getSection(configurationSection, s);
            MMOItems.print(null, "§8--§d-§7 Section §5" + section.getCurrentPath(), null, new String[0]);
            for (final String s2 : section.getKeys(false)) {
                MMOItems.print(null, "§8--§d +§7 " + s2, null, new String[0]);
                MMOItems.print(null, "§8--§d-§e-§7 As List §5" + section.getCurrentPath() + "." + s2 + "§7 {§d" + section.getStringList(s2).size() + "§7}", null, new String[0]);
                final Iterator iterator4 = section.getStringList(s2).iterator();
                while (iterator4.hasNext()) {
                    MMOItems.print(null, "§8--§d +§e-§7" + iterator4.next(), null, new String[0]);
                }
                final ConfigurationSection section2 = getSection(section, s2);
                MMOItems.print(null, "§0--§8--§d-§7 Section §5" + section2.getCurrentPath(), null, new String[0]);
                for (final String s3 : section2.getKeys(false)) {
                    MMOItems.print(null, "§0--§8--§d +§7 " + s3, null, new String[0]);
                    MMOItems.print(null, "§0--§8--§d-§e-§7 As List §5" + section2.getCurrentPath() + "." + s3 + "§7 {§d" + section2.getStringList(s3).size() + "§7}", null, new String[0]);
                    final Iterator iterator6 = section2.getStringList(s3).iterator();
                    while (iterator6.hasNext()) {
                        MMOItems.print(null, "§0--§8--§d +§e-§7" + iterator6.next(), null, new String[0]);
                    }
                }
            }
        }
    }
    
    public static ConfigurationSection moveInput(@NotNull final ConfigurationSection configurationSection, @NotNull final String s) {
        ConfigurationSection configurationSection2;
        if (configurationSection.isConfigurationSection(s)) {
            configurationSection2 = getSection(configurationSection, s);
            final String string = configurationSection2.getString("input1");
            final String string2 = configurationSection2.getString("input2");
            if (string != null && string2 != null) {
                configurationSection2.set("input1", (Object)null);
                configurationSection2.set("input2", (Object)null);
                configurationSection2.set("input", (Object)(poofFromLegacy(string) + "|" + poofFromLegacy(string2)));
                configurationSection2.set("output", (Object)"v AIR 0|v AIR 0");
            }
        }
        else {
            final List stringList = configurationSection.getStringList(s);
            configurationSection.set(s, (Object)null);
            configurationSection2 = getSection(configurationSection, s);
            configurationSection2.set("input", (Object)stringList);
        }
        return configurationSection2;
    }
    
    @NotNull
    public static String poofFromLegacy(@Nullable String substring) {
        if (substring == null || "[]".equals(substring)) {
            return "v AIR - 1..";
        }
        if (substring.contains(" ")) {
            return substring;
        }
        final int index = substring.indexOf(58);
        QuickNumberRange quickNumberRange = new QuickNumberRange(Double.valueOf(1.0), (Double)null);
        if (index > 0) {
            final String substring2 = substring.substring(index + 1);
            substring = substring.substring(0, index);
            Integer n = SilentNumbers.IntegerParse(substring2);
            if (n == null) {
                n = 1;
            }
            quickNumberRange = new QuickNumberRange(Double.valueOf(n), (Double)null);
        }
        if (substring.contains(".")) {
            final String[] split = substring.split("\\.");
            return "m " + split[0] + " " + split[1] + " " + quickNumberRange;
        }
        return "v " + substring + " - " + quickNumberRange;
    }
    
    @NotNull
    public static ProvidedUIFilter readIngredientFrom(@NotNull final String s, @NotNull final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        Material value = null;
        try {
            value = Material.valueOf(s.toUpperCase().replace(" ", "_").replace("-", "_"));
        }
        catch (IllegalArgumentException ex) {}
        if (value != null) {
            if (value.isAir()) {
                final ProvidedUIFilter providedUIFilter = new ProvidedUIFilter((UIFilter)VanillaUIFilter.get(), "AIR", "0");
                providedUIFilter.setAmountRange(new QuickNumberRange((Double)null, (Double)null));
                return providedUIFilter;
            }
            if (!value.isItem()) {
                throw new IllegalArgumentException("Invalid Ingredient $u" + s + "$b ($fNot an Item$b).");
            }
            final ProvidedUIFilter uiFilter = UIFilterManager.getUIFilter("v", value.toString(), "", "1..", friendlyFeedbackProvider);
            if (uiFilter != null) {
                return uiFilter;
            }
            friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.ERROR, MMOItems.getConsole());
            friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.FAILURE, MMOItems.getConsole());
            throw new IllegalArgumentException("Invalid Ingredient $u" + s);
        }
        else {
            if (s.contains(".") && !s.contains(" ")) {
                final String[] split = s.split("\\.");
                if (split.length == 2) {
                    final ProvidedUIFilter uiFilter2 = UIFilterManager.getUIFilter("m", split[0], split[1], "1..", friendlyFeedbackProvider);
                    if (uiFilter2 != null) {
                        return uiFilter2;
                    }
                    friendlyFeedbackProvider.sendAllTo(MMOItems.getConsole());
                    throw new IllegalArgumentException("Invalid Ingredient $u" + s);
                }
            }
            final ProvidedUIFilter uiFilter3 = UIFilterManager.getUIFilter(s, friendlyFeedbackProvider);
            if (uiFilter3 != null) {
                return uiFilter3;
            }
            friendlyFeedbackProvider.sendAllTo(MMOItems.getConsole());
            throw new IllegalArgumentException("Invalid Ingredient $u" + s);
        }
    }
    
    static {
        showingInput = new HashMap<UUID, Boolean>();
        AIR = new ProvidedUIFilter((UIFilter)new VanillaUIFilter(), "AIR", "0");
    }
}
