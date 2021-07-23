// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.data.BooleanData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.inventory.ItemFlag;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class HideDye extends BooleanStat
{
    public HideDye() {
        super("HIDE_DYE", Material.CYAN_DYE, "Hide Dyed", new String[] { "Enable to hide the 'Dyed' tag from the item." }, new String[] { "all" }, new Material[] { Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, VersionMaterial.LEATHER_HORSE_ARMOR.toMaterial() });
        try {
            ItemFlag.valueOf("HIDE_DYE");
        }
        catch (IllegalArgumentException ex) {
            this.disable();
        }
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (((BooleanData)statData).isEnabled()) {
            itemStackBuilder.getMeta().addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DYE });
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        if (readMMOItem.getNBT().getItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_DYE)) {
            readMMOItem.setData(ItemStats.HIDE_DYE, new BooleanData(true));
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
}
