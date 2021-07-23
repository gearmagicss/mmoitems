// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.List;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Optional;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.util.EnumUtils;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.MaterialData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class MaterialStat extends ItemStat
{
    public MaterialStat() {
        super("MATERIAL", VersionMaterial.GRASS_BLOCK.toMaterial(), "Material", new String[] { "Your item material." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public MaterialData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof String, "Must specify material name as string");
        return new MaterialData(Material.valueOf(((String)o).toUpperCase().replace("-", "_").replace(" ", "_")));
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        new StatEdition(editionInventory, ItemStats.MATERIAL, new Object[0]).enable("Write in the chat the material you want.");
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final Optional ifPresent = EnumUtils.getIfPresent((Class)Material.class, s.toUpperCase().replace("-", "_").replace(" ", "_"));
        MMOItems.plugin.getLanguage().getClass();
        if (ifPresent.isPresent()) {
            editionInventory.getEditedSection().set("material", (Object)ifPresent.get().name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Material successfully changed to " + ifPresent.get().name() + ".");
        }
        else {
            editionInventory.getPlayer().spigot().sendMessage(new ComponentBuilder("Invalid material! (Click for a list of valid materials)").color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html")).create());
        }
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        readMMOItem.setData(this, new MaterialData(readMMOItem.getNBT().getItem().getType()));
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.GRAY + "Current Value: " + (optional.isPresent() ? (ChatColor.GREEN + MMOUtils.caseOnWords(optional.get().getMaterial().name().toLowerCase().replace("_", " "))) : (ChatColor.RED + "None")));
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to change this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new MaterialData(Material.IRON_ORE);
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
}
