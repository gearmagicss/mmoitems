// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import java.util.Optional;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.template.explorer.TypeFilter;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.function.Predicate;
import net.Indyuce.mmoitems.api.item.template.explorer.ClassFilter;
import net.Indyuce.mmoitems.api.item.template.explorer.TemplateExplorer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmocore.api.loot.LootBuilder;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.Type;

public class RandomItemDropItem extends ItemGenerationDropItem
{
    private final boolean matchClass;
    private final String profess;
    private final Type type;
    
    public RandomItemDropItem(final MMOLineConfig mmoLineConfig) {
        super(mmoLineConfig);
        this.matchClass = mmoLineConfig.getBoolean("match-class", false);
        this.profess = mmoLineConfig.getString("class", "");
        if (mmoLineConfig.contains("type")) {
            final String replace = mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_");
            Validate.isTrue(MMOItems.plugin.getTypes().has(replace), "Could not find item type with ID '" + replace + "'");
            this.type = MMOItems.plugin.getTypes().get(replace);
        }
        else {
            this.type = null;
        }
    }
    
    public void collect(final LootBuilder lootBuilder) {
        final RPGPlayer rpg = PlayerData.get(lootBuilder.getEntity().getUniqueId()).getRPG();
        final TemplateExplorer templateExplorer = new TemplateExplorer();
        if (this.matchClass) {
            templateExplorer.applyFilter(new ClassFilter(rpg));
        }
        else if (!this.profess.isEmpty()) {
            templateExplorer.applyFilter(new ClassFilter(this.profess));
        }
        if (this.type != null) {
            templateExplorer.applyFilter(new TypeFilter(this.type));
        }
        final Optional<MMOItemTemplate> rollLoot = templateExplorer.rollLoot();
        if (!rollLoot.isPresent()) {
            return;
        }
        final MMOItem rollMMOItem = this.rollMMOItem(rollLoot.get(), rpg);
        if (this.rollSoulbound()) {
            rollMMOItem.setData(ItemStats.SOULBOUND, new SoulboundData(rpg.getPlayer(), 1));
        }
        final ItemStack rollUnidentification = this.rollUnidentification(rollMMOItem);
        rollUnidentification.setAmount(this.rollAmount());
        lootBuilder.addLoot(rollUnidentification);
    }
}
