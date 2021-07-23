// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class CraftingPermission extends StringStat
{
    public CraftingPermission() {
        super("CRAFT_PERMISSION", VersionMaterial.OAK_SIGN.toMaterial(), "Crafting Recipe Permission", new String[] { "The permission needed to craft this item.", "Changing this value requires &o/mi reload recipes&7." }, new String[] { "all" }, new Material[0]);
        this.disable();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
}
