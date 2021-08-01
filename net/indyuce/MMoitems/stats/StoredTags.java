// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.Collection;
import net.Indyuce.mmoitems.stat.data.StoredTagsData;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Optional;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.InternalStat;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class StoredTags extends ItemStat implements InternalStat, GemStoneStat
{
    public StoredTags() {
        super("STORED_TAGS", VersionMaterial.OAK_SIGN.toMaterial(), "Stored Tags", new String[] { "You found a secret dev easter egg", "introduced during the 2020 epidemic!" }, new String[] { "all" }, new Material[0]);
    }
    
    @Nullable
    @Override
    public RandomStatData whenInitialized(final Object o) {
        return null;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
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
