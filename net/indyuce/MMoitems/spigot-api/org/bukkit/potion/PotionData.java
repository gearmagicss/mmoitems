// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.potion;

import org.apache.commons.lang.Validate;

public final class PotionData
{
    private final PotionType type;
    private final boolean extended;
    private final boolean upgraded;
    
    public PotionData(final PotionType type, final boolean extended, final boolean upgraded) {
        Validate.notNull(type, "Potion Type must not be null");
        Validate.isTrue(!upgraded || type.isUpgradeable(), "Potion Type is not upgradable");
        Validate.isTrue(!extended || type.isExtendable(), "Potion Type is not extendable");
        Validate.isTrue(!upgraded || !extended, "Potion cannot be both extended and upgraded");
        this.type = type;
        this.extended = extended;
        this.upgraded = upgraded;
    }
    
    public PotionData(final PotionType type) {
        this(type, false, false);
    }
    
    public PotionType getType() {
        return this.type;
    }
    
    public boolean isUpgraded() {
        return this.upgraded;
    }
    
    public boolean isExtended() {
        return this.extended;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + ((this.type != null) ? this.type.hashCode() : 0);
        hash = 23 * hash + (this.extended ? 1 : 0);
        hash = 23 * hash + (this.upgraded ? 1 : 0);
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final PotionData other = (PotionData)obj;
        return this.upgraded == other.upgraded && this.extended == other.extended && this.type == other.type;
    }
}
