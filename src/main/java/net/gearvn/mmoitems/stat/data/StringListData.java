// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import com.google.gson.JsonElement;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.Collection;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import com.google.gson.JsonArray;
import java.util.Arrays;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class StringListData implements StatData, RandomStatData, Mergeable
{
    @NotNull
    private final List<String> list;
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof StringListData && ((StringListData)o).getList().size() == this.getList().size() && SilentNumbers.hasAll((List)((StringListData)o).getList(), (List)this.getList());
    }
    
    public StringListData() {
        this(new ArrayList<String>());
    }
    
    public StringListData(@NotNull final String[] a) {
        this(Arrays.asList(a));
    }
    
    public StringListData(@NotNull final JsonArray jsonArray) {
        this();
        jsonArray.forEach(jsonElement -> this.list.add(jsonElement.getAsString()));
    }
    
    public StringListData(@NotNull final List<String> list) {
        this.list = list;
    }
    
    @NotNull
    public List<String> getList() {
        return this.list;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new StringListData(new ArrayList<String>(this.list));
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof StringListData, "Cannot merge two different stat data types");
        this.list.addAll(((StringListData)statData).list);
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new StringListData(new ArrayList<String>(this.getList()));
    }
    
    @Override
    public boolean isClear() {
        return this.getList().size() == 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("§7");
        for (final String str : this.getList()) {
            if (sb.length() > 0) {
                sb.append("§8;§7 ");
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
