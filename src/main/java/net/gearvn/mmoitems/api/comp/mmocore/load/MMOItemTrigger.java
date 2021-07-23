// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmoitems.api.Type;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmocore.api.quest.trigger.Trigger;

public class MMOItemTrigger extends Trigger
{
    private final MMOItemTemplate template;
    private final int amount;
    
    public MMOItemTrigger(final MMOLineConfig mmoLineConfig) {
        super(mmoLineConfig);
        mmoLineConfig.validate(new String[] { "type", "id" });
        final String replace = mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_");
        Validate.isTrue(MMOItems.plugin.getTypes().has(replace), "Could not find item type with ID '" + replace + "'");
        final Type value = MMOItems.plugin.getTypes().get(replace);
        final String upperCase = mmoLineConfig.getString("id").replace("-", "_").toUpperCase();
        Validate.isTrue(MMOItems.plugin.getTemplates().hasTemplate(value, upperCase), "Could not find MMOItem with ID '" + upperCase + "'");
        this.template = MMOItems.plugin.getTemplates().getTemplate(value, upperCase);
        this.amount = ((mmoLineConfig.args().length > 0) ? Math.max(1, Integer.parseInt(mmoLineConfig.args()[0])) : 1);
    }
    
    public void apply(final PlayerData playerData) {
        final ItemStack build = this.template.newBuilder(net.Indyuce.mmoitems.api.player.PlayerData.get(playerData.getUniqueId()).getRPG()).build().newBuilder().build();
        build.setAmount(this.amount);
        new SmartGive(playerData.getPlayer()).give(new ItemStack[] { build });
    }
}
