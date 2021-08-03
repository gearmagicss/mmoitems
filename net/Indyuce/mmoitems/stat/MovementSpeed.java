// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.attribute.Attribute;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.AttributeStat;

public class MovementSpeed extends AttributeStat
{
    public MovementSpeed() {
        super("MOVEMENT_SPEED", Material.LEATHER_BOOTS, "Movement Speed", new String[] { "Movement Speed increase walk speed.", "Default MC walk speed: 0.1" }, Attribute.GENERIC_MOVEMENT_SPEED);
    }
    
    @Override
    public double multiplyWhenDisplaying() {
        return 100.0;
    }
}
