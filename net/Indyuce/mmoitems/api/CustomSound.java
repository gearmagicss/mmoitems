// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CustomSound
{
    ON_ATTACK(Material.IRON_SWORD, 19, new String[] { "Plays when attacking an entity." }), 
    ON_RIGHT_CLICK(Material.STONE_HOE, 22, new String[] { "Plays when item is right-clicked." }), 
    ON_BLOCK_BREAK(Material.COBBLESTONE, 25, new String[] { "Plays when a block is broken with the item." }), 
    ON_PICKUP(Material.IRON_INGOT, 28, new String[] { "Plays when you pickup the item from the ground." }), 
    ON_LEFT_CLICK(Material.STONE_AXE, 31, new String[] { "Plays when item is left-clicked." }), 
    ON_CRAFT(VersionMaterial.CRAFTING_TABLE.toMaterial(), 34, new String[] { "Plays when item is crafted in a crafting inventory", "or when smelted from someting in a furnace." }), 
    ON_CONSUME(Material.APPLE, 37, new String[] { "Plays when item has been consumed.", "(After eating/drinking animation)" }), 
    ON_ITEM_BREAK(Material.FLINT, 40, new String[] { "Plays when the item breaks." }), 
    ON_PLACED(Material.STONE, 43, new String[] { "Plays when the block is placed." });
    
    private final ItemStack item;
    private final String[] lore;
    private final int slot;
    
    private CustomSound(final Material material, final int slot, final String[] lore) {
        this.item = new ItemStack(material);
        this.lore = lore;
        this.slot = slot;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public String getName() {
        return MMOUtils.caseOnWords(this.name().toLowerCase().replace('_', ' '));
    }
    
    public String[] getLore() {
        return this.lore;
    }
    
    public int getSlot() {
        return this.slot;
    }
}
