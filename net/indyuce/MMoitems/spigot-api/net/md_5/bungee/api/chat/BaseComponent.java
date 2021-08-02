// 
// Decompiled by Procyon v0.5.36
// 

package net.md_5.bungee.api.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.md_5.bungee.api.ChatColor;

public abstract class BaseComponent
{
    BaseComponent parent;
    private ChatColor color;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;
    private String insertion;
    private List<BaseComponent> extra;
    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;
    
    BaseComponent(final BaseComponent old) {
        this.setColor(old.getColorRaw());
        this.setBold(old.isBoldRaw());
        this.setItalic(old.isItalicRaw());
        this.setUnderlined(old.isUnderlinedRaw());
        this.setStrikethrough(old.isStrikethroughRaw());
        this.setObfuscated(old.isObfuscatedRaw());
        this.setInsertion(old.getInsertion());
        this.setClickEvent(old.getClickEvent());
        this.setHoverEvent(old.getHoverEvent());
        if (old.getExtra() != null) {
            for (final BaseComponent component : old.getExtra()) {
                this.addExtra(component.duplicate());
            }
        }
    }
    
    public abstract BaseComponent duplicate();
    
    public static String toLegacyText(final BaseComponent... components) {
        final StringBuilder builder = new StringBuilder();
        for (final BaseComponent msg : components) {
            builder.append(msg.toLegacyText());
        }
        return builder.toString();
    }
    
    public static String toPlainText(final BaseComponent... components) {
        final StringBuilder builder = new StringBuilder();
        for (final BaseComponent msg : components) {
            builder.append(msg.toPlainText());
        }
        return builder.toString();
    }
    
    public ChatColor getColor() {
        if (this.color != null) {
            return this.color;
        }
        if (this.parent == null) {
            return ChatColor.WHITE;
        }
        return this.parent.getColor();
    }
    
    public ChatColor getColorRaw() {
        return this.color;
    }
    
    public boolean isBold() {
        if (this.bold == null) {
            return this.parent != null && this.parent.isBold();
        }
        return this.bold;
    }
    
    public Boolean isBoldRaw() {
        return this.bold;
    }
    
    public boolean isItalic() {
        if (this.italic == null) {
            return this.parent != null && this.parent.isItalic();
        }
        return this.italic;
    }
    
    public Boolean isItalicRaw() {
        return this.italic;
    }
    
    public boolean isUnderlined() {
        if (this.underlined == null) {
            return this.parent != null && this.parent.isUnderlined();
        }
        return this.underlined;
    }
    
    public Boolean isUnderlinedRaw() {
        return this.underlined;
    }
    
    public boolean isStrikethrough() {
        if (this.strikethrough == null) {
            return this.parent != null && this.parent.isStrikethrough();
        }
        return this.strikethrough;
    }
    
    public Boolean isStrikethroughRaw() {
        return this.strikethrough;
    }
    
    public boolean isObfuscated() {
        if (this.obfuscated == null) {
            return this.parent != null && this.parent.isObfuscated();
        }
        return this.obfuscated;
    }
    
    public Boolean isObfuscatedRaw() {
        return this.obfuscated;
    }
    
    public void setExtra(final List<BaseComponent> components) {
        for (final BaseComponent component : components) {
            component.parent = this;
        }
        this.extra = components;
    }
    
    public void addExtra(final String text) {
        this.addExtra(new TextComponent(text));
    }
    
    public void addExtra(final BaseComponent component) {
        if (this.extra == null) {
            this.extra = new ArrayList<BaseComponent>();
        }
        component.parent = this;
        this.extra.add(component);
    }
    
    public boolean hasFormatting() {
        return this.color != null || this.bold != null || this.italic != null || this.underlined != null || this.strikethrough != null || this.obfuscated != null || this.hoverEvent != null || this.clickEvent != null;
    }
    
    public String toPlainText() {
        final StringBuilder builder = new StringBuilder();
        this.toPlainText(builder);
        return builder.toString();
    }
    
    void toPlainText(final StringBuilder builder) {
        if (this.extra != null) {
            for (final BaseComponent e : this.extra) {
                e.toPlainText(builder);
            }
        }
    }
    
    public String toLegacyText() {
        final StringBuilder builder = new StringBuilder();
        this.toLegacyText(builder);
        return builder.toString();
    }
    
    void toLegacyText(final StringBuilder builder) {
        if (this.extra != null) {
            for (final BaseComponent e : this.extra) {
                e.toLegacyText(builder);
            }
        }
    }
    
    public void setColor(final ChatColor color) {
        this.color = color;
    }
    
    public void setBold(final Boolean bold) {
        this.bold = bold;
    }
    
    public void setItalic(final Boolean italic) {
        this.italic = italic;
    }
    
    public void setUnderlined(final Boolean underlined) {
        this.underlined = underlined;
    }
    
    public void setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }
    
    public void setObfuscated(final Boolean obfuscated) {
        this.obfuscated = obfuscated;
    }
    
    public void setInsertion(final String insertion) {
        this.insertion = insertion;
    }
    
    public void setClickEvent(final ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }
    
    public void setHoverEvent(final HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
    }
    
    @Override
    public String toString() {
        return "BaseComponent(color=" + this.getColor() + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", insertion=" + this.getInsertion() + ", extra=" + this.getExtra() + ", clickEvent=" + this.getClickEvent() + ", hoverEvent=" + this.getHoverEvent() + ")";
    }
    
    public BaseComponent() {
    }
    
    public String getInsertion() {
        return this.insertion;
    }
    
    public List<BaseComponent> getExtra() {
        return this.extra;
    }
    
    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }
    
    public HoverEvent getHoverEvent() {
        return this.hoverEvent;
    }
}
