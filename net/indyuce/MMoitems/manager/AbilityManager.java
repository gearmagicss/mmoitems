// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Enumeration;
import org.bukkit.configuration.file.FileConfiguration;
import net.Indyuce.mmoitems.comp.mythicmobs.MythicMobsAbility;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Collection;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ability.Ability;
import java.util.Map;

public class AbilityManager
{
    private final Map<String, Ability> abilities;
    private final Map<String, Ability> miAbilities;
    private final Map<String, Ability> mmAbilities;
    private final Map<String, Ability> tpAbilities;
    private boolean registrationIsDone;
    
    public AbilityManager() {
        this.abilities = new HashMap<String, Ability>();
        this.miAbilities = new HashMap<String, Ability>();
        this.mmAbilities = new HashMap<String, Ability>();
        this.tpAbilities = new HashMap<String, Ability>();
        this.registrationIsDone = false;
    }
    
    public Ability getAbility(final String s) {
        return this.abilities.get(s);
    }
    
    public boolean hasAbility(final String s) {
        return this.abilities.containsKey(s);
    }
    
    @Deprecated
    public Collection<Ability> getAll() {
        return this.abilities.values();
    }
    
    public Collection<Ability> getAllAbilities() {
        return this.abilities.values();
    }
    
    public Collection<Ability> getAllMMOItemsAbilities() {
        return this.miAbilities.values();
    }
    
    public Collection<Ability> getAllMythicMobsAbilities() {
        return this.mmAbilities.values();
    }
    
    public Collection<Ability> getAllThirdPartyAbilities() {
        return this.tpAbilities.values();
    }
    
    public void registerAbility(final Ability ability) {
        if (this.registerAbility(ability, false, true)) {
            MMOItems.plugin.getLogger().log(Level.INFO, "Loaded third party ability: " + ability.getName() + " from " + JavaPlugin.getProvidingPlugin((Class)ability.getClass()).getName() + ".");
        }
    }
    
    public void registerAbilities(final Ability... array) {
        int i = 0;
        for (int length = array.length, j = 0; j < length; ++j) {
            if (this.registerAbility(array[j], false, true)) {
                ++i;
            }
        }
        MMOItems.plugin.getLogger().log(Level.INFO, "Loaded " + i + " third party abilities from " + JavaPlugin.getProvidingPlugin((Class)array[0].getClass()).getName() + ".");
    }
    
    protected boolean registerAbility(final Ability ability, final boolean b, final boolean b2) {
        if (this.registrationIsDone && !b2) {
            MMOItems.plugin.getLogger().log(Level.WARNING, "Failed attempt to register ability " + ability.getID() + ". Make sure abilities are registered when MI is loading.");
            return false;
        }
        if (!ability.isEnabled()) {
            MMOItems.plugin.getLogger().log(Level.WARNING, "Cannot register disabled ability " + ability.getID() + ".");
            return false;
        }
        if (this.hasAbility(ability.getID())) {
            MMOItems.plugin.getLogger().log(Level.WARNING, "Ability " + ability.getID() + " is already registered!");
            return false;
        }
        if (ability instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener)ability, (Plugin)MMOItems.plugin);
        }
        if (b2) {
            this.tpAbilities.put(ability.getID(), ability);
        }
        else if (b) {
            this.mmAbilities.put(ability.getID(), ability);
        }
        else {
            this.miAbilities.put(ability.getID(), ability);
        }
        this.abilities.put(ability.getID(), ability);
        return true;
    }
    
    public void initialize() {
        try {
            final JarFile jarFile = new JarFile(MMOItems.plugin.getJarFile());
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final String replace = entries.nextElement().getName().replace("/", ".");
                if (!replace.contains("$") && replace.endsWith(".class") && replace.startsWith("net.Indyuce.mmoitems.ability.")) {
                    final Ability ability = (Ability)Class.forName(replace.substring(0, replace.length() - 6)).newInstance();
                    if (!ability.isEnabled()) {
                        continue;
                    }
                    this.registerAbility(ability, false, false);
                }
            }
            jarFile.close();
        }
        catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException ex2) {
            final Throwable t;
            t.printStackTrace();
        }
        final File file = new File(MMOItems.plugin.getDataFolder() + "/dynamic/mythic-mobs-abilities");
        if (!file.exists() && !file.mkdirs()) {
            MMOItems.plugin.getLogger().warning("Failed DIR generation!");
        }
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            int i = 0;
            for (final File file2 : file.listFiles()) {
                try {
                    this.registerAbility(new MythicMobsAbility(file2.getName().substring(0, file2.getName().length() - 4), (FileConfiguration)YamlConfiguration.loadConfiguration(file2)), true, false);
                    ++i;
                }
                catch (IllegalArgumentException ex) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load ability from " + file2.getName() + ": " + ex.getMessage());
                }
            }
            if (i > 0) {
                MMOItems.plugin.getLogger().log(Level.INFO, "Loaded " + i + " extra MythicMobs abilities");
            }
        }
        this.registrationIsDone = true;
    }
}
