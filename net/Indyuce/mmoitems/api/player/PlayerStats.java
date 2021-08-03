// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.type.AttributeStat;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.player.inventory.EquippedPlayerItem;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.api.stat.modifier.ModifierType;
import io.lumine.mythic.lib.api.stat.StatInstance;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.stat.StatMap;

public class PlayerStats
{
    private final PlayerData playerData;
    
    public PlayerStats(final PlayerData playerData) {
        this.playerData = playerData;
    }
    
    public PlayerData getData() {
        return this.playerData;
    }
    
    public StatMap getMap() {
        return this.playerData.getMMOPlayerData().getStatMap();
    }
    
    public double getStat(final ItemStat itemStat) {
        return this.getInstance(itemStat).getTotal();
    }
    
    public StatInstance getInstance(final ItemStat itemStat) {
        return this.getMap().getInstance(itemStat.getId());
    }
    
    public CachedStats newTemporary() {
        return new CachedStats();
    }
    
    public void updateStats() {
        this.getMap().getInstances().forEach(statInstance -> {
            statInstance.remove("MMOItem");
            statInstance.remove("MMOItemSetBonus");
            return;
        });
        if (this.playerData.hasSetBonuses()) {
            this.playerData.getSetBonuses().getStats().forEach((itemStat, n) -> this.getInstance(itemStat).addModifier("MMOItemSetBonus", new StatModifier((double)n, ModifierType.FLAT)));
        }
        for (final DoubleStat doubleStat : MMOItems.plugin.getStats().getNumericStats()) {
            double n2 = 0.0;
            int n3 = 0;
            for (final EquippedPlayerItem equippedPlayerItem : this.playerData.getInventory().getEquipped()) {
                final double stat = equippedPlayerItem.getItem().getNBT().getStat(doubleStat.getId());
                if (stat != 0.0) {
                    n2 += stat;
                    if (n3 != 0 || equippedPlayerItem.getSlot() != Type.EquipmentSlot.MAIN_HAND) {
                        continue;
                    }
                    n3 = 1;
                }
            }
            if (n2 != 0.0) {
                this.getInstance(doubleStat).addModifier("MMOItem", new StatModifier(n2 - ((n3 != 0 && doubleStat instanceof AttributeStat) ? ((AttributeStat)doubleStat).getOffset() : 0.0), ModifierType.FLAT));
            }
        }
    }
    
    public class CachedStats
    {
        private final Player player;
        private final Map<String, Double> stats;
        
        public CachedStats() {
            this.stats = new HashMap<String, Double>();
            this.player = PlayerStats.this.playerData.getPlayer();
            for (final StatInstance statInstance : PlayerStats.this.getMap().getInstances()) {
                this.stats.put(statInstance.getStat(), statInstance.getTotal());
            }
        }
        
        public PlayerData getData() {
            return PlayerStats.this.playerData;
        }
        
        public Player getPlayer() {
            return this.player;
        }
        
        public double getStat(final ItemStat itemStat) {
            return this.stats.containsKey(itemStat.getId()) ? this.stats.get(itemStat.getId()) : 0.0;
        }
        
        public void setStat(final ItemStat itemStat, final double d) {
            this.stats.put(itemStat.getId(), d);
        }
    }
}
