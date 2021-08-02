// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import com.google.common.collect.ImmutableMap;
import java.io.Writer;
import java.util.Collection;
import java.io.Reader;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableList;
import java.io.InputStream;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import java.util.Set;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.permissions.Permission;
import java.util.Map;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

public final class PluginDescriptionFile
{
    private static final ThreadLocal<Yaml> YAML;
    String rawName;
    private String name;
    private String main;
    private String classLoaderOf;
    private List<String> depend;
    private List<String> softDepend;
    private List<String> loadBefore;
    private String version;
    private Map<String, Map<String, Object>> commands;
    private String description;
    private List<String> authors;
    private String website;
    private String prefix;
    private boolean database;
    private PluginLoadOrder order;
    private List<Permission> permissions;
    private Map<?, ?> lazyPermissions;
    private PermissionDefault defaultPerm;
    private Set<PluginAwareness> awareness;
    
    static {
        YAML = new ThreadLocal<Yaml>() {
            @Override
            protected Yaml initialValue() {
                return new Yaml(new SafeConstructor() {
                    {
                        this.yamlConstructors.put(null, new AbstractConstruct() {
                            @Override
                            public Object construct(final Node node) {
                                if (!node.getTag().startsWith("!@")) {
                                    return SafeConstructor.undefinedConstructor.construct(node);
                                }
                                return new PluginAwareness() {
                                    @Override
                                    public String toString() {
                                        return node.toString();
                                    }
                                };
                            }
                        });
                        PluginAwareness.Flags[] values;
                        for (int length = (values = PluginAwareness.Flags.values()).length, i = 0; i < length; ++i) {
                            final PluginAwareness.Flags flag = values[i];
                            this.yamlConstructors.put(new Tag("!@" + flag.name()), new AbstractConstruct() {
                                @Override
                                public PluginAwareness.Flags construct(final Node node) {
                                    return flag;
                                }
                            });
                        }
                    }
                });
            }
        };
    }
    
    public PluginDescriptionFile(final InputStream stream) throws InvalidDescriptionException {
        this.rawName = null;
        this.name = null;
        this.main = null;
        this.classLoaderOf = null;
        this.depend = (List<String>)ImmutableList.of();
        this.softDepend = (List<String>)ImmutableList.of();
        this.loadBefore = (List<String>)ImmutableList.of();
        this.version = null;
        this.commands = null;
        this.description = null;
        this.authors = null;
        this.website = null;
        this.prefix = null;
        this.database = false;
        this.order = PluginLoadOrder.POSTWORLD;
        this.permissions = null;
        this.lazyPermissions = null;
        this.defaultPerm = PermissionDefault.OP;
        this.awareness = (Set<PluginAwareness>)ImmutableSet.of();
        this.loadMap(this.asMap(PluginDescriptionFile.YAML.get().load(stream)));
    }
    
    public PluginDescriptionFile(final Reader reader) throws InvalidDescriptionException {
        this.rawName = null;
        this.name = null;
        this.main = null;
        this.classLoaderOf = null;
        this.depend = (List<String>)ImmutableList.of();
        this.softDepend = (List<String>)ImmutableList.of();
        this.loadBefore = (List<String>)ImmutableList.of();
        this.version = null;
        this.commands = null;
        this.description = null;
        this.authors = null;
        this.website = null;
        this.prefix = null;
        this.database = false;
        this.order = PluginLoadOrder.POSTWORLD;
        this.permissions = null;
        this.lazyPermissions = null;
        this.defaultPerm = PermissionDefault.OP;
        this.awareness = (Set<PluginAwareness>)ImmutableSet.of();
        this.loadMap(this.asMap(PluginDescriptionFile.YAML.get().load(reader)));
    }
    
    public PluginDescriptionFile(final String pluginName, final String pluginVersion, final String mainClass) {
        this.rawName = null;
        this.name = null;
        this.main = null;
        this.classLoaderOf = null;
        this.depend = (List<String>)ImmutableList.of();
        this.softDepend = (List<String>)ImmutableList.of();
        this.loadBefore = (List<String>)ImmutableList.of();
        this.version = null;
        this.commands = null;
        this.description = null;
        this.authors = null;
        this.website = null;
        this.prefix = null;
        this.database = false;
        this.order = PluginLoadOrder.POSTWORLD;
        this.permissions = null;
        this.lazyPermissions = null;
        this.defaultPerm = PermissionDefault.OP;
        this.awareness = (Set<PluginAwareness>)ImmutableSet.of();
        this.name = pluginName.replace(' ', '_');
        this.version = pluginVersion;
        this.main = mainClass;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getMain() {
        return this.main;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public PluginLoadOrder getLoad() {
        return this.order;
    }
    
    public List<String> getAuthors() {
        return this.authors;
    }
    
    public String getWebsite() {
        return this.website;
    }
    
    public boolean isDatabaseEnabled() {
        return this.database;
    }
    
    public List<String> getDepend() {
        return this.depend;
    }
    
    public List<String> getSoftDepend() {
        return this.softDepend;
    }
    
    public List<String> getLoadBefore() {
        return this.loadBefore;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public Map<String, Map<String, Object>> getCommands() {
        return this.commands;
    }
    
    public List<Permission> getPermissions() {
        if (this.permissions == null) {
            if (this.lazyPermissions == null) {
                this.permissions = (List<Permission>)ImmutableList.of();
            }
            else {
                this.permissions = (List<Permission>)ImmutableList.copyOf((Collection<?>)Permission.loadPermissions(this.lazyPermissions, "Permission node '%s' in plugin description file for " + this.getFullName() + " is invalid", this.defaultPerm));
                this.lazyPermissions = null;
            }
        }
        return this.permissions;
    }
    
    public PermissionDefault getPermissionDefault() {
        return this.defaultPerm;
    }
    
    public Set<PluginAwareness> getAwareness() {
        return this.awareness;
    }
    
    public String getFullName() {
        return String.valueOf(this.name) + " v" + this.version;
    }
    
    @Deprecated
    public String getClassLoaderOf() {
        return this.classLoaderOf;
    }
    
    public void setDatabaseEnabled(final boolean database) {
        this.database = database;
    }
    
    public void save(final Writer writer) {
        PluginDescriptionFile.YAML.get().dump(this.saveMap(), writer);
    }
    
    private void loadMap(final Map<?, ?> map) throws InvalidDescriptionException {
        try {
            final String string = map.get("name").toString();
            this.rawName = string;
            this.name = string;
            if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
                throw new InvalidDescriptionException("name '" + this.name + "' contains invalid characters.");
            }
            this.name = this.name.replace(' ', '_');
        }
        catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "name is not defined");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "name is of wrong type");
        }
        try {
            this.version = map.get("version").toString();
        }
        catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "version is not defined");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "version is of wrong type");
        }
        try {
            this.main = map.get("main").toString();
            if (this.main.startsWith("org.bukkit.")) {
                throw new InvalidDescriptionException("main may not be within the org.bukkit namespace");
            }
        }
        catch (NullPointerException ex) {
            throw new InvalidDescriptionException(ex, "main is not defined");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "main is of wrong type");
        }
        if (map.get("commands") != null) {
            final ImmutableMap.Builder<String, Map<String, Object>> commandsBuilder = ImmutableMap.builder();
            try {
                for (final Map.Entry<?, ?> command : ((Map)map.get("commands")).entrySet()) {
                    final ImmutableMap.Builder<String, Object> commandBuilder = ImmutableMap.builder();
                    if (command.getValue() != null) {
                        for (final Map.Entry<?, ?> commandEntry : ((Map)command.getValue()).entrySet()) {
                            if (commandEntry.getValue() instanceof Iterable) {
                                final ImmutableList.Builder<Object> commandSubList = ImmutableList.builder();
                                for (final Object commandSubListItem : (Iterable)commandEntry.getValue()) {
                                    if (commandSubListItem != null) {
                                        commandSubList.add(commandSubListItem);
                                    }
                                }
                                commandBuilder.put(commandEntry.getKey().toString(), commandSubList.build());
                            }
                            else {
                                if (commandEntry.getValue() == null) {
                                    continue;
                                }
                                commandBuilder.put(commandEntry.getKey().toString(), commandEntry.getValue());
                            }
                        }
                    }
                    commandsBuilder.put(command.getKey().toString(), commandBuilder.build());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "commands are of wrong type");
            }
            this.commands = commandsBuilder.build();
        }
        if (map.get("class-loader-of") != null) {
            this.classLoaderOf = map.get("class-loader-of").toString();
        }
        this.depend = makePluginNameList(map, "depend");
        this.softDepend = makePluginNameList(map, "softdepend");
        this.loadBefore = makePluginNameList(map, "loadbefore");
        if (map.get("database") != null) {
            try {
                this.database = (boolean)map.get("database");
            }
            catch (ClassCastException ex2) {
                throw new InvalidDescriptionException(ex2, "database is of wrong type");
            }
        }
        if (map.get("website") != null) {
            this.website = map.get("website").toString();
        }
        if (map.get("description") != null) {
            this.description = map.get("description").toString();
        }
        if (map.get("load") != null) {
            try {
                this.order = PluginLoadOrder.valueOf(((String)map.get("load")).toUpperCase().replaceAll("\\W", ""));
            }
            catch (ClassCastException ex2) {
                throw new InvalidDescriptionException(ex2, "load is of wrong type");
            }
            catch (IllegalArgumentException ex4) {
                throw new InvalidDescriptionException(ex4, "load is not a valid choice");
            }
        }
        if (map.get("authors") != null) {
            final ImmutableList.Builder<String> authorsBuilder = ImmutableList.builder();
            if (map.get("author") != null) {
                authorsBuilder.add(map.get("author").toString());
            }
            try {
                for (final Object o : (Iterable)map.get("authors")) {
                    authorsBuilder.add(o.toString());
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "authors are of wrong type");
            }
            catch (NullPointerException ex5) {
                throw new InvalidDescriptionException(ex5, "authors are improperly defined");
            }
            this.authors = authorsBuilder.build();
        }
        else if (map.get("author") != null) {
            this.authors = ImmutableList.of(map.get("author").toString());
        }
        else {
            this.authors = (List<String>)ImmutableList.of();
        }
        if (map.get("default-permission") != null) {
            try {
                this.defaultPerm = PermissionDefault.getByName(map.get("default-permission").toString());
            }
            catch (ClassCastException ex2) {
                throw new InvalidDescriptionException(ex2, "default-permission is of wrong type");
            }
            catch (IllegalArgumentException ex4) {
                throw new InvalidDescriptionException(ex4, "default-permission is not a valid choice");
            }
        }
        if (map.get("awareness") instanceof Iterable) {
            final Set<PluginAwareness> awareness = new HashSet<PluginAwareness>();
            try {
                for (final Object o : (Iterable)map.get("awareness")) {
                    awareness.add((PluginAwareness)o);
                }
            }
            catch (ClassCastException ex3) {
                throw new InvalidDescriptionException(ex3, "awareness has wrong type");
            }
            this.awareness = (Set<PluginAwareness>)ImmutableSet.copyOf((Collection<?>)awareness);
        }
        try {
            this.lazyPermissions = (Map<?, ?>)map.get("permissions");
        }
        catch (ClassCastException ex2) {
            throw new InvalidDescriptionException(ex2, "permissions are of the wrong type");
        }
        if (map.get("prefix") != null) {
            this.prefix = map.get("prefix").toString();
        }
    }
    
    private static List<String> makePluginNameList(final Map<?, ?> map, final String key) throws InvalidDescriptionException {
        final Object value = map.get(key);
        if (value == null) {
            return (List<String>)ImmutableList.of();
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        try {
            for (final Object entry : (Iterable)value) {
                builder.add(entry.toString().replace(' ', '_'));
            }
        }
        catch (ClassCastException ex) {
            throw new InvalidDescriptionException(ex, String.valueOf(key) + " is of wrong type");
        }
        catch (NullPointerException ex2) {
            throw new InvalidDescriptionException(ex2, "invalid " + key + " format");
        }
        return builder.build();
    }
    
    private Map<String, Object> saveMap() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", this.name);
        map.put("main", this.main);
        map.put("version", this.version);
        map.put("database", this.database);
        map.put("order", this.order.toString());
        map.put("default-permission", this.defaultPerm.toString());
        if (this.commands != null) {
            map.put("command", this.commands);
        }
        if (this.depend != null) {
            map.put("depend", this.depend);
        }
        if (this.softDepend != null) {
            map.put("softdepend", this.softDepend);
        }
        if (this.website != null) {
            map.put("website", this.website);
        }
        if (this.description != null) {
            map.put("description", this.description);
        }
        if (this.authors.size() == 1) {
            map.put("author", this.authors.get(0));
        }
        else if (this.authors.size() > 1) {
            map.put("authors", this.authors);
        }
        if (this.classLoaderOf != null) {
            map.put("class-loader-of", this.classLoaderOf);
        }
        if (this.prefix != null) {
            map.put("prefix", this.prefix);
        }
        return map;
    }
    
    private Map<?, ?> asMap(final Object object) throws InvalidDescriptionException {
        if (object instanceof Map) {
            return (Map<?, ?>)object;
        }
        throw new InvalidDescriptionException(object + " is not properly structured.");
    }
    
    @Deprecated
    public String getRawName() {
        return this.rawName;
    }
}
