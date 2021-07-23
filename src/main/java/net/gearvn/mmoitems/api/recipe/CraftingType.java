// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe;

import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.Material;

public enum CraftingType
{
    SHAPED(21, "The C. Table Recipe (Shaped) for this item", VersionMaterial.CRAFTING_TABLE, new int[0]), 
    SHAPELESS(22, "The C. Table Recipe (Shapeless) for this item", VersionMaterial.CRAFTING_TABLE, new int[0]), 
    FURNACE(23, "The Furnace Recipe for this item", Material.FURNACE, new int[0]), 
    BLAST(29, "The Blast Furnace Recipe for this item", VersionMaterial.BLAST_FURNACE, new int[] { 1, 14 }), 
    SMOKER(30, "The Smoker Recipe for this item", VersionMaterial.SMOKER, new int[] { 1, 14 }), 
    CAMPFIRE(32, "The Campfire Recipe for this item", VersionMaterial.CAMPFIRE, new int[] { 1, 14 }), 
    SMITHING(33, "The Smithing Recipe for this item", VersionMaterial.SMITHING_TABLE, new int[] { 1, 15 });
    
    private final int slot;
    private final String lore;
    private final Material material;
    private final int[] mustBeHigher;
    
    private CraftingType(final int n2, final String s2, final VersionMaterial versionMaterial, final int[] array) {
        this(n2, s2, versionMaterial.toMaterial(), array);
    }
    
    private CraftingType(final int slot, final String lore, final Material material, final int[] mustBeHigher) {
        this.slot = slot;
        this.lore = lore;
        this.material = material;
        this.mustBeHigher = mustBeHigher;
    }
    
    public ItemStack getItem() {
        return new ItemStack(this.material);
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public String getName() {
        return MMOUtils.caseOnWords(this.name().toLowerCase());
    }
    
    public String getLore() {
        return this.lore;
    }
    
    public boolean shouldAdd() {
        return this.mustBeHigher.length == 0 || MythicLib.plugin.getVersion().isStrictlyHigher(this.mustBeHigher);
    }
    
    public static CraftingType getBySlot(final int n) {
        for (final CraftingType craftingType : values()) {
            if (craftingType.getSlot() == n) {
                return craftingType;
            }
        }
        return null;
    }
}
