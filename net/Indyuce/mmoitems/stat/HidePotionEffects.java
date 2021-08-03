// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.bukkit.inventory.ItemFlag;
import net.Indyuce.mmoitems.stat.data.BooleanData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class HidePotionEffects extends BooleanStat
{
    public HidePotionEffects() {
        super("HIDE_POTION_EFFECTS", Material.POTION, "Hide Potion Effects", new String[] { "Hides potion effects & 'No Effects'", "from your item lore." }, new String[] { "all" }, new Material[] { Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.TIPPED_ARROW });
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (((BooleanData)statData).isEnabled()) {
            itemStackBuilder.getMeta().addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        }
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        if (readMMOItem.getNBT().getItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)) {
            readMMOItem.setData(ItemStats.HIDE_POTION_EFFECTS, new BooleanData(true));
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
}
