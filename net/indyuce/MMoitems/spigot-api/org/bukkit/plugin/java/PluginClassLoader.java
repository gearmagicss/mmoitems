// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin.java;

import org.bukkit.plugin.PluginLoader;
import java.util.Set;
import java.net.MalformedURLException;
import org.bukkit.plugin.InvalidPluginException;
import org.apache.commons.lang.Validate;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URL;
import java.lang.reflect.Method;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import java.io.File;
import org.bukkit.plugin.PluginDescriptionFile;
import java.util.Map;
import java.net.URLClassLoader;

final class PluginClassLoader extends URLClassLoader
{
    private final JavaPluginLoader loader;
    private final Map<String, Class<?>> classes;
    private final PluginDescriptionFile description;
    private final File dataFolder;
    private final File file;
    final JavaPlugin plugin;
    private JavaPlugin pluginInit;
    private IllegalStateException pluginState;
    
    static {
        try {
            final Method method = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable", (Class<?>[])new Class[0]);
            if (method != null) {
                final boolean oldAccessible = method.isAccessible();
                method.setAccessible(true);
                method.invoke(null, new Object[0]);
                method.setAccessible(oldAccessible);
                Bukkit.getLogger().log(Level.INFO, "Set PluginClassLoader as parallel capable");
            }
        }
        catch (NoSuchMethodException ex2) {}
        catch (Exception ex) {
            Bukkit.getLogger().log(Level.WARNING, "Error setting PluginClassLoader as parallel capable", ex);
        }
    }
    
    PluginClassLoader(final JavaPluginLoader loader, final ClassLoader parent, final PluginDescriptionFile description, final File dataFolder, final File file) throws InvalidPluginException, MalformedURLException {
        super(new URL[] { file.toURI().toURL() }, parent);
        this.classes = new ConcurrentHashMap<String, Class<?>>();
        Validate.notNull(loader, "Loader cannot be null");
        this.loader = loader;
        this.description = description;
        this.dataFolder = dataFolder;
        this.file = file;
        try {
            Class<?> jarClass;
            try {
                jarClass = Class.forName(description.getMain(), true, this);
            }
            catch (ClassNotFoundException ex) {
                throw new InvalidPluginException("Cannot find main class `" + description.getMain() + "'", ex);
            }
            Class<? extends JavaPlugin> pluginClass;
            try {
                pluginClass = jarClass.asSubclass(JavaPlugin.class);
            }
            catch (ClassCastException ex2) {
                throw new InvalidPluginException("main class `" + description.getMain() + "' does not extend JavaPlugin", ex2);
            }
            this.plugin = (JavaPlugin)pluginClass.newInstance();
        }
        catch (IllegalAccessException ex3) {
            throw new InvalidPluginException("No public constructor", ex3);
        }
        catch (InstantiationException ex4) {
            throw new InvalidPluginException("Abnormal plugin type", ex4);
        }
    }
    
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        return this.findClass(name, true);
    }
    
    Class<?> findClass(final String name, final boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = this.classes.get(name);
        if (result == null) {
            if (checkGlobal) {
                result = this.loader.getClassByName(name);
            }
            if (result == null) {
                result = super.findClass(name);
                if (result != null) {
                    this.loader.setClass(name, result);
                }
            }
            this.classes.put(name, result);
        }
        return result;
    }
    
    Set<String> getClasses() {
        return this.classes.keySet();
    }
    
    synchronized void initialize(final JavaPlugin javaPlugin) {
        Validate.notNull(javaPlugin, "Initializing plugin cannot be null");
        Validate.isTrue(javaPlugin.getClass().getClassLoader() == this, "Cannot initialize plugin outside of this class loader");
        if (this.plugin != null || this.pluginInit != null) {
            throw new IllegalArgumentException("Plugin already initialized!", this.pluginState);
        }
        this.pluginState = new IllegalStateException("Initial initialization");
        (this.pluginInit = javaPlugin).init(this.loader, this.loader.server, this.description, this.dataFolder, this.file, this);
    }
}
