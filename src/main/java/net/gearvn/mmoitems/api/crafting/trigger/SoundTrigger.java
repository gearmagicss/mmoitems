// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.trigger;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;
import org.bukkit.Sound;

public class SoundTrigger extends Trigger
{
    private final Sound sound;
    private final float vol;
    private final float pitch;
    
    public SoundTrigger(final MMOLineConfig mmoLineConfig) {
        super("sound");
        mmoLineConfig.validate(new String[] { "sound" });
        this.sound = Sound.valueOf(mmoLineConfig.getString("sound").toUpperCase().replace("-", "_"));
        this.vol = (float)mmoLineConfig.getDouble("volume", 1.0);
        this.pitch = (float)mmoLineConfig.getDouble("pitch", 1.0);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), this.sound, this.vol, this.pitch);
    }
}
