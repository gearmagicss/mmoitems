// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.attribute.Attribute;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.AttributeStat;

public class ArmorToughness extends AttributeStat
{
    public ArmorToughness() {
        super("ARMOR_TOUGHNESS", Material.DIAMOND_CHESTPLATE, "Armor Toughness", new String[] { "Armor toughness reduces damage taken." }, Attribute.GENERIC_ARMOR_TOUGHNESS);
    }
}
