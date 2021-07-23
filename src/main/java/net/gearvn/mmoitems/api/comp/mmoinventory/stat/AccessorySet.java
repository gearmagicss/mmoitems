// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmoinventory.stat;

import org.bukkit.ChatColor;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class AccessorySet extends StringStat
{
    public AccessorySet() {
        super("ACCESSORY_SET", VersionMaterial.OAK_SIGN.toMaterial(), "Accessory Set (MMOInventory)", new String[] { "Used with MMOInventory's unique", "restriction to only allow one", "accessory to be equipped per set." }, new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath(), (Object)statData.toString()));
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull String lowerCase, final Object... array) {
        lowerCase = lowerCase.toLowerCase();
        editionInventory.getEditedSection().set(this.getPath(), (Object)lowerCase);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to " + MythicLib.plugin.parseColors(lowerCase) + ChatColor.GRAY + ".");
    }
}
