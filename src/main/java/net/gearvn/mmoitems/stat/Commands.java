// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.CommandListEdition;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.CommandData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.CommandListData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Commands extends ItemStat
{
    private static final int max = 15;
    
    public Commands() {
        super("COMMANDS", VersionMaterial.COMMAND_BLOCK_MINECART.toMaterial(), "Commands", new String[] { "The commands your item", "performs when right clicked." }, new String[] { "!armor", "!block", "!gem_stone", "all" }, new Material[0]);
    }
    
    @Override
    public CommandListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        final CommandListData commandListData = new CommandListData(new CommandData[0]);
        final Iterator iterator = configurationSection.getKeys(false).iterator();
        while (iterator.hasNext()) {
            final ConfigurationSection configurationSection2 = configurationSection.getConfigurationSection((String)iterator.next());
            commandListData.add(new CommandData(configurationSection2.getString("format"), configurationSection2.getDouble("delay"), configurationSection2.getBoolean("console"), configurationSection2.getBoolean("op")));
        }
        return commandListData;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        new CommandListEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull String s, final Object... array) {
        if (editionInventory.getEditedSection().contains("commands") && editionInventory.getEditedSection().getConfigurationSection("commands").getKeys(false).size() >= 15) {
            editionInventory.open();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Your item has reached the " + 15 + " commands limit.");
            return;
        }
        double double1 = 0.0;
        boolean b = false;
        boolean b2 = false;
        final String[] split = s.split(" ");
        for (int n = 0; n < split.length && split[n].startsWith("-"); ++n) {
            final String str = split[n];
            if (str.startsWith("-d:")) {
                double1 = MMOUtils.parseDouble(str.substring(3));
                s = s.replaceFirst(str + " ", "");
            }
            else if (str.equalsIgnoreCase("-c")) {
                b = true;
                s = s.replaceFirst(str + " ", "");
            }
            else if (str.equalsIgnoreCase("-op")) {
                b2 = true;
                s = s.replaceFirst(str + " ", "");
            }
        }
        final ConfigurationSection configurationSection = editionInventory.getEditedSection().getConfigurationSection("commands");
        String string = "cmd16";
        if (configurationSection == null) {
            string = "cmd0";
        }
        else {
            for (int i = 0; i < 15; ++i) {
                if (!configurationSection.contains("cmd" + i)) {
                    string = "cmd" + i;
                    break;
                }
            }
        }
        editionInventory.getEditedSection().set("commands." + string + ".format", (Object)s);
        editionInventory.getEditedSection().set("commands." + string + ".delay", (Object)double1);
        editionInventory.getEditedSection().set("commands." + string + ".console", (Object)(b ? Boolean.valueOf(b) : null));
        editionInventory.getEditedSection().set("commands." + string + ".op", (Object)(b2 ? Boolean.valueOf(b2) : null));
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Command successfully registered.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.GRAY + "Current Commands: " + ChatColor.RED + (optional.isPresent() ? Integer.valueOf(optional.get().getCommands().size()) : "0"));
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to edit item commands.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new CommandListData(new CommandData[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        final ArrayList<String> list = new ArrayList<String>();
        ((CommandListData)statData).getCommands().forEach(commandData -> list.add(ItemStat.translate("command").replace("#c", "/" + commandData.getCommand()).replace("#d", "" + commandData.getDelay())));
        itemStackBuilder.getLore().insert("commands", list);
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        final JsonArray jsonArray = new JsonArray();
        for (final CommandData commandData : ((CommandListData)statData).getCommands()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Command", commandData.getCommand());
            jsonObject.addProperty("Delay", (Number)commandData.getDelay());
            jsonObject.addProperty("Console", Boolean.valueOf(commandData.isConsoleCommand()));
            jsonObject.addProperty("Op", Boolean.valueOf(commandData.hasOpPerms()));
            jsonArray.add((JsonElement)jsonObject);
        }
        list.add(new ItemTag(this.getNBTPath(), (Object)jsonArray.toString()));
        return list;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final CommandListData commandListData = (CommandListData)this.getLoadedNBT(list);
        if (commandListData != null) {
            readMMOItem.setData(this, commandListData);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            try {
                final CommandListData commandListData = new CommandListData(new CommandData[0]);
                for (final JsonElement jsonElement : new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonArray()) {
                    if (jsonElement.isJsonObject()) {
                        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                        commandListData.add(new CommandData(asJsonObject.get("Command").getAsString(), asJsonObject.get("Delay").getAsDouble(), asJsonObject.get("Console").getAsBoolean(), asJsonObject.get("Op").getAsBoolean()));
                    }
                }
                return commandListData;
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
}
