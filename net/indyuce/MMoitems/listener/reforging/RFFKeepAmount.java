// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener.reforging;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeFinishEvent;
import org.bukkit.event.Listener;

public class RFFKeepAmount implements Listener
{
    @EventHandler
    public void onReforge(final MMOItemReforgeFinishEvent mmoItemReforgeFinishEvent) {
        mmoItemReforgeFinishEvent.getFinishedItem().setAmount(mmoItemReforgeFinishEvent.getReforger().getStack().getAmount());
    }
}
