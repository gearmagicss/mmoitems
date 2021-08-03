// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.List;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.StringData;

public class NameData extends StringData implements Mergeable
{
    @NotNull
    ArrayList<String> prefixes;
    @NotNull
    ArrayList<String> suffixes;
    
    public NameData(@NotNull final String s) {
        super(s);
        this.prefixes = new ArrayList<String>();
        this.suffixes = new ArrayList<String>();
    }
    
    public void readPrefixes(@Nullable final ItemTag itemTag) {
        if (itemTag == null) {
            return;
        }
        for (final String s : ItemTag.getStringListFromTag(itemTag)) {
            if (s == null) {
                continue;
            }
            this.addPrefix(s);
        }
    }
    
    @NotNull
    public String getMainName() {
        return this.getString();
    }
    
    public boolean hasPrefixes() {
        return this.prefixes.size() > 0;
    }
    
    public boolean hasSuffixes() {
        return this.suffixes.size() > 0;
    }
    
    @NotNull
    public String bake() {
        final StringBuilder sb = new StringBuilder();
        for (final String str : this.getPrefixes()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str);
        }
        if (sb.length() > 0) {
            sb.append(" ");
        }
        sb.append(this.getMainName());
        for (final String str2 : this.getSuffixes()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str2);
        }
        return sb.toString();
    }
    
    @NotNull
    public String bakePrefix() {
        final StringBuilder sb = new StringBuilder();
        for (final String str : this.getPrefixes()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str);
        }
        return sb.toString();
    }
    
    @NotNull
    public String bakeSuffix() {
        final StringBuilder sb = new StringBuilder();
        for (final String str : this.getSuffixes()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return this.bake();
    }
    
    @NotNull
    public ItemTag compressPrefixes(@NotNull final String s) {
        return ItemTag.fromStringList(s, (List)this.getPrefixes());
    }
    
    @NotNull
    public ItemTag compressSuffixes(@NotNull final String s) {
        return ItemTag.fromStringList(s, (List)this.getSuffixes());
    }
    
    public void readSuffixes(@Nullable final ItemTag itemTag) {
        if (itemTag == null) {
            return;
        }
        for (final String s : ItemTag.getStringListFromTag(itemTag)) {
            if (s == null) {
                continue;
            }
            this.addSuffix(s);
        }
    }
    
    public void addPrefix(@NotNull final String e) {
        this.prefixes.add(e);
    }
    
    public void clearPrefixes() {
        this.prefixes.clear();
    }
    
    @NotNull
    public ArrayList<String> getPrefixes() {
        return this.prefixes;
    }
    
    public void addSuffix(@NotNull final String e) {
        this.suffixes.add(e);
    }
    
    public void clearSuffixes() {
        this.suffixes.clear();
    }
    
    @NotNull
    public ArrayList<String> getSuffixes() {
        return this.suffixes;
    }
    
    @Override
    public void merge(final StatData statData) {
        if (statData instanceof NameData) {
            if (!((NameData)statData).getMainName().isEmpty()) {
                this.setString(((NameData)statData).getMainName());
            }
            final Iterator<String> iterator = ((NameData)statData).getPrefixes().iterator();
            while (iterator.hasNext()) {
                this.addPrefix(iterator.next());
            }
            final Iterator<String> iterator2 = ((NameData)statData).getSuffixes().iterator();
            while (iterator2.hasNext()) {
                this.addSuffix(iterator2.next());
            }
        }
        else if (statData instanceof StringData) {
            if (statData.toString().isEmpty()) {
                return;
            }
            this.setString(statData.toString());
        }
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        final NameData nameData = new NameData(this.getMainName());
        final Iterator<String> iterator = this.getPrefixes().iterator();
        while (iterator.hasNext()) {
            nameData.addPrefix(iterator.next());
        }
        final Iterator<String> iterator2 = this.getSuffixes().iterator();
        while (iterator2.hasNext()) {
            nameData.addSuffix(iterator2.next());
        }
        return nameData;
    }
    
    @Override
    public boolean isClear() {
        return this.getMainName().isEmpty() && this.getPrefixes().size() == 0 && this.getSuffixes().size() == 0;
    }
}
