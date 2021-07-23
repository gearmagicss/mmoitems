// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util.crafting;

import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import java.util.ListIterator;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.utils.adventure.text.Component;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemFlag;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.recipe.UpgradingRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.CheckedRecipe;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.util.ConfigItem;

public class UpgradingRecipeDisplay extends ConfigItem
{
    public UpgradingRecipeDisplay() {
        super("UPGRADING_RECIPE_DISPLAY", Material.BARRIER, "&e&lUpgrade&f #name#", new String[] { "{conditions}", "{conditions}&8Conditions:", "", "&8Ingredients:", "#ingredients#", "", "&eLeft-Click to craft!", "&eRight-Click to preview!" });
    }
    
    public ItemBuilder newBuilder(final CheckedRecipe checkedRecipe) {
        return new ItemBuilder(checkedRecipe);
    }
    
    public class ItemBuilder
    {
        private final CheckedRecipe recipe;
        private final UpgradingRecipe upgradingRecipe;
        private final String name;
        private final List<String> lore;
        
        public ItemBuilder(final CheckedRecipe recipe) {
            this.name = UpgradingRecipeDisplay.this.getName();
            this.lore = new ArrayList<String>(UpgradingRecipeDisplay.this.getLore());
            this.recipe = recipe;
            this.upgradingRecipe = (UpgradingRecipe)recipe.getRecipe();
        }
        
        public ItemStack build() {
            final HashMap<Object, String> hashMap = new HashMap<Object, String>();
            int n = -1;
            final ListIterator<String> listIterator = this.lore.listIterator();
            while (listIterator.hasNext()) {
                final int nextIndex = listIterator.nextIndex();
                final String s2 = listIterator.next();
                if (s2.startsWith("{conditions}")) {
                    n = nextIndex;
                    if (this.recipe.getConditions().size() == 0) {
                        listIterator.remove();
                    }
                    else {
                        hashMap.put(s2, s2.replace("{conditions}", ""));
                    }
                }
            }
            for (final String s3 : hashMap.keySet()) {
                this.lore.set(this.lore.indexOf(s3), hashMap.get(s3));
            }
            this.lore.remove(this.lore.indexOf("#ingredients#"));
            final int n2;
            this.recipe.getIngredients().forEach(checkedIngredient -> this.lore.add(n2, checkedIngredient.format()));
            if (n >= 0) {
                for (final Condition.CheckedCondition checkedCondition : this.recipe.getConditions()) {
                    if (checkedCondition.getCondition().getDisplay() != null) {
                        this.lore.add(n++, checkedCondition.format());
                    }
                }
            }
            final ItemStack preview = this.upgradingRecipe.getItem().getPreview();
            final ItemMeta itemMeta = preview.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.values());
            preview.setItemMeta(itemMeta);
            final NBTItem value = NBTItem.get(preview);
            value.setDisplayNameComponent(LegacyComponent.parse(this.name.replace("#name#", MMOUtils.getDisplayName(preview))));
            final ArrayList<Component> loreComponents = new ArrayList<Component>();
            this.lore.forEach(s -> loreComponents.add(LegacyComponent.parse(s)));
            value.setLoreComponents((List)loreComponents);
            return value.addTag(new ItemTag[] { new ItemTag("recipeId", (Object)this.recipe.getRecipe().getId()) }).toItem();
        }
    }
}
