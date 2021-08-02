// 
// Decompiled by Procyon v0.5.36
// 

package net.md_5.bungee.api.chat;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.MissingResourceException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ResourceBundle;

public class TranslatableComponent extends BaseComponent
{
    private final ResourceBundle locales;
    private final Pattern format;
    private String translate;
    private List<BaseComponent> with;
    
    public TranslatableComponent(final TranslatableComponent original) {
        super(original);
        this.locales = ResourceBundle.getBundle("mojang-translations/en_US");
        this.format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
        this.setTranslate(original.getTranslate());
        if (original.getWith() != null) {
            final List<BaseComponent> temp = new ArrayList<BaseComponent>();
            for (final BaseComponent baseComponent : original.getWith()) {
                temp.add(baseComponent.duplicate());
            }
            this.setWith(temp);
        }
    }
    
    public TranslatableComponent(final String translate, final Object... with) {
        this.locales = ResourceBundle.getBundle("mojang-translations/en_US");
        this.format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
        this.setTranslate(translate);
        final List<BaseComponent> temp = new ArrayList<BaseComponent>();
        for (final Object w : with) {
            if (w instanceof String) {
                temp.add(new TextComponent((String)w));
            }
            else {
                temp.add((BaseComponent)w);
            }
        }
        this.setWith(temp);
    }
    
    @Override
    public BaseComponent duplicate() {
        return new TranslatableComponent(this);
    }
    
    public void setWith(final List<BaseComponent> components) {
        for (final BaseComponent component : components) {
            component.parent = this;
        }
        this.with = components;
    }
    
    public void addWith(final String text) {
        this.addWith(new TextComponent(text));
    }
    
    public void addWith(final BaseComponent component) {
        if (this.with == null) {
            this.with = new ArrayList<BaseComponent>();
        }
        component.parent = this;
        this.with.add(component);
    }
    
    protected void toPlainText(final StringBuilder builder) {
        String trans;
        try {
            trans = this.locales.getString(this.translate);
        }
        catch (MissingResourceException ex) {
            trans = this.translate;
        }
        final Matcher matcher = this.format.matcher(trans);
        int position = 0;
        int i = 0;
        while (matcher.find(position)) {
            final int pos = matcher.start();
            if (pos != position) {
                builder.append(trans.substring(position, pos));
            }
            position = matcher.end();
            final String formatCode = matcher.group(2);
            switch (formatCode.charAt(0)) {
                case 'd':
                case 's': {
                    final String withIndex = matcher.group(1);
                    this.with.get((withIndex != null) ? (Integer.parseInt(withIndex) - 1) : i++).toPlainText(builder);
                    continue;
                }
                case '%': {
                    builder.append('%');
                    continue;
                }
            }
        }
        if (trans.length() != position) {
            builder.append(trans.substring(position, trans.length()));
        }
        super.toPlainText(builder);
    }
    
    protected void toLegacyText(final StringBuilder builder) {
        String trans;
        try {
            trans = this.locales.getString(this.translate);
        }
        catch (MissingResourceException e) {
            trans = this.translate;
        }
        final Matcher matcher = this.format.matcher(trans);
        int position = 0;
        int i = 0;
        while (matcher.find(position)) {
            final int pos = matcher.start();
            if (pos != position) {
                this.addFormat(builder);
                builder.append(trans.substring(position, pos));
            }
            position = matcher.end();
            final String formatCode = matcher.group(2);
            switch (formatCode.charAt(0)) {
                case 'd':
                case 's': {
                    final String withIndex = matcher.group(1);
                    this.with.get((withIndex != null) ? (Integer.parseInt(withIndex) - 1) : i++).toLegacyText(builder);
                    continue;
                }
                case '%': {
                    this.addFormat(builder);
                    builder.append('%');
                    continue;
                }
            }
        }
        if (trans.length() != position) {
            this.addFormat(builder);
            builder.append(trans.substring(position, trans.length()));
        }
        super.toLegacyText(builder);
    }
    
    private void addFormat(final StringBuilder builder) {
        builder.append(this.getColor());
        if (this.isBold()) {
            builder.append(ChatColor.BOLD);
        }
        if (this.isItalic()) {
            builder.append(ChatColor.ITALIC);
        }
        if (this.isUnderlined()) {
            builder.append(ChatColor.UNDERLINE);
        }
        if (this.isStrikethrough()) {
            builder.append(ChatColor.STRIKETHROUGH);
        }
        if (this.isObfuscated()) {
            builder.append(ChatColor.MAGIC);
        }
    }
    
    public ResourceBundle getLocales() {
        return this.locales;
    }
    
    public Pattern getFormat() {
        return this.format;
    }
    
    public String getTranslate() {
        return this.translate;
    }
    
    public List<BaseComponent> getWith() {
        return this.with;
    }
    
    public void setTranslate(final String translate) {
        this.translate = translate;
    }
    
    @Override
    public String toString() {
        return "TranslatableComponent(locales=" + this.getLocales() + ", format=" + this.getFormat() + ", translate=" + this.getTranslate() + ", with=" + this.getWith() + ")";
    }
    
    public TranslatableComponent() {
        this.locales = ResourceBundle.getBundle("mojang-translations/en_US");
        this.format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    }
}
