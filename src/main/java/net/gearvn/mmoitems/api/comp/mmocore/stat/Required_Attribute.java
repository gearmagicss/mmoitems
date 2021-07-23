// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.stat;

import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.AddonMessage;
import net.Indyuce.mmoitems.comp.mmocore.MMOCoreHook;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttribute;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class Required_Attribute extends DoubleStat implements ItemRestriction, GemStoneStat
{
    private final PlayerAttribute attribute;
    
    public Required_Attribute(final PlayerAttribute attribute) {
        super("REQUIRED_" + attribute.getId().toUpperCase().replace("-", "_"), VersionMaterial.GRAY_DYE.toMaterial(), attribute.getName() + " Requirement (MMOCore)", new String[] { "Amount of " + attribute.getName() + " points the", "player needs to use the item." }, new String[] { "!block", "all" }, new Material[0]);
        this.attribute = attribute;
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (((MMOCoreHook.MMOCoreRPGPlayer)rpgPlayer).getData().getAttributes().getAttribute(this.attribute) < nbtItem.getStat(this.getId())) {
            if (b) {
                new AddonMessage("not-enough-attribute").format(ChatColor.RED, "#attribute#", this.attribute.getName()).send(rpgPlayer.getPlayer(), "cant-use-item");
                rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            }
            return false;
        }
        return true;
    }
}
