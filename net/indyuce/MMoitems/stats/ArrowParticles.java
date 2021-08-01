// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.ArrowParticlesEdition;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import org.bukkit.Particle;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.ArrowParticlesData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class ArrowParticles extends ItemStat
{
    public ArrowParticles() {
        super("ARROW_PARTICLES", VersionMaterial.LIME_STAINED_GLASS.toMaterial(), "Arrow Particles", new String[] { "Particles that display around", "the arrows your bow fires." }, new String[] { "bow", "crossbow" }, new Material[0]);
    }
    
    @Override
    public ArrowParticlesData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a valid config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        Validate.isTrue(configurationSection.contains("particle"), "Could not find arrow particle");
        final Particle value = Particle.valueOf(configurationSection.getString("particle").toUpperCase().replace("-", "_").replace(" ", "_"));
        final int int1 = configurationSection.getInt("amount");
        final double double1 = configurationSection.getDouble("offset");
        return ParticleData.isColorable(value) ? new ArrowParticlesData(value, int1, double1, configurationSection.getInt("color.red"), configurationSection.getInt("color.green"), configurationSection.getInt("color.blue")) : new ArrowParticlesData(value, int1, double1, configurationSection.getDouble("speed"));
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)statData.toString()));
        return list;
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
                final JsonObject asJsonObject = new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject();
                final Particle value = Particle.valueOf(asJsonObject.get("Particle").getAsString());
                final int asInt = asJsonObject.get("Amount").getAsInt();
                final double asDouble = asJsonObject.get("Offset").getAsDouble();
                if (ParticleData.isColorable(value)) {
                    return new ArrowParticlesData(value, asInt, asDouble, asJsonObject.get("Red").getAsInt(), asJsonObject.get("Green").getAsInt(), asJsonObject.get("Blue").getAsInt());
                }
                return new ArrowParticlesData(value, asInt, asDouble, asJsonObject.get("Speed").getAsDouble());
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        new ArrowParticlesEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String str = (String)array[0];
        if (str.equals("color")) {
            final String[] split = s.split(" ");
            final int int1 = Integer.parseInt(split[0]);
            final int int2 = Integer.parseInt(split[1]);
            final int int3 = Integer.parseInt(split[2]);
            editionInventory.getEditedSection().set("arrow-particles.color.red", (Object)int1);
            editionInventory.getEditedSection().set("arrow-particles.color.green", (Object)int2);
            editionInventory.getEditedSection().set("arrow-particles.color.blue", (Object)int3);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle color successfully set to " + ChatColor.translateAlternateColorCodes('&', "&c&l" + int1 + "&7 - &a&l" + int2 + "&7 - &9&l" + int3));
            return;
        }
        if (str.equals("particle")) {
            final Particle value = Particle.valueOf(s.toUpperCase().replace("-", "_").replace(" ", "_"));
            editionInventory.getEditedSection().set("arrow-particles.particle", (Object)value.name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle successfully set to " + ChatColor.GOLD + MMOUtils.caseOnWords(value.name().toLowerCase().replace("_", " ")) + ChatColor.GRAY + ".");
            return;
        }
        if (str.equals("amount")) {
            final int int4 = Integer.parseInt(s);
            editionInventory.getEditedSection().set("arrow-particles.amount", (Object)int4);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + "Amount" + ChatColor.GRAY + " set to " + ChatColor.GOLD + int4 + ChatColor.GRAY + ".");
            return;
        }
        final double double1 = MMOUtils.parseDouble(s);
        editionInventory.getEditedSection().set("arrow-particles." + str, (Object)double1);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + MMOUtils.caseOnWords(str.replace("-", " ")) + ChatColor.GRAY + " set to " + ChatColor.GOLD + double1 + ChatColor.GRAY + ".");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            final ArrowParticlesData arrowParticlesData = optional.get();
            list.add(ChatColor.GRAY + "Current Value:");
            list.add(ChatColor.GRAY + "* Particle: " + ChatColor.GOLD + MMOUtils.caseOnWords(arrowParticlesData.getParticle().name().replace("_", " ").toLowerCase()));
            list.add(ChatColor.GRAY + "* Amount: " + ChatColor.WHITE + arrowParticlesData.getAmount());
            list.add(ChatColor.GRAY + "* Offset: " + ChatColor.WHITE + arrowParticlesData.getOffset());
            list.add("");
            if (ParticleData.isColorable(arrowParticlesData.getParticle())) {
                list.add(ChatColor.translateAlternateColorCodes('&', "&7* Color: &c&l" + arrowParticlesData.getRed() + "&7 - &a&l" + arrowParticlesData.getGreen() + "&7 - &9&l" + arrowParticlesData.getBlue()));
            }
            else {
                list.add(ChatColor.GRAY + "* Speed: " + ChatColor.WHITE + arrowParticlesData.getSpeed());
            }
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to edit.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new ArrowParticlesData(Particle.EXPLOSION_LARGE, 1, 0.0, 1.0);
    }
}
