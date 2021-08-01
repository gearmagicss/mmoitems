// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmocore.api.droptable.dropitem.DropItem;

public abstract class ItemGenerationDropItem extends DropItem
{
    protected final int level;
    protected final ItemTier tier;
    private final double unidentified;
    private final double soulbound;
    
    public ItemGenerationDropItem(final MMOLineConfig mmoLineConfig) {
        super(mmoLineConfig);
        this.level = mmoLineConfig.getInt("level", 0);
        if (mmoLineConfig.contains("tier")) {
            final String replace = mmoLineConfig.getString("tier").toUpperCase().replace("-", "_").replace(" ", "_");
            Validate.isTrue(MMOItems.plugin.getTiers().has(replace), "Could not find item tier with ID '" + replace + "'");
            this.tier = MMOItems.plugin.getTiers().get(replace);
        }
        else {
            this.tier = null;
        }
        this.unidentified = mmoLineConfig.getDouble("unidentified", 0.0);
        this.soulbound = mmoLineConfig.getDouble("soulbound", 0.0);
    }
    
    public MMOItem rollMMOItem(final MMOItemTemplate mmoItemTemplate, final RPGPlayer rpgPlayer) {
        return new MMOItemBuilder(mmoItemTemplate, (this.level > 0) ? this.level : (mmoItemTemplate.hasOption(MMOItemTemplate.TemplateOption.LEVEL_ITEM) ? MMOItems.plugin.getTemplates().rollLevel(rpgPlayer.getLevel()) : 0), (this.tier != null) ? this.tier : (mmoItemTemplate.hasOption(MMOItemTemplate.TemplateOption.TIERED) ? MMOItems.plugin.getTemplates().rollTier() : null)).build();
    }
    
    public ItemStack rollUnidentification(final MMOItem mmoItem) {
        return (ItemGenerationDropItem.random.nextDouble() < this.unidentified) ? mmoItem.getType().getUnidentifiedTemplate().newBuilder(mmoItem.newBuilder().buildNBT()).build() : mmoItem.newBuilder().build();
    }
    
    public boolean rollSoulbound() {
        return ItemGenerationDropItem.random.nextDouble() < this.soulbound;
    }
}
