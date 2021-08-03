// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import java.util.Iterator;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.ClickType;
import org.black_ixx.bossshop.core.BSBuy;
import org.bukkit.entity.Player;
import org.black_ixx.bossshop.managers.ClassManager;
import java.util.List;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.black_ixx.bossshop.core.rewards.BSRewardType;

public class MMOItemsRewardTypes extends BSRewardType
{
    public Object createObject(final Object o, final boolean b) {
        return InputReader.readStringList(o);
    }
    
    public boolean validityCheck(final String str, final Object o) {
        if (o != null || !(o instanceof List)) {
            return true;
        }
        ClassManager.manager.getBugFinder().severe("Couldn't load the MMOItems reward type" + str + ". The reward object needs to be a list of types & IDs (format: [ITEM_TYPE].[ITEM_ID]).");
        return false;
    }
    
    public boolean canBuy(final Player player, final BSBuy bsBuy, final boolean b, final Object o, final ClickType clickType) {
        return true;
    }
    
    public void giveReward(final Player player, final BSBuy bsBuy, final Object o, final ClickType clickType) {
        for (final String str : (List)o) {
            try {
                final String[] split = str.split("\\.");
                final Iterator<ItemStack> iterator2 = player.getInventory().addItem(new ItemStack[] { MMOItems.plugin.getItem(MMOItems.plugin.getTypes().get(split[0].toUpperCase().replace("-", "_")), split[1], PlayerData.get((OfflinePlayer)player)) }).values().iterator();
                while (iterator2.hasNext()) {
                    player.getWorld().dropItem(player.getLocation(), (ItemStack)iterator2.next());
                }
            }
            catch (Exception ex) {
                ClassManager.manager.getBugFinder().severe("Couldn't load the MMOItems reward type" + str + ". Format: [ITEM_TYPE].[ITEM_ID]).");
            }
        }
    }
    
    public String getDisplayReward(final Player player, final BSBuy bsBuy, final Object o, final ClickType clickType) {
        return "";
    }
    
    public String[] createNames() {
        return new String[] { "mmoitem", "mmoitems" };
    }
    
    public boolean mightNeedShopUpdate() {
        return false;
    }
    
    public void enableType() {
    }
}
