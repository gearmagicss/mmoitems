// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.Arrays;
import io.lumine.mythic.lib.api.MMOLineConfig;
import java.util.List;

public class PermissionCondition extends Condition
{
    private final List<String> permissions;
    
    public PermissionCondition(final MMOLineConfig mmoLineConfig) {
        super("permission");
        mmoLineConfig.validate(new String[] { "list" });
        this.permissions = Arrays.asList(mmoLineConfig.getString("list").split(","));
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return false;
        }
        final Iterator<String> iterator = this.permissions.iterator();
        while (iterator.hasNext()) {
            if (!playerData.getPlayer().hasPermission((String)iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#perms#", String.join(", ", this.permissions));
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
    }
}
