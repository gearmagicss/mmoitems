// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import com.mojang.authlib.GameProfile;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class SkullTextureData implements StatData, RandomStatData
{
    private final GameProfile profile;
    
    public SkullTextureData(final GameProfile profile) {
        this.profile = profile;
    }
    
    public GameProfile getGameProfile() {
        return this.profile;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
