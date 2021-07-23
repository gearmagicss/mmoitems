// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.trigger;

import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class MessageTrigger extends Trigger
{
    private final String message;
    
    public MessageTrigger(final MMOLineConfig mmoLineConfig) {
        super("message");
        mmoLineConfig.validate(new String[] { "format" });
        this.message = mmoLineConfig.getString("format");
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        playerData.getPlayer().sendMessage(MMOItems.plugin.getPlaceholderParser().parse((OfflinePlayer)playerData.getPlayer(), this.message));
    }
}
