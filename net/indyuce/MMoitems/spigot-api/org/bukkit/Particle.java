// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import org.bukkit.material.MaterialData;
import org.bukkit.inventory.ItemStack;

public enum Particle
{
    EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0), 
    EXPLOSION_LARGE("EXPLOSION_LARGE", 1), 
    EXPLOSION_HUGE("EXPLOSION_HUGE", 2), 
    FIREWORKS_SPARK("FIREWORKS_SPARK", 3), 
    WATER_BUBBLE("WATER_BUBBLE", 4), 
    WATER_SPLASH("WATER_SPLASH", 5), 
    WATER_WAKE("WATER_WAKE", 6), 
    SUSPENDED("SUSPENDED", 7), 
    SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8), 
    CRIT("CRIT", 9), 
    CRIT_MAGIC("CRIT_MAGIC", 10), 
    SMOKE_NORMAL("SMOKE_NORMAL", 11), 
    SMOKE_LARGE("SMOKE_LARGE", 12), 
    SPELL("SPELL", 13), 
    SPELL_INSTANT("SPELL_INSTANT", 14), 
    SPELL_MOB("SPELL_MOB", 15), 
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16), 
    SPELL_WITCH("SPELL_WITCH", 17), 
    DRIP_WATER("DRIP_WATER", 18), 
    DRIP_LAVA("DRIP_LAVA", 19), 
    VILLAGER_ANGRY("VILLAGER_ANGRY", 20), 
    VILLAGER_HAPPY("VILLAGER_HAPPY", 21), 
    TOWN_AURA("TOWN_AURA", 22), 
    NOTE("NOTE", 23), 
    PORTAL("PORTAL", 24), 
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25), 
    FLAME("FLAME", 26), 
    LAVA("LAVA", 27), 
    FOOTSTEP("FOOTSTEP", 28), 
    CLOUD("CLOUD", 29), 
    REDSTONE("REDSTONE", 30), 
    SNOWBALL("SNOWBALL", 31), 
    SNOW_SHOVEL("SNOW_SHOVEL", 32), 
    SLIME("SLIME", 33), 
    HEART("HEART", 34), 
    BARRIER("BARRIER", 35), 
    ITEM_CRACK((Class<?>)ItemStack.class), 
    BLOCK_CRACK((Class<?>)MaterialData.class), 
    BLOCK_DUST((Class<?>)MaterialData.class), 
    WATER_DROP("WATER_DROP", 39), 
    ITEM_TAKE("ITEM_TAKE", 40), 
    MOB_APPEARANCE("MOB_APPEARANCE", 41), 
    DRAGON_BREATH("DRAGON_BREATH", 42), 
    END_ROD("END_ROD", 43), 
    DAMAGE_INDICATOR("DAMAGE_INDICATOR", 44), 
    SWEEP_ATTACK("SWEEP_ATTACK", 45), 
    FALLING_DUST((Class<?>)MaterialData.class);
    
    private final Class<?> dataType;
    
    private Particle(final String name, final int ordinal) {
        this.dataType = Void.class;
    }
    
    private Particle(final Class<?> data) {
        this.dataType = data;
    }
    
    public Class<?> getDataType() {
        return this.dataType;
    }
}
