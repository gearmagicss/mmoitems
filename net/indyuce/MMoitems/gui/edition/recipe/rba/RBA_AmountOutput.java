// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import io.lumine.mythic.utils.version.ServerVersion;
import java.util.Objects;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RBA_AmountOutput extends RecipeButtonAction
{
    @NotNull
    public final String[] amountLog;
    @NotNull
    final ItemStack button;
    public static final String AMOUNT_INGREDIENTS = "amount";
    
    public RBA_AmountOutput(@NotNull final RecipeMakerGUI recipeMakerGUI, @NotNull final ItemStack itemStack) {
        super(recipeMakerGUI);
        this.amountLog = new String[] { FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Write in the chat the amount of output of this recipe.", new String[0]), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "It must be an integer number, ex $e4$b.", new String[0]) };
        this.button = RecipeMakerGUI.rename(ItemFactory.of(itemStack.getType()).lore((Iterable)SilentNumbers.chop("The amount of items produced every time the player crafts.", 65, "§7")).build(), "§cChoose Output Amount");
        final ItemMeta itemMeta = Objects.requireNonNull(this.button.getItemMeta());
        final ItemMeta itemMeta2 = Objects.requireNonNull(itemStack.getItemMeta());
        if (ServerVersion.get().getMinor() >= 14 && itemMeta.hasCustomModelData()) {
            itemMeta.setCustomModelData(Integer.valueOf(itemMeta2.getCustomModelData()));
        }
        if (itemMeta2 instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)itemMeta).setColor(((LeatherArmorMeta)itemMeta2).getColor());
        }
        if (itemMeta2 instanceof BannerMeta) {
            ((BannerMeta)itemMeta).setPatterns(((BannerMeta)itemMeta2).getPatterns());
        }
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS });
        this.button.setItemMeta(itemMeta);
    }
    
    @Override
    public boolean runPrimary() {
        new StatEdition(this.inv, ItemStats.CRAFTING, new Object[] { 2, this }).enable(this.amountLog);
        return true;
    }
    
    @Override
    public void primaryProcessInput(@NotNull final String str, final Object... array) {
        final Integer integerParse = SilentNumbers.IntegerParse(str);
        if (integerParse == null) {
            throw new IllegalArgumentException("Expected an integer number instead of $u" + str);
        }
        if (integerParse > 64) {
            throw new IllegalArgumentException("Max stack size is $e64$b, Minecraft doesnt support $u" + str);
        }
        if (integerParse <= 0) {
            throw new IllegalArgumentException("Min output stack size is $e0$b, you specified $u" + str);
        }
        this.getInv().getNameSection().set("amount", (Object)integerParse);
    }
    
    @Override
    public boolean runSecondary() {
        this.getInv().getNameSection().set("amount", (Object)null);
        this.clickSFX();
        this.inv.registerTemplateEdition();
        return true;
    }
    
    public int getOutputAmount() {
        return this.getInv().getNameSection().getInt("amount", 1);
    }
    
    @Override
    public void secondaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    @NotNull
    @Override
    public ItemStack getButton() {
        final ItemStack clone = this.button.clone();
        clone.setAmount(this.getOutputAmount());
        return RecipeMakerGUI.addLore(clone, SilentNumbers.toArrayList((Object[])new String[] { "", ChatColor.YELLOW + "\u25ba" + " Right click to reset to 1.", ChatColor.YELLOW + "\u25ba" + " Left click to edit amount." }));
    }
}
