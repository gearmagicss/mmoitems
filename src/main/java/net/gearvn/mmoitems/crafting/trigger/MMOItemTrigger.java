// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.trigger;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.Type;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;

public class MMOItemTrigger extends Trigger
{
    private final MMOItemTemplate template;
    private final int amount;
    
    public MMOItemTrigger(final MMOLineConfig mmoLineConfig) {
        super("mmoitem");
        mmoLineConfig.validate(new String[] { "type", "id" });
        final Type orThrow = MMOItems.plugin.getTypes().getOrThrow(mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_"));
        final String upperCase = mmoLineConfig.getString("id").replace("-", "_").toUpperCase();
        Validate.isTrue(MMOItems.plugin.getTemplates().hasTemplate(orThrow, upperCase), "Could not find MMOItem with ID '" + upperCase + "'");
        this.template = MMOItems.plugin.getTemplates().getTemplate(orThrow, upperCase);
        this.amount = ((mmoLineConfig.args().length > 0) ? Math.max(1, Integer.parseInt(mmoLineConfig.args()[0])) : 1);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        final ItemStack build = this.template.newBuilder(playerData.getRPG()).build().newBuilder().build();
        if (build == null || build.getType() == Material.AIR) {
            return;
        }
        build.setAmount(this.amount);
        if (build != null && build.getType() != Material.AIR) {
            new SmartGive(playerData.getPlayer()).give(new ItemStack[] { build });
        }
    }
}
