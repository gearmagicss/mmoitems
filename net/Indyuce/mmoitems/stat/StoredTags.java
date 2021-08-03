// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.Collection;
import net.Indyuce.mmoitems.stat.data.StoredTagsData;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.InternalStat;

public class StoredTags extends InternalStat implements GemStoneStat
{
    public StoredTags() {
        super("STORED_TAGS", VersionMaterial.OAK_SIGN.toMaterial(), "Stored Tags", new String[] { "You found a secret dev easter egg", "introduced during the 2020 epidemic!" }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>(((StoredTagsData)statData).getTags());
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        readMMOItem.setData(ItemStats.STORED_TAGS, new StoredTagsData(readMMOItem.getNBT()));
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return new StoredTagsData(list);
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StoredTagsData(new ArrayList<ItemTag>());
    }
}
