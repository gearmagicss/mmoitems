// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.flags;

import org.bukkit.entity.Player;
import org.bukkit.Location;

public interface FlagPlugin
{
    boolean isPvpAllowed(final Location p0);
    
    boolean isFlagAllowed(final Player p0, final CustomFlag p1);
    
    boolean isFlagAllowed(final Location p0, final CustomFlag p1);
    
    public enum CustomFlag
    {
        MI_ABILITIES, 
        MI_WEAPONS, 
        MI_COMMANDS, 
        MI_CONSUMABLES, 
        MI_TOOLS;
        
        public String getPath() {
            return this.name().toLowerCase().replace("_", "-");
        }
        
        private static /* synthetic */ CustomFlag[] $values() {
            return new CustomFlag[] { CustomFlag.MI_ABILITIES, CustomFlag.MI_WEAPONS, CustomFlag.MI_COMMANDS, CustomFlag.MI_CONSUMABLES, CustomFlag.MI_TOOLS };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
