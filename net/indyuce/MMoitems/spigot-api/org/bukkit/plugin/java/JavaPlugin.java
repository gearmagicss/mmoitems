// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin.java;

import org.apache.commons.lang.Validate;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.google.common.base.Preconditions;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.Warning;
import java.net.URLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import java.io.Reader;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.configuration.file.FileConfiguration;
import com.avaje.ebean.EbeanServer;
import org.bukkit.plugin.PluginDescriptionFile;
import java.io.File;
import org.bukkit.Server;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginBase;

public abstract class JavaPlugin extends PluginBase
{
    private boolean isEnabled;
    private PluginLoader loader;
    private Server server;
    private File file;
    private PluginDescriptionFile description;
    private File dataFolder;
    private ClassLoader classLoader;
    private boolean naggable;
    private EbeanServer ebean;
    private FileConfiguration newConfig;
    private File configFile;
    private PluginLogger logger;
    
    public JavaPlugin() {
        this.isEnabled = false;
        this.loader = null;
        this.server = null;
        this.file = null;
        this.description = null;
        this.dataFolder = null;
        this.classLoader = null;
        this.naggable = true;
        this.ebean = null;
        this.newConfig = null;
        this.configFile = null;
        this.logger = null;
        final ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof PluginClassLoader)) {
            throw new IllegalStateException("JavaPlugin requires " + PluginClassLoader.class.getName());
        }
        ((PluginClassLoader)classLoader).initialize(this);
    }
    
    @Deprecated
    protected JavaPlugin(final PluginLoader loader, final Server server, final PluginDescriptionFile description, final File dataFolder, final File file) {
        this.isEnabled = false;
        this.loader = null;
        this.server = null;
        this.file = null;
        this.description = null;
        this.dataFolder = null;
        this.classLoader = null;
        this.naggable = true;
        this.ebean = null;
        this.newConfig = null;
        this.configFile = null;
        this.logger = null;
        final ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader instanceof PluginClassLoader) {
            throw new IllegalStateException("Cannot use initialization constructor at runtime");
        }
        this.init(loader, server, description, dataFolder, file, classLoader);
    }
    
    protected JavaPlugin(final JavaPluginLoader loader, final PluginDescriptionFile description, final File dataFolder, final File file) {
        this.isEnabled = false;
        this.loader = null;
        this.server = null;
        this.file = null;
        this.description = null;
        this.dataFolder = null;
        this.classLoader = null;
        this.naggable = true;
        this.ebean = null;
        this.newConfig = null;
        this.configFile = null;
        this.logger = null;
        final ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader instanceof PluginClassLoader) {
            throw new IllegalStateException("Cannot use initialization constructor at runtime");
        }
        this.init(loader, loader.server, description, dataFolder, file, classLoader);
    }
    
    @Override
    public final File getDataFolder() {
        return this.dataFolder;
    }
    
    @Override
    public final PluginLoader getPluginLoader() {
        return this.loader;
    }
    
    @Override
    public final Server getServer() {
        return this.server;
    }
    
    @Override
    public final boolean isEnabled() {
        return this.isEnabled;
    }
    
    protected File getFile() {
        return this.file;
    }
    
    @Override
    public final PluginDescriptionFile getDescription() {
        return this.description;
    }
    
    @Override
    public FileConfiguration getConfig() {
        if (this.newConfig == null) {
            this.reloadConfig();
        }
        return this.newConfig;
    }
    
    protected final Reader getTextResource(final String file) {
        final InputStream in = this.getResource(file);
        return (in == null) ? null : new InputStreamReader(in, Charsets.UTF_8);
    }
    
    @Override
    public void reloadConfig() {
        this.newConfig = YamlConfiguration.loadConfiguration(this.configFile);
        final InputStream defConfigStream = this.getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }
        this.newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }
    
    @Override
    public void saveConfig() {
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
        }
    }
    
    @Override
    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            this.saveResource("config.yml", false);
        }
    }
    
    @Override
    public void saveResource(String resourcePath, final boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
        resourcePath = resourcePath.replace('\\', '/');
        final InputStream in = this.getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
        }
        final File outFile = new File(this.dataFolder, resourcePath);
        final int lastIndex = resourcePath.lastIndexOf(47);
        final File outDir = new File(this.dataFolder, resourcePath.substring(0, (lastIndex >= 0) ? lastIndex : 0));
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        try {
            if (!outFile.exists() || replace) {
                final OutputStream out = new FileOutputStream(outFile);
                final byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
            else {
                this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        }
        catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }
    
    @Override
    public InputStream getResource(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }
        try {
            final URL url = this.getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            }
            final URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    protected final void setEnabled(final boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            if (this.isEnabled) {
                this.onEnable();
            }
            else {
                this.onDisable();
            }
        }
    }
    
    @Deprecated
    protected final void initialize(final PluginLoader loader, final Server server, final PluginDescriptionFile description, final File dataFolder, final File file, final ClassLoader classLoader) {
        if (server.getWarningState() == Warning.WarningState.OFF) {
            return;
        }
        this.getLogger().log(Level.WARNING, String.valueOf(this.getClass().getName()) + " is already initialized", (server.getWarningState() == Warning.WarningState.DEFAULT) ? null : new AuthorNagException("Explicit initialization"));
    }
    
    final void init(final PluginLoader loader, final Server server, final PluginDescriptionFile description, final File dataFolder, final File file, final ClassLoader classLoader) {
        this.loader = loader;
        this.server = server;
        this.file = file;
        this.description = description;
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
        this.configFile = new File(dataFolder, "config.yml");
        this.logger = new PluginLogger(this);
        if (description.isDatabaseEnabled()) {
            final ServerConfig db = new ServerConfig();
            db.setDefaultServer(false);
            db.setRegister(false);
            db.setClasses(this.getDatabaseClasses());
            db.setName(description.getName());
            server.configureDbConfig(db);
            final DataSourceConfig ds = db.getDataSourceConfig();
            ds.setUrl(this.replaceDatabaseString(ds.getUrl()));
            dataFolder.mkdirs();
            final ClassLoader previous = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(classLoader);
            this.ebean = EbeanServerFactory.create(db);
            Thread.currentThread().setContextClassLoader(previous);
        }
    }
    
    public List<Class<?>> getDatabaseClasses() {
        return new ArrayList<Class<?>>();
    }
    
    private String replaceDatabaseString(String input) {
        input = input.replaceAll("\\{DIR\\}", String.valueOf(this.dataFolder.getPath().replaceAll("\\\\", "/")) + "/");
        input = input.replaceAll("\\{NAME\\}", this.description.getName().replaceAll("[^\\w_-]", ""));
        return input;
    }
    
    @Deprecated
    public final boolean isInitialized() {
        return true;
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return false;
    }
    
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return null;
    }
    
    public PluginCommand getCommand(final String name) {
        final String alias = name.toLowerCase();
        PluginCommand command = this.getServer().getPluginCommand(alias);
        if (command == null || command.getPlugin() != this) {
            command = this.getServer().getPluginCommand(String.valueOf(this.description.getName().toLowerCase()) + ":" + alias);
        }
        if (command != null && command.getPlugin() == this) {
            return command;
        }
        return null;
    }
    
    @Override
    public void onLoad() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String id) {
        return null;
    }
    
    @Override
    public final boolean isNaggable() {
        return this.naggable;
    }
    
    @Override
    public final void setNaggable(final boolean canNag) {
        this.naggable = canNag;
    }
    
    @Override
    public EbeanServer getDatabase() {
        Preconditions.checkState(this.description.isDatabaseEnabled(), (Object)"Plugin does not have database: true in plugin.yml");
        return this.ebean;
    }
    
    protected void installDDL() {
        final SpiEbeanServer serv = (SpiEbeanServer)this.getDatabase();
        final DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(false, gen.generateCreateDdl());
    }
    
    protected void removeDDL() {
        final SpiEbeanServer serv = (SpiEbeanServer)this.getDatabase();
        final DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(true, gen.generateDropDdl());
    }
    
    @Override
    public final Logger getLogger() {
        return this.logger;
    }
    
    @Override
    public String toString() {
        return this.description.getFullName();
    }
    
    public static <T extends JavaPlugin> T getPlugin(final Class<T> clazz) {
        Validate.notNull(clazz, "Null class cannot have a plugin");
        if (!JavaPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz + " does not extend " + JavaPlugin.class);
        }
        final ClassLoader cl = clazz.getClassLoader();
        if (!(cl instanceof PluginClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not initialized by " + PluginClassLoader.class);
        }
        final JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
        if (plugin == null) {
            throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
        }
        return clazz.cast(plugin);
    }
    
    public static JavaPlugin getProvidingPlugin(final Class<?> clazz) {
        Validate.notNull(clazz, "Null class cannot have a plugin");
        final ClassLoader cl = clazz.getClassLoader();
        if (!(cl instanceof PluginClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not provided by " + PluginClassLoader.class);
        }
        final JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
        if (plugin == null) {
            throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
        }
        return plugin;
    }
}
