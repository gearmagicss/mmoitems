// 
// Decompiled by Procyon v0.5.36
// 

package net.md_5.bungee.api.chat;

import net.md_5.bungee.api.ChatColor;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ComponentBuilder
{
    private TextComponent current;
    private final List<BaseComponent> parts;
    
    public ComponentBuilder(final ComponentBuilder original) {
        this.parts = new ArrayList<BaseComponent>();
        this.current = new TextComponent(original.current);
        for (final BaseComponent baseComponent : original.parts) {
            this.parts.add(baseComponent.duplicate());
        }
    }
    
    public ComponentBuilder(final String text) {
        this.parts = new ArrayList<BaseComponent>();
        this.current = new TextComponent(text);
    }
    
    public ComponentBuilder append(final String text) {
        return this.append(text, FormatRetention.ALL);
    }
    
    public ComponentBuilder append(final String text, final FormatRetention retention) {
        this.parts.add(this.current);
        (this.current = new TextComponent(this.current)).setText(text);
        this.retain(retention);
        return this;
    }
    
    public ComponentBuilder color(final ChatColor color) {
        this.current.setColor(color);
        return this;
    }
    
    public ComponentBuilder bold(final boolean bold) {
        this.current.setBold(bold);
        return this;
    }
    
    public ComponentBuilder italic(final boolean italic) {
        this.current.setItalic(italic);
        return this;
    }
    
    public ComponentBuilder underlined(final boolean underlined) {
        this.current.setUnderlined(underlined);
        return this;
    }
    
    public ComponentBuilder strikethrough(final boolean strikethrough) {
        this.current.setStrikethrough(strikethrough);
        return this;
    }
    
    public ComponentBuilder obfuscated(final boolean obfuscated) {
        this.current.setObfuscated(obfuscated);
        return this;
    }
    
    public ComponentBuilder insertion(final String insertion) {
        this.current.setInsertion(insertion);
        return this;
    }
    
    public ComponentBuilder event(final ClickEvent clickEvent) {
        this.current.setClickEvent(clickEvent);
        return this;
    }
    
    public ComponentBuilder event(final HoverEvent hoverEvent) {
        this.current.setHoverEvent(hoverEvent);
        return this;
    }
    
    public ComponentBuilder reset() {
        return this.retain(FormatRetention.NONE);
    }
    
    public ComponentBuilder retain(final FormatRetention retention) {
        final BaseComponent previous = this.current;
        switch (retention) {
            case NONE: {
                this.current = new TextComponent(this.current.getText());
            }
            case EVENTS: {
                (this.current = new TextComponent(this.current.getText())).setInsertion(previous.getInsertion());
                this.current.setClickEvent(previous.getClickEvent());
                this.current.setHoverEvent(previous.getHoverEvent());
                break;
            }
            case FORMATTING: {
                this.current.setClickEvent(null);
                this.current.setHoverEvent(null);
                break;
            }
        }
        return this;
    }
    
    public BaseComponent[] create() {
        this.parts.add(this.current);
        return this.parts.toArray(new BaseComponent[this.parts.size()]);
    }
    
    public enum FormatRetention
    {
        NONE, 
        FORMATTING, 
        EVENTS, 
        ALL;
    }
}
