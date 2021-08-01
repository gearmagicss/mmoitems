// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonObject;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Particle;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.ProjectileParticlesData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class ProjectileParticles extends StringStat
{
    public ProjectileParticles() {
        super("PROJECTILE_PARTICLES", VersionMaterial.LIME_STAINED_GLASS.toMaterial(), "Projectile Particles", new String[] { "The projectile particle that your weapon shoots" }, new String[] { "lute" }, new Material[0]);
    }
    
    @Override
    public ProjectileParticlesData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a valid config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        Validate.isTrue(configurationSection.contains("particle"), "Could not find projectile particle");
        final Particle value = Particle.valueOf(configurationSection.getString("particle").toUpperCase().replace("-", "_").replace(" ", ""));
        return ProjectileParticlesData.isColorable(value) ? new ProjectileParticlesData(value, configurationSection.getInt("color.red"), configurationSection.getInt("color.green"), configurationSection.getInt("color.blue")) : new ProjectileParticlesData(value);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set("projectile-particles", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed the projectile particle.");
        }
        else {
            new StatEdition(editionInventory, this, new Object[0]).enable("Write in the chat the particle you want along with the color if applicable.", ChatColor.AQUA + "Format: {Particle} {Color}", "All particles can be found here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html");
        }
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            final JsonObject jsonObject = (JsonObject)MythicLib.plugin.getJson().parse(optional.get().toString(), (Class)JsonObject.class);
            final Particle value = Particle.valueOf(jsonObject.get("Particle").getAsString());
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + value);
            if (ProjectileParticlesData.isColorable(value)) {
                final String value2 = String.valueOf(jsonObject.get("Red"));
                final String value3 = String.valueOf(jsonObject.get("Green"));
                final String value4 = String.valueOf(jsonObject.get("Blue"));
                list.add(ChatColor.GRAY + "Color: " + ChatColor.GREEN + ((value == Particle.NOTE) ? value2 : (value2 + " " + value3 + " " + value4)));
            }
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to change this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String[] split = s.replace(", ", " ").replace(",", " ").split(" ");
        final Particle value = Particle.valueOf(split[0].toUpperCase().replace("-", "_").replace(" ", "_"));
        if (ProjectileParticlesData.isColorable(value)) {
            Validate.isTrue(split.length <= 4, "Too many arguments provided.");
            if (value.equals((Object)Particle.NOTE)) {
                Validate.isTrue(split.length == 2, "You must provide a color for this particle.\n" + MMOItems.plugin.getPrefix() + "NOTE particle colors only take a single value between 1 and 24.\n" + MMOItems.plugin.getPrefix() + ChatColor.AQUA + "Format: {Particle} {Color}");
                final int min = Math.min(24, Math.max(1, Integer.parseInt(split[1])));
                editionInventory.getEditedSection().set("projectile-particles.particle", (Object)value.name());
                editionInventory.getEditedSection().set("projectile-particles.color.red", (Object)min);
                editionInventory.getEditedSection().set("projectile-particles.color.green", (Object)0);
                editionInventory.getEditedSection().set("projectile-particles.color.blue", (Object)0);
                editionInventory.registerTemplateEdition();
                editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle successfully set to " + MMOUtils.caseOnWords(value.name().toLowerCase().replace("_", " ")) + " with color " + min);
            }
            else {
                Validate.isTrue(split.length == 4, "You must provide a color for this particle.\n" + MMOItems.plugin.getPrefix() + ChatColor.AQUA + "Format: {Particle} {R G B}");
                final int n = (split[1] != null) ? Math.min(255, Math.max(0, Integer.parseInt(split[1]))) : 0;
                final int n2 = (split[2] != null) ? Math.min(255, Math.max(0, Integer.parseInt(split[2]))) : 0;
                final int n3 = (split[3] != null) ? Math.min(255, Math.max(0, Integer.parseInt(split[3]))) : 0;
                editionInventory.getEditedSection().set("projectile-particles.particle", (Object)value.name());
                editionInventory.getEditedSection().set("projectile-particles.color.red", (Object)n);
                editionInventory.getEditedSection().set("projectile-particles.color.green", (Object)n2);
                editionInventory.getEditedSection().set("projectile-particles.color.blue", (Object)n3);
                editionInventory.registerTemplateEdition();
                editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle successfully set to " + MMOUtils.caseOnWords(value.name().toLowerCase().replace("_", " ")) + " with RGB color " + n + " " + n2 + " " + n3);
            }
        }
        else {
            Validate.isTrue(split.length == 1, "That particle cannot be assigned a color");
            editionInventory.getEditedSection().set("projectile-particles.particle", (Object)value.name());
            editionInventory.getEditedSection().set("projectile-particles.color.red", (Object)0);
            editionInventory.getEditedSection().set("projectile-particles.color.green", (Object)0);
            editionInventory.getEditedSection().set("projectile-particles.color.blue", (Object)0);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Particle successfully set to " + MMOUtils.caseOnWords(value.name().toLowerCase().replace("_", " ")));
        }
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
    
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            try {
                final JsonObject asJsonObject = new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject();
                final Particle value = Particle.valueOf(asJsonObject.get("Particle").getAsString());
                if (ProjectileParticlesData.isColorable(value)) {
                    return new ProjectileParticlesData(value, asJsonObject.get("Red").getAsInt(), asJsonObject.get("Green").getAsInt(), asJsonObject.get("Blue").getAsInt());
                }
                return new ProjectileParticlesData(value);
            }
            catch (JsonSyntaxException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
