// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.potion;

import org.bukkit.Material;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.lang.Validate;

@Deprecated
public class Potion
{
    private boolean extended;
    private boolean splash;
    private int level;
    private PotionType type;
    private static PotionBrewer brewer;
    private static final int EXTENDED_BIT = 64;
    private static final int POTION_BIT = 15;
    private static final int SPLASH_BIT = 16384;
    private static final int TIER_BIT = 32;
    private static final int TIER_SHIFT = 5;
    
    public Potion(final PotionType type) {
        this.extended = false;
        this.splash = false;
        this.level = 1;
        Validate.notNull(type, "Null PotionType");
        this.type = type;
    }
    
    @Deprecated
    public Potion(final PotionType type, final Tier tier) {
        this(type, (tier == Tier.TWO) ? 2 : 1);
        Validate.notNull(type, "Type cannot be null");
    }
    
    @Deprecated
    public Potion(final PotionType type, final Tier tier, final boolean splash) {
        this(type, (tier == Tier.TWO) ? 2 : 1, splash);
    }
    
    @Deprecated
    public Potion(final PotionType type, final Tier tier, final boolean splash, final boolean extended) {
        this(type, tier, splash);
        this.extended = extended;
    }
    
    public Potion(final PotionType type, final int level) {
        this(type);
        Validate.notNull(type, "Type cannot be null");
        Validate.isTrue(level > 0 && level < 3, "Level must be 1 or 2");
        this.level = level;
    }
    
    @Deprecated
    public Potion(final PotionType type, final int level, final boolean splash) {
        this(type, level);
        this.splash = splash;
    }
    
    @Deprecated
    public Potion(final PotionType type, final int level, final boolean splash, final boolean extended) {
        this(type, level, splash);
        this.extended = extended;
    }
    
    @Deprecated
    public Potion(final int name) {
        this(PotionType.WATER);
    }
    
    public Potion splash() {
        this.setSplash(true);
        return this;
    }
    
    public Potion extend() {
        this.setHasExtendedDuration(true);
        return this;
    }
    
    public void apply(final ItemStack to) {
        Validate.notNull(to, "itemstack cannot be null");
        Validate.isTrue(to.hasItemMeta(), "given itemstack is not a potion");
        Validate.isTrue(to.getItemMeta() instanceof PotionMeta, "given itemstack is not a potion");
        final PotionMeta meta = (PotionMeta)to.getItemMeta();
        meta.setBasePotionData(new PotionData(this.type, this.extended, this.level == 2));
        to.setItemMeta(meta);
    }
    
    public void apply(final LivingEntity to) {
        Validate.notNull(to, "entity cannot be null");
        to.addPotionEffects(this.getEffects());
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final Potion other = (Potion)obj;
        return this.extended == other.extended && this.splash == other.splash && this.level == other.level && this.type == other.type;
    }
    
    public Collection<PotionEffect> getEffects() {
        return getBrewer().getEffects(this.type, this.level == 2, this.extended);
    }
    
    public int getLevel() {
        return this.level;
    }
    
    @Deprecated
    public Tier getTier() {
        return (this.level == 2) ? Tier.TWO : Tier.ONE;
    }
    
    public PotionType getType() {
        return this.type;
    }
    
    public boolean hasExtendedDuration() {
        return this.extended;
    }
    
    @Override
    public int hashCode() {
        int result = 31 + this.level;
        result = 31 * result + (this.extended ? 1231 : 1237);
        result = 31 * result + (this.splash ? 1231 : 1237);
        result = 31 * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }
    
    public boolean isSplash() {
        return this.splash;
    }
    
    public void setHasExtendedDuration(final boolean isExtended) {
        Validate.isTrue(this.type == null || !this.type.isInstant(), "Instant potions cannot be extended");
        this.extended = isExtended;
    }
    
    public void setSplash(final boolean isSplash) {
        this.splash = isSplash;
    }
    
    @Deprecated
    public void setTier(final Tier tier) {
        Validate.notNull(tier, "tier cannot be null");
        this.level = ((tier == Tier.TWO) ? 2 : 1);
    }
    
    public void setType(final PotionType type) {
        this.type = type;
    }
    
    public void setLevel(final int level) {
        Validate.notNull(this.type, "No-effect potions don't have a level.");
        Validate.isTrue(level > 0 && level <= 2, "Level must be between 1 and 2 for this potion");
        this.level = level;
    }
    
    @Deprecated
    public short toDamageValue() {
        return 0;
    }
    
    public ItemStack toItemStack(final int amount) {
        Material material;
        if (this.isSplash()) {
            material = Material.SPLASH_POTION;
        }
        else {
            material = Material.POTION;
        }
        final ItemStack itemStack = new ItemStack(material, amount);
        final PotionMeta meta = (PotionMeta)itemStack.getItemMeta();
        meta.setBasePotionData(new PotionData(this.type, this.level == 2, this.extended));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    
    public static Potion fromDamage(final int damage) {
        PotionType type = null;
        switch (damage & 0xF) {
            case 0: {
                type = PotionType.WATER;
                break;
            }
            case 1: {
                type = PotionType.REGEN;
                break;
            }
            case 2: {
                type = PotionType.SPEED;
                break;
            }
            case 3: {
                type = PotionType.FIRE_RESISTANCE;
                break;
            }
            case 4: {
                type = PotionType.POISON;
                break;
            }
            case 5: {
                type = PotionType.INSTANT_HEAL;
                break;
            }
            case 6: {
                type = PotionType.NIGHT_VISION;
                break;
            }
            case 8: {
                type = PotionType.WEAKNESS;
                break;
            }
            case 9: {
                type = PotionType.STRENGTH;
                break;
            }
            case 10: {
                type = PotionType.SLOWNESS;
                break;
            }
            case 11: {
                type = PotionType.JUMP;
                break;
            }
            case 12: {
                type = PotionType.INSTANT_DAMAGE;
                break;
            }
            case 13: {
                type = PotionType.WATER_BREATHING;
                break;
            }
            case 14: {
                type = PotionType.INVISIBILITY;
                break;
            }
            default: {
                type = PotionType.WATER;
                break;
            }
        }
        Potion potion;
        if (type == null || type == PotionType.WATER) {
            potion = new Potion(PotionType.WATER);
        }
        else {
            int level = (damage & 0x20) >> 5;
            ++level;
            potion = new Potion(type, level);
        }
        if ((damage & 0x4000) > 0) {
            potion = potion.splash();
        }
        if ((damage & 0x40) > 0) {
            potion = potion.extend();
        }
        return potion;
    }
    
    public static Potion fromItemStack(final ItemStack item) {
        Validate.notNull(item, "item cannot be null");
        if (item.getType() != Material.POTION) {
            throw new IllegalArgumentException("item is not a potion");
        }
        return fromDamage(item.getDurability());
    }
    
    public static PotionBrewer getBrewer() {
        return Potion.brewer;
    }
    
    public static void setPotionBrewer(final PotionBrewer other) {
        if (Potion.brewer != null) {
            throw new IllegalArgumentException("brewer can only be set internally");
        }
        Potion.brewer = other;
    }
    
    @Deprecated
    public int getNameId() {
        return 0;
    }
    
    @Deprecated
    public enum Tier
    {
        ONE("ONE", 0, 0), 
        TWO("TWO", 1, 32);
        
        private int damageBit;
        
        private Tier(final String name, final int ordinal, final int bit) {
            this.damageBit = bit;
        }
        
        public int getDamageBit() {
            return this.damageBit;
        }
        
        public static Tier getByDamageBit(final int damageBit) {
            Tier[] values;
            for (int length = (values = values()).length, i = 0; i < length; ++i) {
                final Tier tier = values[i];
                if (tier.damageBit == damageBit) {
                    return tier;
                }
            }
            return null;
        }
    }
}
