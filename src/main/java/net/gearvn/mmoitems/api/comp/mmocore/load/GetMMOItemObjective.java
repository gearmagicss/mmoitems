// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmocore.comp.citizens.CitizenInteractEvent;
import org.bukkit.event.Listener;
import net.Indyuce.mmocore.api.quest.ObjectiveProgress;
import net.Indyuce.mmocore.api.quest.QuestProgress;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmocore.api.quest.objective.Objective;

public class GetMMOItemObjective extends Objective
{
    private final Type type;
    private final String id;
    private final int required;
    private final int npcId;
    
    public GetMMOItemObjective(final ConfigurationSection configurationSection, final MMOLineConfig mmoLineConfig) {
        super(configurationSection);
        mmoLineConfig.validate(new String[] { "type", "id", "npc" });
        final String replace = mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_");
        Validate.isTrue(MMOItems.plugin.getTypes().has(replace), "Could not find item type " + replace);
        this.type = MMOItems.plugin.getTypes().get(replace);
        this.id = mmoLineConfig.getString("id");
        this.required = (mmoLineConfig.contains("amount") ? Math.max(mmoLineConfig.getInt("amount"), 1) : 1);
        this.npcId = mmoLineConfig.getInt("npc");
    }
    
    public ObjectiveProgress newProgress(final QuestProgress questProgress) {
        return new GotoProgress(questProgress, this);
    }
    
    public class GotoProgress extends ObjectiveProgress implements Listener
    {
        public GotoProgress(final QuestProgress questProgress, final Objective objective) {
            super(questProgress, objective);
        }
        
        @EventHandler
        public void a(final CitizenInteractEvent citizenInteractEvent) {
            final Player player = citizenInteractEvent.getPlayer();
            if (!this.getQuestProgress().getPlayer().isOnline()) {
                return;
            }
            if (player.equals(this.getQuestProgress().getPlayer().getPlayer()) && citizenInteractEvent.getNPC().getId() == GetMMOItemObjective.this.npcId && player.getInventory().getItemInMainHand() != null) {
                final NBTItem value = NBTItem.get(player.getInventory().getItemInMainHand());
                final int amount;
                if (value.getString("MMOITEMS_ITEM_TYPE").equals(GetMMOItemObjective.this.type.getId()) && value.getString("MMOITEMS_ITEM_ID").equals(GetMMOItemObjective.this.id) && (amount = player.getInventory().getItemInMainHand().getAmount()) >= GetMMOItemObjective.this.required) {
                    if (amount <= GetMMOItemObjective.this.required) {
                        player.getInventory().setItemInMainHand((ItemStack)null);
                    }
                    else {
                        player.getInventory().getItemInMainHand().setAmount(amount - GetMMOItemObjective.this.required);
                    }
                    this.getQuestProgress().completeObjective();
                }
            }
        }
        
        public String formatLore(final String s) {
            return s;
        }
    }
}
