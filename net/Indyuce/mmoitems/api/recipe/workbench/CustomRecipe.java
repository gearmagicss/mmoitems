// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench;

import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.crafting.uimanager.UIFilterManager;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import io.lumine.mythic.lib.api.crafting.uifilters.VanillaUIFilter;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.crafting.recipe.CustomSmithingRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.SmithingCombinationType;
import io.lumine.mythic.lib.api.crafting.ingredients.ShapedIngredient;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import io.lumine.mythic.lib.api.crafting.outputs.MythicRecipeOutput;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipe;
import io.lumine.mythic.lib.api.crafting.outputs.MRORecipe;
import io.lumine.mythic.lib.api.crafting.uifilters.UIFilter;
import net.Indyuce.mmoitems.api.crafting.MMOItemUIFilter;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicRecipeIngredient;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.NamespacedKey;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.permissions.PermissionDefault;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.inventory.ItemStack;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.AirIngredient;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import java.util.List;
import org.bukkit.permissions.Permission;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;
import java.util.Map;
import net.Indyuce.mmoitems.api.Type;

public class CustomRecipe implements Comparable<CustomRecipe>
{
    private final Type type;
    private final String id;
    private final boolean shapeless;
    private final Map<Integer, WorkbenchIngredient> ingredients;
    private Permission permission;
    
    public CustomRecipe(final Type type, final String id, final List<String> list, final boolean shapeless) {
        this.ingredients = new HashMap<Integer, WorkbenchIngredient>(9);
        this.shapeless = shapeless;
        this.type = type;
        this.id = id;
        if (this.shapeless) {
            Validate.isTrue(list.size() == 9, "Invalid shapeless recipe");
            for (int i = 0; i < 9; ++i) {
                final WorkbenchIngredient workbenchIngredient = MMOItems.plugin.getRecipes().getWorkbenchIngredient(list.get(i));
                if (MMOItems.plugin.getRecipes().isAmounts() || !(workbenchIngredient instanceof AirIngredient)) {
                    this.ingredients.put(i, workbenchIngredient);
                }
            }
            return;
        }
        Validate.isTrue(list.size() == 3, "Invalid shaped recipe");
        for (int j = 0; j < 9; ++j) {
            final List<String> list2 = Arrays.asList(list.get(j / 3).split(" "));
            while (list2.size() < 3) {
                list2.add("AIR");
            }
            final WorkbenchIngredient workbenchIngredient2 = MMOItems.plugin.getRecipes().getWorkbenchIngredient(list2.get(j % 3));
            if (!(workbenchIngredient2 instanceof AirIngredient)) {
                this.ingredients.put(j, workbenchIngredient2);
            }
        }
    }
    
    public Set<Map.Entry<Integer, WorkbenchIngredient>> getIngredients() {
        return this.ingredients.entrySet();
    }
    
    public boolean isOneRow() {
        final Iterator<Integer> iterator = this.ingredients.keySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() > 2) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isTwoRows() {
        final Iterator<Integer> iterator = this.ingredients.keySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() > 5) {
                return false;
            }
        }
        return true;
    }
    
    public boolean fitsPlayerCrafting() {
        for (final int intValue : this.ingredients.keySet()) {
            if (intValue > 4 || intValue == 2) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isEmpty() {
        return this.ingredients.isEmpty();
    }
    
    public boolean isShapeless() {
        return this.shapeless;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean checkPermission(final Player player) {
        return this.permission == null || player.hasPermission(this.permission);
    }
    
    public ItemStack getResult(@Nullable final Player player) {
        final MMOItem mmoItem = (player == null) ? MMOItems.plugin.getMMOItem(this.type, this.id) : MMOItems.plugin.getMMOItem(this.type, this.id, PlayerData.get((OfflinePlayer)player));
        final ItemStack build = mmoItem.newBuilder().build();
        if (mmoItem.hasData(ItemStats.CRAFT_AMOUNT)) {
            build.setAmount((int)((DoubleData)mmoItem.getData(ItemStats.CRAFT_AMOUNT)).getValue());
        }
        if (mmoItem.hasData(ItemStats.CRAFT_PERMISSION)) {
            this.permission = new Permission(mmoItem.getData(ItemStats.CRAFT_PERMISSION).toString(), PermissionDefault.FALSE);
        }
        return build;
    }
    
    @Override
    public int compareTo(final CustomRecipe customRecipe) {
        return Boolean.compare(this.shapeless, customRecipe.shapeless);
    }
    
    public Recipe asBukkit(final NamespacedKey namespacedKey) {
        Object o;
        if (this.shapeless) {
            final ShapelessRecipe shapelessRecipe = new ShapelessRecipe(namespacedKey, this.getResult(null));
            for (final WorkbenchIngredient workbenchIngredient : this.ingredients.values()) {
                if (!(workbenchIngredient instanceof AirIngredient)) {
                    shapelessRecipe.addIngredient(workbenchIngredient.toBukkit());
                }
            }
            o = shapelessRecipe;
        }
        else {
            final ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, this.getResult(null));
            final char[] array = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };
            final ArrayList list = new ArrayList((Collection<? extends E>)this.ingredients.keySet());
            final StringBuilder sb = new StringBuilder();
            sb.append(list.contains(0) ? "A" : " ");
            sb.append(list.contains(1) ? "B" : " ");
            sb.append(list.contains(2) ? "C" : " ");
            if (!this.isOneRow()) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(list.contains(3) ? "D" : " ");
                sb2.append(list.contains(4) ? "E" : " ");
                sb2.append(list.contains(5) ? "F" : " ");
                if (!this.isTwoRows()) {
                    shapedRecipe.shape(new String[] { sb.toString(), sb2.toString(), (list.contains(6) ? "G" : " ") + (list.contains(7) ? "H" : " ") + (list.contains(8) ? "I" : " ") });
                }
                else {
                    shapedRecipe.shape(new String[] { sb.toString(), sb2.toString() });
                }
            }
            else {
                shapedRecipe.shape(new String[] { sb.toString() });
            }
            for (final Map.Entry<Integer, WorkbenchIngredient> entry : this.getIngredients()) {
                if (entry.getValue() instanceof AirIngredient) {
                    continue;
                }
                shapedRecipe.setIngredient(array[entry.getKey()], entry.getValue().toBukkit());
            }
            o = shapedRecipe;
        }
        return (Recipe)o;
    }
    
    @NotNull
    public static MythicRecipeBlueprint generateShapeless(@NotNull final Type obj, @NotNull final String str, @NotNull final List<String> list, @NotNull final String s) {
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(obj, str);
        Validate.isTrue(template != null, "Unexpected Error Occurred: Template does not exist.");
        final ArrayList<MythicRecipeIngredient> list2 = new ArrayList<MythicRecipeIngredient>();
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Recipe of $u" + obj + " " + str);
        boolean b = false;
        for (final String s2 : list) {
            if (s2 != null) {
                if (s2.equals("AIR")) {
                    continue;
                }
                final ProvidedUIFilter ingredient = readIngredientFrom(s2, friendlyFeedbackProvider);
                b = true;
                list2.add(new MythicRecipeIngredient(ingredient));
            }
        }
        if (!b) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Shapeless recipe containing only AIR, $fignored$b.", new String[0]));
        }
        return new MythicRecipeBlueprint((MythicRecipe)new io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe(s, (ArrayList)list2), (MythicRecipeOutput)new MRORecipe(io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe.single(s, new ProvidedUIFilter[] { new ProvidedUIFilter((UIFilter)MMOItemUIFilter.get(), obj.getId(), str, Math.max(template.getCraftedAmount(), 1)) })));
    }
    
    @NotNull
    public static MythicRecipeBlueprint generateShaped(@NotNull final Type obj, @NotNull final String str, @NotNull final List<String> list, @NotNull final String s) {
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(obj, str);
        Validate.isTrue(template != null, "Unexpected Error Occurred: Template does not exist.");
        final ArrayList<ShapedIngredient> list2 = new ArrayList<ShapedIngredient>();
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Recipe of $u" + obj + " " + str);
        int n = 0;
        boolean b = false;
        for (final String str2 : list) {
            String[] array;
            if (str2.contains("|")) {
                array = str2.split("\\|");
            }
            else {
                array = str2.split(" ");
            }
            if (array.length != 3) {
                throw new IllegalArgumentException("Invalid crafting table row $u" + str2 + "$b ($fNot exactly 3 ingredients wide$b).");
            }
            final ProvidedUIFilter ingredient = readIngredientFrom(array[0], friendlyFeedbackProvider);
            final ProvidedUIFilter ingredient2 = readIngredientFrom(array[1], friendlyFeedbackProvider);
            final ProvidedUIFilter ingredient3 = readIngredientFrom(array[2], friendlyFeedbackProvider);
            if (!ingredient.isAir()) {
                b = true;
            }
            if (!ingredient2.isAir()) {
                b = true;
            }
            if (!ingredient3.isAir()) {
                b = true;
            }
            final ShapedIngredient e = new ShapedIngredient(ingredient, 0, -n);
            final ShapedIngredient e2 = new ShapedIngredient(ingredient2, 1, -n);
            final ShapedIngredient e3 = new ShapedIngredient(ingredient3, 2, -n);
            list2.add(e);
            list2.add(e2);
            list2.add(e3);
            ++n;
        }
        if (!b) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Shaped recipe containing only AIR, $fignored$b.", new String[0]));
        }
        return new MythicRecipeBlueprint((MythicRecipe)io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe.unsharpen(new io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe(s, (ArrayList)list2)), (MythicRecipeOutput)new MRORecipe(io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe.single(s, new ProvidedUIFilter[] { new ProvidedUIFilter((UIFilter)MMOItemUIFilter.get(), obj.getId(), str, Math.max(template.getCraftedAmount(), 1)) })));
    }
    
    @NotNull
    public static MythicRecipeBlueprint generateSmithing(@NotNull final Type obj, @NotNull final String str, @NotNull final String s, @NotNull final String s2, final boolean b, @NotNull final String s3, @NotNull final String s4, @NotNull final String s5) {
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(obj, str);
        Validate.isTrue(template != null, "Unexpected Error Occurred: Template does not exist.");
        final SmithingCombinationType value = SmithingCombinationType.valueOf(s4.toUpperCase());
        final SmithingCombinationType value2 = SmithingCombinationType.valueOf(s3.toUpperCase());
        final ArrayList list = new ArrayList();
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Recipe of $u" + obj + " " + str);
        final ProvidedUIFilter ingredient = readIngredientFrom(s, friendlyFeedbackProvider);
        final ProvidedUIFilter ingredient2 = readIngredientFrom(s2, friendlyFeedbackProvider);
        if (ingredient.isAir() || ingredient2.isAir()) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Smithing recipe containing AIR, $fignored$b.", new String[0]));
        }
        final MythicRecipeIngredient mythicRecipeIngredient = new MythicRecipeIngredient(ingredient);
        final MythicRecipeIngredient mythicRecipeIngredient2 = new MythicRecipeIngredient(ingredient2);
        final io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe shapelessRecipe = new io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe(s5, new MythicRecipeIngredient[] { mythicRecipeIngredient });
        final io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe shapelessRecipe2 = new io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe(s5, new MythicRecipeIngredient[] { mythicRecipeIngredient2 });
        final MythicRecipeBlueprint mythicRecipeBlueprint = new MythicRecipeBlueprint((MythicRecipe)shapelessRecipe, (MythicRecipeOutput)new CustomSmithingRecipe(template, b, value2, value));
        mythicRecipeBlueprint.addSideCheck("ingot", (MythicRecipe)shapelessRecipe2);
        return mythicRecipeBlueprint;
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
}
