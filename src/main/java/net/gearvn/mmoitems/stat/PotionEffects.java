// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.StringData;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.bukkit.inventory.meta.PotionMeta;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.PotionEffectListData;
import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.bukkit.potion.PotionEffectType;
import java.util.Set;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectListData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class PotionEffects extends ItemStat
{
    public PotionEffects() {
        super("POTION_EFFECT", Material.POTION, "Potion Effects", new String[] { "The effects of your potion.", "(May have an impact on color)." }, new String[] { "all" }, new Material[] { Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.TIPPED_ARROW });
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        return new RandomPotionEffectListData((ConfigurationSection)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.POTION_EFFECTS, new Object[0]).enable("Write in the chat the potion effect you want to add.", ChatColor.AQUA + "Format: {Effect Name} {Duration} {Amplifier}", ChatColor.AQUA + "Other Format: {Effect Name}|{Duration Numeric Formula}|{Amplifier Numeric Formula}");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("potion-effect")) {
            final Set keys = editionInventory.getEditedSection().getConfigurationSection("potion-effect").getKeys(false);
            final String str = new ArrayList<String>(keys).get(keys.size() - 1);
            editionInventory.getEditedSection().set("potion-effect." + str, (Object)null);
            if (keys.size() <= 1) {
                editionInventory.getEditedSection().set("potion-effect", (Object)null);
            }
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + ChatColor.GOLD + this.formatName(str) + ChatColor.GRAY + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        if (str.contains("|")) {
            final String[] split = str.split("\\|");
            final PotionEffectType byName = PotionEffectType.getByName(split[0].replace("-", "_"));
            Validate.notNull((Object)byName, split[0] + " is not a valid potion effect. All potion effects can be found here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html");
            final NumericStatFormula numericStatFormula = new NumericStatFormula(split[1]);
            final NumericStatFormula numericStatFormula2 = new NumericStatFormula(split[2]);
            numericStatFormula.fillConfigurationSection(editionInventory.getEditedSection(), "potion-effect." + byName.getName() + ".duration");
            numericStatFormula2.fillConfigurationSection(editionInventory.getEditedSection(), "potion-effect." + byName.getName() + ".amplifier");
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + this.formatName(byName) + ChatColor.GRAY + " successfully added.");
            return;
        }
        final String[] split2 = str.split(" ");
        Validate.isTrue(split2.length == 3, str + " is not a valid {Effect Name} {Duration} {Amplifier}. Example: 'FAST_DIGGING 30 3' stands for Haste 3 for 30 seconds.");
        final PotionEffectType byName2 = PotionEffectType.getByName(split2[0].replace("-", "_"));
        Validate.notNull((Object)byName2, split2[0] + " is not a valid potion effect. All potion effects can be found here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html");
        final double double1 = MMOUtils.parseDouble(split2[1]);
        final int n = (int)MMOUtils.parseDouble(split2[2]);
        editionInventory.getEditedSection().set("potion-effect." + byName2.getName() + ".duration", (Object)double1);
        editionInventory.getEditedSection().set("potion-effect." + byName2.getName() + ".amplifier", (Object)n);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + this.formatName(byName2) + " " + n + ChatColor.GRAY + " successfully added.");
    }
    
    private String formatName(final PotionEffectType potionEffectType) {
        return this.formatName(potionEffectType.getName());
    }
    
    private String formatName(final String s) {
        return MMOUtils.caseOnWords(s.replace("_", " ").toLowerCase());
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            for (final RandomPotionEffectData randomPotionEffectData : optional.get().getEffects()) {
                list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + MMOUtils.caseOnWords(randomPotionEffectData.getType().getName().toLowerCase().replace("_", " ")) + " " + randomPotionEffectData.getAmplifier().toString() + " " + ChatColor.GRAY + "(" + ChatColor.GREEN + randomPotionEffectData.getDuration().toString() + ChatColor.GRAY + "s)");
            }
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add an effect.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last effect.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new PotionEffectListData(new PotionEffectData[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (itemStackBuilder.getItemStack().getType().name().contains("POTION") || itemStackBuilder.getItemStack().getType() == Material.TIPPED_ARROW) {
            final Iterator<PotionEffectData> iterator = ((PotionEffectListData)statData).getEffects().iterator();
            while (iterator.hasNext()) {
                ((PotionMeta)itemStackBuilder.getMeta()).addCustomEffect(iterator.next().toEffect(), false);
            }
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            readMMOItem.setData(this, new StringData(readMMOItem.getNBT().getString(this.getNBTPath())));
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new StringData((String)tagAtPath.getValue());
        }
        return null;
    }
}
