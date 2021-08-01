// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench;

import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.NamespacedKey;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.permissions.PermissionDefault;
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
import net.Indyuce.mmoitems.manager.RecipeManager;
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
                MMOItems.plugin.getRecipes();
                final WorkbenchIngredient workbenchIngredient = RecipeManager.getWorkbenchIngredient(list.get(i));
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
            MMOItems.plugin.getRecipes();
            final WorkbenchIngredient workbenchIngredient2 = RecipeManager.getWorkbenchIngredient(list2.get(j % 3));
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
}
