// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import java.util.Iterator;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.inventory.InventoryAction;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.Collection;
import java.util.Arrays;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class ChooseStat extends StringStat
{
    private final ArrayList<String> choices;
    private final HashMap<String, String> hints;
    
    public ChooseStat(final String s, final Material material, final String s2, final String[] array, final String[] array2, final Material... array3) {
        super(s, material, s2, array, array2, array3);
        this.choices = new ArrayList<String>();
        this.hints = new HashMap<String, String>();
    }
    
    public void addChoices(final String... a) {
        this.choices.addAll(Arrays.asList(a));
    }
    
    public void setHint(final String key, final String value) {
        this.hints.put(key, value);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        Validate.isTrue(this.choices.size() > 0, "§7Invalid Chooseable Item Stat " + ChatColor.GOLD + this.getId() + "§7' - §cNo options to choose from.");
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set(this.getPath(), (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + this.getName() + ".");
        }
        else {
            final String string = editionInventory.getEditedSection().getString(this.getPath());
            int index = 0;
            if (string != null && this.choices.contains(string)) {
                index = this.choices.indexOf(string);
            }
            if (++index >= this.choices.size()) {
                index = 0;
            }
            final String str = this.choices.get(index);
            editionInventory.getEditedSection().set(this.getPath(), (Object)str);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to " + str + ChatColor.GRAY + ".");
        }
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        Validate.isTrue(this.choices.size() > 0, "§7Invalid Chooseable Item Stat " + ChatColor.GOLD + this.getId() + "§7' - §cNo options to choose from.");
        String string = this.choices.get(0);
        if (optional.isPresent()) {
            string = optional.get().toString();
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + string);
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + string);
        }
        if (this.hints.containsKey(string)) {
            final Iterator iterator = SilentNumbers.chop((String)this.hints.get(string), 50, "").iterator();
            while (iterator.hasNext()) {
                list.add(ChatColor.GRAY + "   " + iterator.next());
            }
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to return to default value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to cycle through the available options:");
        for (final String str : this.choices) {
            String str2 = ChatColor.GOLD.toString();
            if (str.equals(string)) {
                str2 = ChatColor.RED.toString() + ChatColor.BOLD.toString();
            }
            list.add(str2 + "  " + "\u25b8" + " §7" + str);
        }
    }
}
