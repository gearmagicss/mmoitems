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
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class Required_Profession extends DoubleStat implements ItemRestriction, GemStoneStat
{
    private final Profession profession;
    
    public Required_Profession(final Profession profession) {
        super("PROFESSION_" + profession.getId().toUpperCase().replace("-", "_"), Material.PINK_DYE, profession.getName() + " Requirement (MMOCore)", new String[] { "Amount of " + profession.getName() + " levels the", "player needs to use the item." }, new String[] { "!block", "all" }, new Material[0]);
        this.profession = profession;
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (((MMOCoreHook.MMOCoreRPGPlayer)rpgPlayer).getData().getCollectionSkills().getLevel(this.profession) < nbtItem.getStat(this.getId())) {
            if (b) {
                new AddonMessage("not-enough-profession").format(ChatColor.RED, "#profession#", this.profession.getName()).send(rpgPlayer.getPlayer(), "cant-use-item");
                rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            }
            return false;
        }
        return true;
    }
}
