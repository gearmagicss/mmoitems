// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.build;

import io.lumine.mythic.lib.MythicLib;
import org.jetbrains.annotations.NotNull;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;

public class LoreBuilder
{
    private final List<String> lore;
    private final List<String> end;
    private final Map<String, String> placeholders;
    
    public LoreBuilder(final Collection<String> collection) {
        this.lore = new ArrayList<String>();
        this.end = new ArrayList<String>();
        this.placeholders = new HashMap<String, String>();
        this.lore.addAll(collection);
    }
    
    public void insert(final String str, final String... array) {
        final int index = this.lore.indexOf("#" + str + "#");
        if (index < 0) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            this.lore.add(index + 1, array[array.length - i - 1]);
        }
        this.lore.remove(index);
    }
    
    public void insert(final String str, final List<String> list) {
        final int index = this.lore.indexOf("#" + str + "#");
        if (index < 0) {
            return;
        }
        Lists.reverse((List)list).forEach(s -> this.lore.add(index + 1, s));
        this.lore.remove(index);
    }
    
    public void registerPlaceholder(final String s, final Object o) {
        this.placeholders.put(s, o.toString());
    }
    
    public String applySpecialPlaceholders(String replace) {
        while (replace.contains("{") && replace.substring(replace.indexOf("{")).contains("}")) {
            final String substring = replace.substring(replace.indexOf("{") + 1, replace.indexOf("}"));
            replace = replace.replace("{" + substring + "}", this.placeholders.getOrDefault(substring, "PHE"));
        }
        return replace;
    }
    
    public void end(@NotNull final String s) {
        this.end.add(s);
    }
    
    public List<String> build() {
        int i = 0;
        while (i < this.lore.size()) {
            final int n = this.lore.size() - i - 1;
            final String s = this.lore.get(n);
            if (s.startsWith("#")) {
                this.lore.remove(n);
            }
            else if (s.startsWith("{bar}") && (n == this.lore.size() - 1 || this.isBar(this.lore.get(n + 1)))) {
                this.lore.remove(n);
            }
            else {
                ++i;
            }
        }
        int j = 0;
        while (j < this.lore.size()) {
            final String colors = MythicLib.plugin.parseColors(this.lore.get(j).replace("{bar}", "").replace("{sbar}", ""));
            if (colors.contains("\\n")) {
                this.lore.remove(j);
                final String[] split = colors.split("\\\\n");
                for (int k = split.length - 1; k >= 0; --k) {
                    this.lore.add(j + 1, split[k]);
                }
                j += split.length;
            }
            else {
                this.lore.set(j++, colors);
            }
        }
        this.lore.addAll(this.end);
        return this.lore;
    }
    
    private boolean isBar(final String s) {
        return s.startsWith("{bar}") || s.startsWith("{sbar}");
    }
}
