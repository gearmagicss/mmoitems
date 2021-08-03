// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class LoreFormat extends StringStat
{
    public LoreFormat() {
        super("LORE_FORMAT", Material.MAP, "Lore Format", new String[] { "The lore format decides", "where each stat goes.", "&9Formats can be configured in", "&9the lore-formats folder" }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final String string = statData.toString();
        Validate.isTrue(MMOItems.plugin.getFormats().hasFormat(string), "Could not find lore format with ID '" + string + "'");
        itemStackBuilder.addItemTag(new ItemTag(this.getNBTPath(), (Object)string));
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        Validate.isTrue(MMOItems.plugin.getFormats().hasFormat(s), "Couldn't find lore format with ID '" + s + "'.");
        editionInventory.getEditedSection().set(this.getPath(), (Object)s);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Lore Format successfully changed to " + s + ".");
    }
}
