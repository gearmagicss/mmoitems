// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.particle.api.ParticleType;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.gui.edition.ParticlesEdition;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class ItemParticles extends ItemStat
{
    public ItemParticles() {
        super("ITEM_PARTICLES", VersionMaterial.PINK_STAINED_GLASS.toMaterial(), "Item Particles", new String[] { "The particles displayed when", "holding/wearing your item.", "", ChatColor.BLUE + "A tutorial is available on the wiki." }, new String[] { "all", "!block" }, new Material[0]);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        new ParticlesEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
    }
    
    @Override
    public ParticleData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        if (((ConfigurationSection)o).getKeys(false).size() < 1) {
            throw new IllegalArgumentException("");
        }
        return new ParticleData((ConfigurationSection)o);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((ParticleData)statData).toJson().toString()));
        return list;
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to setup the item particles.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new ParticleData(ParticleType.AURA, Particle.EXPLOSION_LARGE);
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String str = (String)array[0];
        final String replace = s.toUpperCase().replace("-", "_").replace(" ", "_");
        if (str.equals("particle-type")) {
            final ParticleType value = ParticleType.valueOf(replace);
            editionInventory.getEditedSection().set("item-particles.type", (Object)value.name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle type successfully set to " + ChatColor.GOLD + value.getDefaultName() + ChatColor.GRAY + ".");
            return;
        }
        if (str.equals("particle-color")) {
            final String[] split = s.split(" ");
            final int int1 = Integer.parseInt(split[0]);
            final int int2 = Integer.parseInt(split[1]);
            final int int3 = Integer.parseInt(split[2]);
            editionInventory.getEditedSection().set("item-particles.color.red", (Object)int1);
            editionInventory.getEditedSection().set("item-particles.color.green", (Object)int2);
            editionInventory.getEditedSection().set("item-particles.color.blue", (Object)int3);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle color successfully set to " + ChatColor.RED + ChatColor.BOLD + int1 + ChatColor.GRAY + " - " + ChatColor.GREEN + ChatColor.BOLD + int2 + ChatColor.GRAY + " - " + ChatColor.BLUE + ChatColor.BOLD + int3 + ChatColor.GRAY + ".");
            return;
        }
        if (str.equals("particle")) {
            final Particle value2 = Particle.valueOf(replace);
            editionInventory.getEditedSection().set("item-particles.particle", (Object)value2.name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle successfully set to " + ChatColor.GOLD + MMOUtils.caseOnWords(value2.name().toLowerCase().replace("_", " ")) + ChatColor.GRAY + ".");
            return;
        }
        final double double1 = MMOUtils.parseDouble(s);
        editionInventory.getEditedSection().set("item-particles." + str, (Object)double1);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + MMOUtils.caseOnWords(str.replace("-", " ")) + ChatColor.GRAY + " set to " + ChatColor.GOLD + double1 + ChatColor.GRAY + ".");
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
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            try {
                return new ParticleData(new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject());
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
}
