// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class StoredTagsData implements StatData, Mergeable
{
    private final List<ItemTag> tags;
    private static final List<String> ignoreList;
    
    @Deprecated
    public StoredTagsData(final ItemStack itemStack) {
        this(NBTItem.get(itemStack));
    }
    
    public StoredTagsData(final List<ItemTag> list) {
        (this.tags = new ArrayList<ItemTag>()).addAll(list);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof StoredTagsData)) {
            return false;
        }
        if (((StoredTagsData)o).getTags().size() != this.getTags().size()) {
            return false;
        }
        for (final ItemTag itemTag : ((StoredTagsData)o).getTags()) {
            if (itemTag == null) {
                continue;
            }
            boolean b = true;
            final Iterator<ItemTag> iterator2 = this.getTags().iterator();
            while (iterator2.hasNext()) {
                if (itemTag.equals((Object)iterator2.next())) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return false;
            }
        }
        return true;
    }
    
    public StoredTagsData(final NBTItem nbtItem) {
        this.tags = new ArrayList<ItemTag>();
        for (final String s : nbtItem.getTags()) {
            if (!StoredTagsData.ignoreList.contains(s) && !s.startsWith("MMOITEMS_")) {
                if (s.startsWith("HSTRY_")) {
                    continue;
                }
                final String tagType = this.getTagType(nbtItem.getTypeId(s));
                switch (tagType) {
                    case "double": {
                        this.tags.add(new ItemTag(s, (Object)nbtItem.getDouble(s)));
                        continue;
                    }
                    case "int": {
                        this.tags.add(new ItemTag(s, (Object)nbtItem.getInteger(s)));
                        continue;
                    }
                    case "byte": {
                        this.tags.add(new ItemTag(s, (Object)nbtItem.getBoolean(s)));
                        continue;
                    }
                    case "string": {
                        this.tags.add(new ItemTag(s, (Object)nbtItem.getString(s)));
                        continue;
                    }
                }
            }
        }
    }
    
    public void addTag(final ItemTag itemTag) {
        this.tags.add(itemTag);
    }
    
    public List<ItemTag> getTags() {
        return this.tags;
    }
    
    private String getTagType(final int n) {
        switch (n) {
            case 0: {
                return "end";
            }
            case 1: {
                return "byte";
            }
            case 2: {
                return "short";
            }
            case 3: {
                return "int";
            }
            case 4: {
                return "long";
            }
            case 5: {
                return "float";
            }
            case 6: {
                return "double";
            }
            case 7: {
                return "bytearray";
            }
            case 8: {
                return "string";
            }
            case 9: {
                return "list";
            }
            case 10: {
                return "compound";
            }
            case 11: {
                return "intarray";
            }
            default: {
                return "unknown";
            }
        }
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof StoredTagsData, "Cannot merge two different stat data types");
        this.tags.addAll(((StoredTagsData)statData).tags);
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new StoredTagsData(this.getTags());
    }
    
    @Override
    public boolean isClear() {
        return this.getTags().size() == 0;
    }
    
    static {
        ignoreList = Arrays.asList("Unbreakable", "BlockEntityTag", "display", "Enchantments", "HideFlags", "Damage", "AttributeModifiers", "SkullOwner", "CanDestroy", "PickupDelay", "Age");
    }
}
