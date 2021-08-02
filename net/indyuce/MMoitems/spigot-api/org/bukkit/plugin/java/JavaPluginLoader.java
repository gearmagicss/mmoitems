// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin.java;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.event.EventException;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.AuthorNagException;
import java.util.Arrays;
import org.bukkit.Warning;
import org.bukkit.event.EventHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.HashMap;
import org.bukkit.plugin.RegisteredListener;
import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.jar.JarEntry;
import java.io.InputStream;
import org.yaml.snakeyaml.error.YAMLException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.jar.JarFile;
import java.util.Iterator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.UnknownDependencyException;
import java.util.logging.Level;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import java.io.FileNotFoundException;
import org.bukkit.plugin.Plugin;
import java.io.File;
import org.apache.commons.lang.Validate;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.spigotmc.CustomTimingsHandler;
import java.util.Map;
import java.util.regex.Pattern;
import org.bukkit.Server;
import org.bukkit.plugin.PluginLoader;

public final class JavaPluginLoader implements PluginLoader
{
    final Server server;
    private final Pattern[] fileFilters;
    private final Map<String, Class<?>> classes;
    private final Map<String, PluginClassLoader> loaders;
    public static final CustomTimingsHandler pluginParentTimer;
    
    static {
        pluginParentTimer = new CustomTimingsHandler("** Plugins");
    }
    
    @Deprecated
    public JavaPluginLoader(final Server instance) {
        this.fileFilters = new Pattern[] { Pattern.compile("\\.jar$") };
        this.classes = new ConcurrentHashMap<String, Class<?>>();
        this.loaders = new LinkedHashMap<String, PluginClassLoader>();
        Validate.notNull(instance, "Server cannot be null");
        this.server = instance;
    }
    
    @Override
    public Plugin loadPlugin(final File file) throws InvalidPluginException {
        Validate.notNull(file, "File cannot be null");
        if (!file.exists()) {
            throw new InvalidPluginException(new FileNotFoundException(String.valueOf(file.getPath()) + " does not exist"));
        }
        PluginDescriptionFile description;
        try {
            description = this.getPluginDescription(file);
        }
        catch (InvalidDescriptionException ex) {
            throw new InvalidPluginException(ex);
        }
        final File parentFile = file.getParentFile();
        final File dataFolder = new File(parentFile, description.getName());
        final File oldDataFolder = new File(parentFile, description.getRawName());
        if (!dataFolder.equals(oldDataFolder)) {
            if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
                this.server.getLogger().warning(String.format("While loading %s (%s) found old-data folder: `%s' next to the new one `%s'", description.getFullName(), file, oldDataFolder, dataFolder));
            }
            else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
                if (!oldDataFolder.renameTo(dataFolder)) {
                    throw new InvalidPluginException("Unable to rename old data folder: `" + oldDataFolder + "' to: `" + dataFolder + "'");
                }
                this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) renamed data folder: `%s' to `%s'", description.getFullName(), file, oldDataFolder, dataFolder));
            }
        }
        if (dataFolder.exists() && !dataFolder.isDirectory()) {
            throw new InvalidPluginException(String.format("Projected datafolder: `%s' for %s (%s) exists and is not a directory", dataFolder, description.getFullName(), file));
        }
        for (final String pluginName : description.getDepend()) {
            if (this.loaders == null) {
                throw new UnknownDependencyException(pluginName);
            }
            final PluginClassLoader current = this.loaders.get(pluginName);
            if (current == null) {
                throw new UnknownDependencyException(pluginName);
            }
        }
        PluginClassLoader loader;
        try {
            loader = new PluginClassLoader(this, this.getClass().getClassLoader(), description, dataFolder, file);
        }
        catch (InvalidPluginException ex2) {
            throw ex2;
        }
        catch (Throwable ex3) {
            throw new InvalidPluginException(ex3);
        }
        this.loaders.put(description.getName(), loader);
        return loader.plugin;
    }
    
    @Override
    public PluginDescriptionFile getPluginDescription(final File file) throws InvalidDescriptionException {
        Validate.notNull(file, "File cannot be null");
        JarFile jar = null;
        InputStream stream = null;
        try {
            jar = new JarFile(file);
            final JarEntry entry = jar.getJarEntry("plugin.yml");
            if (entry == null) {
                throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain plugin.yml"));
            }
            stream = jar.getInputStream(entry);
            return new PluginDescriptionFile(stream);
        }
        catch (IOException ex) {
            throw new InvalidDescriptionException(ex);
        }
        catch (YAMLException ex2) {
            throw new InvalidDescriptionException(ex2);
        }
        finally {
            if (jar != null) {
                try {
                    jar.close();
                }
                catch (IOException ex3) {}
            }
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException ex4) {}
            }
        }
    }
    
    @Override
    public Pattern[] getPluginFileFilters() {
        return this.fileFilters.clone();
    }
    
    Class<?> getClassByName(final String name) {
        Class<?> cachedClass = this.classes.get(name);
        if (cachedClass != null) {
            return cachedClass;
        }
        for (final String current : this.loaders.keySet()) {
            final PluginClassLoader loader = this.loaders.get(current);
            try {
                cachedClass = loader.findClass(name, false);
            }
            catch (ClassNotFoundException ex) {}
            if (cachedClass != null) {
                return cachedClass;
            }
        }
        return null;
    }
    
    void setClass(final String name, final Class<?> clazz) {
        if (!this.classes.containsKey(name)) {
            this.classes.put(name, clazz);
            if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                final Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.registerClass(serializable);
            }
        }
    }
    
    private void removeClass(final String name) {
        final Class<?> clazz = this.classes.remove(name);
        try {
            if (clazz != null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                final Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.unregisterClass(serializable);
            }
        }
        catch (NullPointerException ex) {}
    }
    
    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(final Listener listener, final Plugin plugin) {
        Validate.notNull(plugin, "Plugin can not be null");
        Validate.notNull(listener, "Listener can not be null");
        this.server.getPluginManager().useTimings();
        final Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
        Set<Method> methods;
        try {
            final Method[] publicMethods = listener.getClass().getMethods();
            final Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<Method>(publicMethods.length + privateMethods.length, 1.0f);
            Method[] array;
            for (int length = (array = publicMethods).length, i = 0; i < length; ++i) {
                final Method method = array[i];
                methods.add(method);
            }
            Method[] array2;
            for (int length2 = (array2 = privateMethods).length, j = 0; j < length2; ++j) {
                final Method method = array2[j];
                methods.add(method);
            }
        }
        catch (NoClassDefFoundError e) {
            plugin.getLogger().severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
            return ret;
        }
        for (final Method method2 : methods) {
            final EventHandler eh = method2.getAnnotation(EventHandler.class);
            if (eh == null) {
                continue;
            }
            if (method2.isBridge()) {
                continue;
            }
            if (method2.isSynthetic()) {
                continue;
            }
            final Class<?> checkClass;
            if (method2.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method2.getParameterTypes()[0])) {
                plugin.getLogger().severe(String.valueOf(plugin.getDescription().getFullName()) + " attempted to register an invalid EventHandler method signature \"" + method2.toGenericString() + "\" in " + listener.getClass());
            }
            else {
                final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
                method2.setAccessible(true);
                Set<RegisteredListener> eventSet = ret.get(eventClass);
                if (eventSet == null) {
                    eventSet = new HashSet<RegisteredListener>();
                    ret.put(eventClass, eventSet);
                }
                Class<?> clazz = eventClass;
                while (Event.class.isAssignableFrom(clazz)) {
                    if (clazz.getAnnotation(Deprecated.class) != null) {
                        final Warning warning = clazz.getAnnotation(Warning.class);
                        final Warning.WarningState warningState = this.server.getWarningState();
                        if (!warningState.printFor(warning)) {
                            break;
                        }
                        plugin.getLogger().log(Level.WARNING, String.format("\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.", plugin.getDescription().getFullName(), clazz.getName(), method2.toGenericString(), (warning != null && warning.reason().length() != 0) ? warning.reason() : "Server performance will be affected", Arrays.toString(plugin.getDescription().getAuthors().toArray())), (warningState == Warning.WarningState.ON) ? new AuthorNagException((String)null) : null);
                        break;
                    }
                    else {
                        clazz = clazz.getSuperclass();
                    }
                }
                final CustomTimingsHandler timings = new CustomTimingsHandler("Plugin: " + plugin.getDescription().getFullName() + " Event: " + listener.getClass().getName() + "::" + method2.getName() + "(" + eventClass.getSimpleName() + ")", JavaPluginLoader.pluginParentTimer);
                final EventExecutor executor = new EventExecutor() {
                    @Override
                    public void execute(final Listener listener, final Event event) throws EventException {
                        try {
                            if (!eventClass.isAssignableFrom(event.getClass())) {
                                return;
                            }
                            final boolean isAsync = event.isAsynchronous();
                            if (!isAsync) {
                                timings.startTiming();
                            }
                            method2.invoke(listener, event);
                            if (!isAsync) {
                                timings.stopTiming();
                            }
                        }
                        catch (InvocationTargetException ex) {
                            throw new EventException(ex.getCause());
                        }
                        catch (Throwable t) {
                            throw new EventException(t);
                        }
                    }
                };
                eventSet.add(new RegisteredListener(listener, executor, eh.priority(), plugin, eh.ignoreCancelled()));
            }
        }
        return ret;
    }
    
    @Override
    public void enablePlugin(final Plugin plugin) {
        Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
        if (!plugin.isEnabled()) {
            plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
            final JavaPlugin jPlugin = (JavaPlugin)plugin;
            final String pluginName = jPlugin.getDescription().getName();
            if (!this.loaders.containsKey(pluginName)) {
                this.loaders.put(pluginName, (PluginClassLoader)jPlugin.getClassLoader());
            }
            try {
                jPlugin.setEnabled(true);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            this.server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
        }
    }
    
    @Override
    public void disablePlugin(final Plugin plugin) {
        Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
        if (plugin.isEnabled()) {
            final String message = String.format("Disabling %s", plugin.getDescription().getFullName());
            plugin.getLogger().info(message);
            this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));
            final JavaPlugin jPlugin = (JavaPlugin)plugin;
            final ClassLoader cloader = jPlugin.getClassLoader();
            try {
                jPlugin.setEnabled(false);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            this.loaders.remove(jPlugin.getDescription().getName());
            if (cloader instanceof PluginClassLoader) {
                final PluginClassLoader loader = (PluginClassLoader)cloader;
                final Set<String> names = loader.getClasses();
                for (final String name : names) {
                    this.removeClass(name);
                }
            }
        }
    }
}
