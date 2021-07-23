// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.bukkit.inventory.ItemStack;
import com.codisimus.plugins.phatloots.events.PlayerLootEvent;
import org.bukkit.event.EventHandler;
import com.codisimus.plugins.phatloots.events.LootEvent;
import com.codisimus.plugins.phatloots.events.MobDropLootEvent;
import org.bukkit.event.Listener;

public class PhatLootsHook implements Listener
{
    @EventHandler
    public void OnLootBeLooted(final MobDropLootEvent mobDropLootEvent) {
        this.handle((LootEvent)mobDropLootEvent);
    }
    
    @EventHandler
    public void OnLootBeLooted(final PlayerLootEvent playerLootEvent) {
        this.handle((LootEvent)playerLootEvent);
    }
    
    public void handle(final LootEvent lootEvent) {
        for (final ItemStack itemStack : lootEvent.getItemList()) {
            if (SilentNumbers.isAir(itemStack)) {
                continue;
            }
            final NBTItem value = NBTItem.get(itemStack);
            if (!value.hasType()) {
                continue;
            }
            if (MMOItems.plugin.getTemplates().getTemplate(MMOItems.plugin.getType(value), MMOItems.plugin.getID(value)) == null) {
                continue;
            }
            final MMOItemReforger mmoItemReforger = new MMOItemReforger(value);
            mmoItemReforger.update((RPGPlayer)null, MMOItems.plugin.getLanguage().phatLootsOptions);
            if (!mmoItemReforger.hasChanges()) {
                continue;
            }
            final ItemStack stack = mmoItemReforger.toStack();
            stack.setAmount(itemStack.getAmount());
            final ItemMeta itemMeta = stack.getItemMeta();
            itemStack.setType(stack.getType());
            itemStack.setItemMeta(itemMeta);
            itemStack.setData(stack.getData());
        }
    }
}
