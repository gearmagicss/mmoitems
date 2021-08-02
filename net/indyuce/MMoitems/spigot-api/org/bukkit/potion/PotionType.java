// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.potion;

public enum PotionType
{
    UNCRAFTABLE("UNCRAFTABLE", 0, (PotionEffectType)null, false, false), 
    WATER("WATER", 1, (PotionEffectType)null, false, false), 
    MUNDANE("MUNDANE", 2, (PotionEffectType)null, false, false), 
    THICK("THICK", 3, (PotionEffectType)null, false, false), 
    AWKWARD("AWKWARD", 4, (PotionEffectType)null, false, false), 
    NIGHT_VISION("NIGHT_VISION", 5, PotionEffectType.NIGHT_VISION, false, true), 
    INVISIBILITY("INVISIBILITY", 6, PotionEffectType.INVISIBILITY, false, true), 
    JUMP("JUMP", 7, PotionEffectType.JUMP, true, true), 
    FIRE_RESISTANCE("FIRE_RESISTANCE", 8, PotionEffectType.FIRE_RESISTANCE, false, true), 
    SPEED("SPEED", 9, PotionEffectType.SPEED, true, true), 
    SLOWNESS("SLOWNESS", 10, PotionEffectType.SLOW, false, true), 
    WATER_BREATHING("WATER_BREATHING", 11, PotionEffectType.WATER_BREATHING, false, true), 
    INSTANT_HEAL("INSTANT_HEAL", 12, PotionEffectType.HEAL, true, false), 
    INSTANT_DAMAGE("INSTANT_DAMAGE", 13, PotionEffectType.HARM, true, false), 
    POISON("POISON", 14, PotionEffectType.POISON, true, true), 
    REGEN("REGEN", 15, PotionEffectType.REGENERATION, true, true), 
    STRENGTH("STRENGTH", 16, PotionEffectType.INCREASE_DAMAGE, true, true), 
    WEAKNESS("WEAKNESS", 17, PotionEffectType.WEAKNESS, false, true), 
    LUCK("LUCK", 18, PotionEffectType.LUCK, false, false);
    
    private final PotionEffectType effect;
    private final boolean upgradeable;
    private final boolean extendable;
    
    private PotionType(final String name, final int ordinal, final PotionEffectType effect, final boolean upgradeable, final boolean extendable) {
        this.effect = effect;
        this.upgradeable = upgradeable;
        this.extendable = extendable;
    }
    
    public PotionEffectType getEffectType() {
        return this.effect;
    }
    
    public boolean isInstant() {
        return this.effect != null && this.effect.isInstant();
    }
    
    public boolean isUpgradeable() {
        return this.upgradeable;
    }
    
    public boolean isExtendable() {
        return this.extendable;
    }
    
    @Deprecated
    public int getDamageValue() {
        return this.ordinal();
    }
    
    public int getMaxLevel() {
        return this.upgradeable ? 2 : 1;
    }
    
    @Deprecated
    public static PotionType getByDamageValue(final int damage) {
        return null;
    }
    
    @Deprecated
    public static PotionType getByEffect(final PotionEffectType effectType) {
        if (effectType == null) {
            return PotionType.WATER;
        }
        PotionType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PotionType type = values[i];
            if (effectType.equals(type.effect)) {
                return type;
            }
        }
        return null;
    }
}
