// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.enchantments;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public enum EnchantmentTarget
{
    ALL(0) {
        @Override
        public boolean includes(final Material item) {
            return true;
        }
    }, 
    ARMOR(1) {
        @Override
        public boolean includes(final Material item) {
            return EnchantmentTarget$2.ARMOR_FEET.includes(item) || EnchantmentTarget$2.ARMOR_LEGS.includes(item) || EnchantmentTarget$2.ARMOR_HEAD.includes(item) || EnchantmentTarget$2.ARMOR_TORSO.includes(item);
        }
    }, 
    ARMOR_FEET(2) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.LEATHER_BOOTS) || item.equals(Material.CHAINMAIL_BOOTS) || item.equals(Material.IRON_BOOTS) || item.equals(Material.DIAMOND_BOOTS) || item.equals(Material.GOLD_BOOTS);
        }
    }, 
    ARMOR_LEGS(3) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.LEATHER_LEGGINGS) || item.equals(Material.CHAINMAIL_LEGGINGS) || item.equals(Material.IRON_LEGGINGS) || item.equals(Material.DIAMOND_LEGGINGS) || item.equals(Material.GOLD_LEGGINGS);
        }
    }, 
    ARMOR_TORSO(4) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.LEATHER_CHESTPLATE) || item.equals(Material.CHAINMAIL_CHESTPLATE) || item.equals(Material.IRON_CHESTPLATE) || item.equals(Material.DIAMOND_CHESTPLATE) || item.equals(Material.GOLD_CHESTPLATE);
        }
    }, 
    ARMOR_HEAD(5) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.LEATHER_HELMET) || item.equals(Material.CHAINMAIL_HELMET) || item.equals(Material.DIAMOND_HELMET) || item.equals(Material.IRON_HELMET) || item.equals(Material.GOLD_HELMET);
        }
    }, 
    WEAPON(6) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.WOOD_SWORD) || item.equals(Material.STONE_SWORD) || item.equals(Material.IRON_SWORD) || item.equals(Material.DIAMOND_SWORD) || item.equals(Material.GOLD_SWORD);
        }
    }, 
    TOOL(7) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.WOOD_SPADE) || item.equals(Material.STONE_SPADE) || item.equals(Material.IRON_SPADE) || item.equals(Material.DIAMOND_SPADE) || item.equals(Material.GOLD_SPADE) || item.equals(Material.WOOD_PICKAXE) || item.equals(Material.STONE_PICKAXE) || item.equals(Material.IRON_PICKAXE) || item.equals(Material.DIAMOND_PICKAXE) || item.equals(Material.GOLD_PICKAXE) || item.equals(Material.WOOD_HOE) || item.equals(Material.STONE_HOE) || item.equals(Material.IRON_HOE) || item.equals(Material.DIAMOND_HOE) || item.equals(Material.GOLD_HOE) || item.equals(Material.WOOD_AXE) || item.equals(Material.STONE_AXE) || item.equals(Material.IRON_AXE) || item.equals(Material.DIAMOND_AXE) || item.equals(Material.GOLD_AXE) || item.equals(Material.SHEARS) || item.equals(Material.FLINT_AND_STEEL);
        }
    }, 
    BOW(8) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.BOW);
        }
    }, 
    FISHING_ROD(9) {
        @Override
        public boolean includes(final Material item) {
            return item.equals(Material.FISHING_ROD);
        }
    };
    
    private EnchantmentTarget(final String name, final int ordinal) {
    }
    
    public abstract boolean includes(final Material p0);
    
    public boolean includes(final ItemStack item) {
        return this.includes(item.getType());
    }
}
