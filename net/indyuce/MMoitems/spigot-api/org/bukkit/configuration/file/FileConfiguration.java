// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.configuration.file;

import org.bukkit.configuration.MemoryConfigurationOptions;
import java.io.BufferedReader;
import org.bukkit.configuration.InvalidConfigurationException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.google.common.base.Charsets;
import java.io.FileOutputStream;
import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import java.io.File;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;

public abstract class FileConfiguration extends MemoryConfiguration
{
    public FileConfiguration() {
    }
    
    public FileConfiguration(final Configuration defaults) {
        super(defaults);
    }
    
    public void save(final File file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        Files.createParentDirs(file);
        final String data = this.saveToString();
        final Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
        try {
            writer.write(data);
        }
        finally {
            writer.close();
        }
        writer.close();
    }
    
    public void save(final String file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        this.save(new File(file));
    }
    
    public abstract String saveToString();
    
    public void load(final File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        final FileInputStream stream = new FileInputStream(file);
        this.load(new InputStreamReader(stream, Charsets.UTF_8));
    }
    
    @Deprecated
    public void load(final InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull(stream, "Stream cannot be null");
        this.load(new InputStreamReader(stream, Charsets.UTF_8));
    }
    
    public void load(final Reader reader) throws IOException, InvalidConfigurationException {
        final BufferedReader input = (BufferedReader)((reader instanceof BufferedReader) ? reader : new BufferedReader(reader));
        final StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }
        finally {
            input.close();
        }
        input.close();
        this.loadFromString(builder.toString());
    }
    
    public void load(final String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        this.load(new File(file));
    }
    
    public abstract void loadFromString(final String p0) throws InvalidConfigurationException;
    
    protected abstract String buildHeader();
    
    @Override
    public FileConfigurationOptions options() {
        if (this.options == null) {
            this.options = new FileConfigurationOptions(this);
        }
        return (FileConfigurationOptions)this.options;
    }
}
