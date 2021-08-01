// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.Type;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class ItemStat
{
    @NotNull
    private final String id;
    @NotNull
    private final String name;
    @NotNull
    private final String configPath;
    @NotNull
    private final String nbtPath;
    @NotNull
    private final Material material;
    private final String[] lore;
    private final List<String> compatibleTypes;
    private final List<Material> compatibleMaterials;
    private boolean enabled;
    
    public ItemStat(@NotNull final String s, @NotNull final Material material, @NotNull final String name, final String[] array, final String[] a, final Material... a2) {
        this.enabled = true;
        this.id = s;
        this.material = material;
        this.lore = ((array == null) ? new String[0] : array);
        this.compatibleTypes = ((a == null) ? new ArrayList<String>() : Arrays.asList(a));
        this.name = name;
        this.compatibleMaterials = Arrays.asList(a2);
        this.configPath = s.toLowerCase().replace("_", "-");
        this.nbtPath = "MMOITEMS_" + s;
    }
    
    public abstract RandomStatData whenInitialized(final Object p0);
    
    public abstract void whenApplied(@NotNull final ItemStackBuilder p0, @NotNull final StatData p1);
    
    @NotNull
    public abstract ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData p0);
    
    public abstract void whenClicked(@NotNull final EditionInventory p0, @NotNull final InventoryClickEvent p1);
    
    public abstract void whenInput(@NotNull final EditionInventory p0, @NotNull final String p1, final Object... p2);
    
    public abstract void whenLoaded(@NotNull final ReadMMOItem p0);
    
    @Nullable
    public abstract StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> p0);
    
    public abstract void whenDisplayed(final List<String> p0, final Optional<RandomStatData> p1);
    
    @NotNull
    public String getName() {
        return this.name;
    }
    
    @NotNull
    public String getId() {
        return this.id;
    }
    
    @Deprecated
    @NotNull
    public String name() {
        return this.id;
    }
    
    @NotNull
    public String getPath() {
        return this.configPath;
    }
    
    @NotNull
    public String getNBTPath() {
        return this.nbtPath;
    }
    
    public Material getDisplayMaterial() {
        return this.material;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public String[] getLore() {
        return this.lore;
    }
    
    public List<String> getCompatibleTypes() {
        return this.compatibleTypes;
    }
    
    public boolean isCompatible(final Type type) {
        final String lowerCase = type.getId().toLowerCase();
        return type.isSubtype() ? this.isCompatible(type.getParent()) : (!this.compatibleTypes.contains("!" + lowerCase) && (this.compatibleTypes.contains("all") || this.compatibleTypes.contains(lowerCase) || this.compatibleTypes.contains(type.getItemSet().getName().toLowerCase())));
    }
    
    public boolean hasValidMaterial(final ItemStack itemStack) {
        return this.compatibleMaterials.size() == 0 || this.compatibleMaterials.contains(itemStack.getType());
    }
    
    public void disable() {
        this.enabled = false;
    }
    
    public String formatNumericStat(final double n, final String... array) {
        String s = MMOItems.plugin.getLanguage().getStatFormat(this.getPath()).replace("<plus>", (n > 0.0) ? "+" : "");
        for (int i = 0; i < array.length; i += 2) {
            s = s.replace(array[i], array[i + 1]);
        }
        return s;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ItemStat && ((ItemStat)o).getId().equals(this.getId());
    }
    
    public static String translate(final String str) {
        final String statFormat = MMOItems.plugin.getLanguage().getStatFormat(str);
        return (statFormat == null) ? ("<TranslationNotFound:" + str + ">") : statFormat;
    }
    
    @NotNull
    public abstract StatData getClearStatData();
}
