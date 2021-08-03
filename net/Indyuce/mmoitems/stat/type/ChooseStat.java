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
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Material;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public abstract class ChooseStat extends StringStat
{
    @NotNull
    ArrayList<String> chooseableList;
    @NotNull
    HashMap<String, String> chooseableDefs;
    
    public ChooseStat(final String s, final Material material, final String s2, final String[] array, final String[] array2, final Material... array3) {
        super(s, material, s2, array, array2, array3);
        this.chooseableDefs = new HashMap<String, String>();
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        Validate.isTrue(this.chooseableList.size() > 0, "§7Invalid Chooseable Item Stat " + ChatColor.GOLD + this.getId() + "§7' - §cNo options to choose from.");
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set(this.getPath(), (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + this.getName() + ".");
        }
        else {
            final String string = editionInventory.getEditedSection().getString(this.getPath());
            int index = 0;
            if (string != null && this.chooseableList.contains(string)) {
                index = this.chooseableList.indexOf(string);
            }
            if (++index >= this.chooseableList.size()) {
                index = 0;
            }
            final String str = this.chooseableList.get(index);
            editionInventory.getEditedSection().set(this.getPath(), (Object)str);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to " + str + ChatColor.GRAY + ".");
        }
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        Validate.isTrue(this.chooseableList.size() > 0, "§7Invalid Chooseable Item Stat " + ChatColor.GOLD + this.getId() + "§7' - §cNo options to choose from.");
        String string = this.chooseableList.get(0);
        if (optional.isPresent()) {
            string = optional.get().toString();
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + string);
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + string);
        }
        if (this.chooseableDefs.containsKey(string)) {
            final Iterator iterator = SilentNumbers.chop((String)this.chooseableDefs.get(string), 50, "").iterator();
            while (iterator.hasNext()) {
                list.add(ChatColor.GRAY + "   " + iterator.next());
            }
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to return to default value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to cycle through the available options:");
        for (final String str : this.chooseableList) {
            String str2 = ChatColor.GOLD.toString();
            if (str.equals(string)) {
                str2 = ChatColor.RED.toString() + ChatColor.BOLD.toString();
            }
            list.add(str2 + "  " + "\u25b8" + " §7" + str);
        }
    }
    
    public void InitializeChooseableList(@NotNull final ArrayList<String> chooseableList) {
        this.chooseableList = chooseableList;
    }
    
    public void HintChooseableDefs(@NotNull final HashMap<String, String> chooseableDefs) {
        this.chooseableDefs = chooseableDefs;
    }
}
