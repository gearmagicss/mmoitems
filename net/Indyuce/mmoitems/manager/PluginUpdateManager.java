// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.configuration.file.FileConfiguration;
import java.util.UUID;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.ArrayList;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.md_5.bungee.api.ChatColor;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Collection;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Consumer;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.PluginUpdate;
import java.util.Map;

public class PluginUpdateManager
{
    private final Map<Integer, PluginUpdate> updates;
    
    public PluginUpdateManager() {
        this.updates = new HashMap<Integer, PluginUpdate>();
        this.register(new PluginUpdate(1, new String[] { "Applies a fix for skull textures values in 4.7.1.", "Texture values data storage changed in 4.7.1 due to the UUID change." }, (Consumer<CommandSender>)(commandSender -> {
            final Iterator<Type> iterator = MMOItems.plugin.getTypes().getAll().iterator();
            while (iterator.hasNext()) {
                final ConfigFile configFile = iterator.next().getConfigFile();
                final Iterator iterator2 = configFile.getConfig().getKeys(false).iterator();
                while (iterator2.hasNext()) {
                    final ConfigurationSection configurationSection = configFile.getConfig().getConfigurationSection((String)iterator2.next());
                    if (configurationSection.contains("skull-texture") && configurationSection.get("skull-texture") instanceof String) {
                        configurationSection.set("skull-texture.value", (Object)configurationSection.getString("skull-texture"));
                        configurationSection.set("skull-texture.uuid", (Object)UUID.randomUUID().toString());
                    }
                }
                configFile.save();
            }
        })));
        this.register(new PluginUpdate(3, new String[] { "5.3.2: converts all your crafting station recipes to the newest config format.", "&cWarning, running this update will get rid of your # config file comments." }, (Consumer<CommandSender>)(commandSender -> {
            for (final File file : new File(MMOItems.plugin.getDataFolder() + "/crafting-stations").listFiles()) {
                final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
                if (((FileConfiguration)loadConfiguration).contains("recipes")) {
                    for (final String str : ((FileConfiguration)loadConfiguration).getConfigurationSection("recipes").getKeys(false)) {
                        try {
                            final List stringList = ((FileConfiguration)loadConfiguration).getStringList("recipes." + str + ".ingredients");
                            final ArrayList<String> list = new ArrayList<String>();
                            for (final String str2 : stringList) {
                                final String[] split = str2.split(" ");
                                if (split[0].equals("mmoitem")) {
                                    String str3 = "mmoitem{type=" + split[1] + ",id=" + split[2];
                                    if (split.length > 3) {
                                        str3 = str3 + ",amount=" + split[3];
                                    }
                                    if (split.length > 4) {
                                        str3 = str3 + ",display=\"" + split[4].replace("_", " ") + "\"";
                                    }
                                    list.add(str3 + "}");
                                }
                                else if (split[0].equals("vanilla")) {
                                    String s = "vanilla{type=" + split[1];
                                    if (split.length > 2 && !split[2].equals(".")) {
                                        s = s + ",name=\"" + split[2] + "\"";
                                    }
                                    if (split.length > 3) {
                                        s = s + ",amount=" + split[3];
                                    }
                                    if (split.length > 4) {
                                        s = s + ",display=\"" + split[4].replace("_", " ") + "\"";
                                    }
                                    list.add(s + "}");
                                }
                                else {
                                    MMOItems.plugin.getLogger().log(Level.INFO, "Config Update 3: Could not match ingredient from '" + str2 + "' from recipe '" + str + "', added it anyway.");
                                    list.add(str2);
                                }
                            }
                            ((FileConfiguration)loadConfiguration).set("recipes." + str + ".ingredients", (Object)list);
                            final List stringList2 = ((FileConfiguration)loadConfiguration).getStringList("recipes." + str + ".conditions");
                            final ArrayList<String> list2 = new ArrayList<String>();
                            for (final String str4 : stringList2) {
                                final String[] split2 = str4.split(" ");
                                if (split2[0].equalsIgnoreCase("class")) {
                                    list2.add("class{list=\"" + str4.replace(split2[0] + " ", "").replace(" ", ",") + "\"}");
                                }
                                else if (split2[0].equalsIgnoreCase("perms")) {
                                    list2.add("permission{list=\"" + str4.replace(split2[0] + " ", "").replace(" ", ",") + "\"}");
                                }
                                else if (split2[0].equalsIgnoreCase("food") || split2[0].equals("mana") || split2[0].equals("stamina")) {
                                    list2.add(split2[0] + "{amount=" + split2[1] + "}");
                                }
                                else if (split2[0].equalsIgnoreCase("level")) {
                                    list2.add("level{level=" + split2[1] + "}");
                                }
                                else if (split2[0].equalsIgnoreCase("profession")) {
                                    list2.add("profession{profession=" + split2[1] + ",level=" + split2[2] + "}");
                                }
                                else if (split2[0].equalsIgnoreCase("exp")) {
                                    list2.add("exp{profession=" + split2[1] + ",amount=" + split2[2] + "}");
                                }
                                else {
                                    MMOItems.plugin.getLogger().log(Level.INFO, "Config Update 3: Could not match condition from '" + str4 + "' from recipe '" + str + "', added it anyway.");
                                    list2.add(str4);
                                }
                            }
                            ((FileConfiguration)loadConfiguration).set("recipes." + str + ".conditions", (Object)list2);
                        }
                        catch (Exception ex) {
                            MMOItems.plugin.getLogger().log(Level.INFO, "Config Update 3: Could not convert recipe with key '" + str + "': " + ex.getMessage());
                        }
                    }
                    try {
                        ((FileConfiguration)loadConfiguration).save(file);
                    }
                    catch (IOException ex2) {
                        MMOItems.plugin.getLogger().log(Level.INFO, "Config Update 3: Could not save config '" + file.getName() + "': " + ex2.getMessage());
                    }
                }
            }
        })));
        this.register(new PluginUpdate(2, new String[] { "Enables the item updater for every item.", "&cNot recommended unless you know what you are doing.", "&e(No longer available)" }, (Consumer<CommandSender>)(commandSender -> {
            commandSender.sendMessage(ChatColor.RED + "This command is no longer available.");
            commandSender.sendMessage(ChatColor.RED + "Please refer to the Revision System on the wiki.");
        })));
        this.register(new PluginUpdate(4, new String[] { "Transforms all your current MMOItems into item templates and fixes some stat formats which have been changed.", "&cIt is REALLY important to save a backup before using this config update!" }, (Consumer<CommandSender>)(commandSender -> {
            final Iterator<Type> iterator = MMOItems.plugin.getTypes().getAll().iterator();
            while (iterator.hasNext()) {
                final ConfigFile configFile = iterator.next().getConfigFile();
                for (final String s : configFile.getConfig().getKeys(false)) {
                    if (configFile.getConfig().getConfigurationSection(s).contains("base")) {
                        continue;
                    }
                    configFile.getConfig().createSection(s + ".base", configFile.getConfig().getConfigurationSection(s).getValues(false));
                    for (final String str : configFile.getConfig().getConfigurationSection(s).getKeys(false)) {
                        if (!str.equals("base")) {
                            configFile.getConfig().set(s + "." + str, (Object)null);
                        }
                    }
                    this.rename(configFile.getConfig().getConfigurationSection(s + ".base"), "regeneration", "health-regeneration");
                    this.rename(configFile.getConfig().getConfigurationSection(s + ".base"), "element.light", "element.lightness");
                    if (configFile.getConfig().getConfigurationSection(s + ".base").contains("consume-sound")) {
                        this.rename(configFile.getConfig().getConfigurationSection(s + ".base"), "consume-sound", "sounds.on-consume.sound");
                        configFile.getConfig().set(s + ".base.sounds.on-consume.volume", (Object)1.0);
                        configFile.getConfig().set(s + ".base.sounds.on-consume.pitch", (Object)1.0);
                    }
                    if (configFile.getConfig().getConfigurationSection(s + ".base").contains("effects")) {
                        for (final String str2 : configFile.getConfig().getConfigurationSection(s + ".base.effects").getKeys(false)) {
                            final String[] split = configFile.getConfig().getString(s + ".base.effects." + str2).split(",");
                            if (split.length > 1) {
                                configFile.getConfig().set(s + ".base.new-effects." + str2 + ".duration", (Object)Double.parseDouble(split[0]));
                                configFile.getConfig().set(s + ".base.new-effects." + str2 + ".amplifier", (Object)Double.parseDouble(split[1]));
                            }
                        }
                        configFile.getConfig().set(s + ".base.effects", (Object)null);
                        this.rename(configFile.getConfig().getConfigurationSection(s + ".base"), "new-effects", "effects");
                    }
                    if (configFile.getConfig().getConfigurationSection(s + ".base").contains("restore")) {
                        configFile.getConfig().set(s + ".base.restore-health", (Object)configFile.getConfig().getDouble(s + ".base.restore.health"));
                        configFile.getConfig().set(s + ".base.restore-food", (Object)configFile.getConfig().getDouble(s + ".base.restore.food"));
                        configFile.getConfig().set(s + ".base.restore-saturation", (Object)configFile.getConfig().getDouble(s + ".base.restore.saturation"));
                        configFile.getConfig().set(s + ".base.restore", (Object)null);
                    }
                    for (final String s2 : configFile.getConfig().getConfigurationSection(s + ".base").getKeys(false)) {
                        final String string = configFile.getConfig().getString(s + ".base." + s2);
                        if (string != null) {
                            try {
                                final String[] split2 = string.split("=");
                                Validate.isTrue(split2.length == 2);
                                final double double1 = Double.parseDouble(split2[0]);
                                final double double2 = Double.parseDouble(split2[1]);
                                final double d = (double1 + double2) / 2.0;
                                final double max = Math.max(Math.abs(double1), Math.abs(double2));
                                final double d2 = (max - d) / max;
                                configFile.getConfig().set(s + ".base." + s2 + ".base", (Object)d);
                                configFile.getConfig().set(s + ".base." + s2 + ".spread", (Object)(d2 / 3.0));
                                configFile.getConfig().set(s + ".base." + s2 + ".max-spread", (Object)d2);
                            }
                            catch (Exception ex) {}
                        }
                    }
                }
                configFile.save();
            }
        })));
    }
    
    public void register(final PluginUpdate pluginUpdate) {
        this.updates.put(pluginUpdate.getId(), pluginUpdate);
    }
    
    public boolean has(final int i) {
        return this.updates.containsKey(i);
    }
    
    public PluginUpdate get(final int i) {
        return this.updates.get(i);
    }
    
    public Collection<PluginUpdate> getAll() {
        return this.updates.values();
    }
    
    private void rename(final ConfigurationSection configurationSection, final String s, final String s2) {
        if (configurationSection.contains(s)) {
            final Object value = configurationSection.get(s);
            configurationSection.set(s, (Object)null);
            configurationSection.set(s2, value);
        }
    }
}
