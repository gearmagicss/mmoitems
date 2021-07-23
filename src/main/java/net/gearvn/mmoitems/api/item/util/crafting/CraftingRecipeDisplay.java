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
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.recipe.CraftingRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.CheckedRecipe;
import org.bukkit.Material;
import java.text.DecimalFormat;
import net.Indyuce.mmoitems.api.item.util.ConfigItem;

public class CraftingRecipeDisplay extends ConfigItem
{
    private static final DecimalFormat craftingTimeFormat;
    
    public CraftingRecipeDisplay() {
        super("CRAFTING_RECIPE_DISPLAY", Material.BARRIER, "&a&lCraft&f #name#", new String[] { "{conditions}", "{conditions}&8Conditions:", "{crafting_time}", "{crafting_time}&7Crafting Time: &c#crafting-time#&7s", "", "&8Ingredients:", "#ingredients#", "", "&eLeft-Click to craft!", "&eRight-Click to preview!" });
    }
    
    public ItemBuilder newBuilder(final CheckedRecipe checkedRecipe) {
        return new ItemBuilder(checkedRecipe);
    }
    
    static {
        craftingTimeFormat = new DecimalFormat("0.#");
    }
    
    public class ItemBuilder
    {
        private final CheckedRecipe recipe;
        private final CraftingRecipe craftingRecipe;
        private final String name;
        private final List<String> lore;
        
        public ItemBuilder(final CheckedRecipe recipe) {
            this.name = CraftingRecipeDisplay.this.getName();
            this.lore = new ArrayList<String>(CraftingRecipeDisplay.this.getLore());
            this.recipe = recipe;
            this.craftingRecipe = (CraftingRecipe)recipe.getRecipe();
        }
        
        public ItemStack build() {
            final HashMap<String, Object> hashMap = (HashMap<String, Object>)new HashMap<Object, String>();
            int n = -1;
            final ListIterator<String> listIterator = this.lore.listIterator();
            while (listIterator.hasNext()) {
                final int nextIndex = listIterator.nextIndex();
                final String s2 = listIterator.next();
                if (s2.startsWith("{crafting_time}")) {
                    if (this.craftingRecipe.getCraftingTime() <= 0.0) {
                        listIterator.remove();
                        continue;
                    }
                    hashMap.put(s2, s2.replace("{crafting_time}", "").replace("#crafting-time#", CraftingRecipeDisplay.craftingTimeFormat.format(this.craftingRecipe.getCraftingTime())));
                }
                if (s2.startsWith("{conditions}")) {
                    n = nextIndex + 1;
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
            final ItemStack preview = this.craftingRecipe.getOutput().getPreview();
            final int amount = this.craftingRecipe.getOutput().getAmount();
            if (amount > 64) {
                this.lore.add(0, Message.STATION_BIG_STACK.format(ChatColor.GOLD, "#size#", "" + amount).toString());
            }
            else {
                preview.setAmount(amount);
            }
            final ItemMeta itemMeta = preview.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.values());
            preview.setItemMeta(itemMeta);
            final NBTItem value = NBTItem.get(preview);
            value.setDisplayNameComponent(LegacyComponent.parse(this.name.replace("#name#", ((amount > 1) ? (ChatColor.WHITE + "" + amount + " x ") : "") + MMOUtils.getDisplayName(preview))));
            final ArrayList<Component> loreComponents = new ArrayList<Component>();
            this.lore.forEach(s -> loreComponents.add(LegacyComponent.parse(s)));
            value.setLoreComponents((List)loreComponents);
            return value.addTag(new ItemTag[] { new ItemTag("recipeId", (Object)this.craftingRecipe.getId()) }).toItem();
        }
    }
}
