// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.type.RBA_BooleanButton;

public class RBA_HideFromBook extends RBA_BooleanButton
{
    public static final String BOOK_HIDDEN = "hidden";
    @NotNull
    final ItemStack booleanButton;
    
    public RBA_HideFromBook(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.booleanButton = RecipeMakerGUI.addLore(ItemFactory.of(Material.KNOWLEDGE_BOOK).name("§cHide from Crafting Book").lore((Iterable)SilentNumbers.chop("Even if the crafting book is enabled, this recipe wont be automatically unlocked by players.", 65, "§7")).build(), SilentNumbers.toArrayList((Object[])new String[] { "" }));
    }
    
    @NotNull
    @Override
    public String getBooleanConfigPath() {
        return "hidden";
    }
    
    @Override
    public boolean runSecondary() {
        final ItemStack itemStack = new ItemStack(Material.KNOWLEDGE_BOOK);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof KnowledgeBookMeta) {
            ((KnowledgeBookMeta)itemMeta).addRecipe(new NamespacedKey[] { MMOItems.plugin.getRecipes().getRecipeKey(this.getInv().getEdited().getType(), this.getInv().getEdited().getId(), this.getInv().getRecipeRegistry().getRecipeConfigPath(), this.getInv().getRecipeName()) });
        }
        itemStack.setItemMeta(itemMeta);
        this.getInv().getPlayer().getInventory().addItem(new ItemStack[] { itemStack });
        this.getInv().getPlayer().playSound(this.getInv().getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f);
        return true;
    }
    
    @NotNull
    @Override
    public ItemStack getBooleanButton() {
        return this.booleanButton;
    }
    
    @NotNull
    @Override
    public ItemStack getButton() {
        return RecipeMakerGUI.addLore(this.getBooleanButton().clone(), SilentNumbers.toArrayList((Object[])new String[] { "", "§7Currently in Book? " + (this.isEnabled() ? "§cNO§8, it's hidden." : "§aYES§8, it's shown."), "", ChatColor.YELLOW + "\u25ba" + " Right click to generate recipe unlock book.", ChatColor.YELLOW + "\u25ba" + " Left click to toggle this option." }));
    }
}
