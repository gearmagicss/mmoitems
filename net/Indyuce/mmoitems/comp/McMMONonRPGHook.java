// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import org.bukkit.event.EventHandler;
import io.lumine.mythic.lib.api.item.NBTItem;
import com.gmail.nossr50.events.skills.repair.McMMOPlayerRepairCheckEvent;
import org.bukkit.event.Listener;

public class McMMONonRPGHook implements Listener
{
    @EventHandler(ignoreCancelled = true)
    public void handleNoMcMMORepair(final McMMOPlayerRepairCheckEvent mcMMOPlayerRepairCheckEvent) {
        final NBTItem value = NBTItem.get(mcMMOPlayerRepairCheckEvent.getRepairedObject());
        if (value.hasType() && value.getBoolean("MMOITEMS_DISABLE_MCMMO_REPAIR")) {
            mcMMOPlayerRepairCheckEvent.setCancelled(true);
        }
    }
}
