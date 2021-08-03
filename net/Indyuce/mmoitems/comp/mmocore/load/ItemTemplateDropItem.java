// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmocore.api.loot.LootBuilder;
import net.Indyuce.mmoitems.api.Type;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;

public class ItemTemplateDropItem extends ItemGenerationDropItem
{
    private final MMOItemTemplate template;
    
    public ItemTemplateDropItem(final MMOLineConfig mmoLineConfig) {
        super(mmoLineConfig);
        mmoLineConfig.validate(new String[] { "type", "id" });
        final String replace = mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_");
        Validate.isTrue(MMOItems.plugin.getTypes().has(replace), "Could not find item type with ID '" + replace + "'");
        final Type value = MMOItems.plugin.getTypes().get(replace);
        final String upperCase = mmoLineConfig.getString("id").replace("-", "_").toUpperCase();
        Validate.isTrue(MMOItems.plugin.getTemplates().hasTemplate(value, upperCase), "Could not find MMOItem with ID '" + upperCase + "'");
        this.template = MMOItems.plugin.getTemplates().getTemplate(value, upperCase);
    }
    
    public void collect(final LootBuilder lootBuilder) {
        final RPGPlayer rpg = PlayerData.get(lootBuilder.getEntity().getUniqueId()).getRPG();
        final MMOItem rollMMOItem = this.rollMMOItem(this.template, rpg);
        if (this.rollSoulbound()) {
            rollMMOItem.setData(ItemStats.SOULBOUND, new SoulboundData(rpg.getPlayer(), 1));
        }
        final ItemStack rollUnidentification = this.rollUnidentification(rollMMOItem);
        rollUnidentification.setAmount(this.rollAmount());
        lootBuilder.addLoot(rollUnidentification);
    }
}
