// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.api.ItemTier;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class ItemTierStat extends StringStat
{
    public ItemTierStat() {
        super("TIER", Material.DIAMOND, "Item Tier", new String[] { "The tier defines how rare your item is", "and what item is dropped when your", "item is deconstructed.", "&9Tiers can be configured in the tiers.yml file" }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final String replace = statData.toString().toUpperCase().replace("-", "_").replace(" ", "_");
        Validate.isTrue(MMOItems.plugin.getTiers().has(replace), "Could not find item tier with ID '" + replace + "'");
        final ItemTier value = MMOItems.plugin.getTiers().get(replace);
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_TIER", (Object)replace));
        itemStackBuilder.getLore().insert("tier", MMOItems.plugin.getLanguage().getStatFormat(this.getPath()).replace("#", value.getName()));
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String replace = s.toUpperCase().replace(" ", "_").replace("-", "_");
        Validate.isTrue(MMOItems.plugin.getTiers().has(replace), "Couldn't find the tier called '" + replace + "'.");
        editionInventory.getEditedSection().set("tier", (Object)replace);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Tier successfully changed to " + replace + ".");
    }
}
