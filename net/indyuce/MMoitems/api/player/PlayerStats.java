// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.Type;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.type.AttributeStat;
import net.Indyuce.mmoitems.api.player.inventory.EquippedPlayerItem;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.api.stat.modifier.ModifierSource;
import io.lumine.mythic.lib.api.stat.modifier.ModifierType;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
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
        return this.getMap().getInstance(itemStat.getId()).getTotal();
    }
    
    public StatInstance getInstance(final ItemStat itemStat) {
        return this.getMap().getInstance(itemStat.getId());
    }
    
    public CachedStats newTemporary(final EquipmentSlot equipmentSlot) {
        return new CachedStats(equipmentSlot);
    }
    
    public void updateStats() {
        this.getMap().getInstances().forEach(statInstance -> statInstance.removeIf(s -> s.startsWith("MMOItem")));
        if (this.playerData.hasSetBonuses()) {
            this.playerData.getSetBonuses().getStats().forEach((itemStat, n) -> this.getInstance(itemStat).addModifier("MMOItemSetBonus", new StatModifier((double)n, ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER)));
        }
        for (final DoubleStat doubleStat : MMOItems.plugin.getStats().getNumericStats()) {
            final StatInstance.ModifierPacket packet = this.getInstance(doubleStat).newPacket();
            int n2 = 0;
            for (final EquippedPlayerItem equippedPlayerItem : this.playerData.getInventory().getEquipped()) {
                double stat = equippedPlayerItem.getItem().getNBT().getStat(doubleStat.getId());
                if (stat != 0.0) {
                    final Type type = equippedPlayerItem.getItem().getType();
                    final ModifierSource modifierSource = (type == null) ? ModifierSource.OTHER : type.getItemSet().getModifierSource();
                    if (equippedPlayerItem.getSlot() == EquipmentSlot.MAIN_HAND && doubleStat instanceof AttributeStat) {
                        stat -= ((AttributeStat)doubleStat).getOffset();
                    }
                    packet.addModifier("MMOItem-" + n2++, new StatModifier(stat, ModifierType.FLAT, equippedPlayerItem.getSlot(), modifierSource));
                }
            }
            packet.runUpdate();
        }
    }
    
    public class CachedStats
    {
        private final Player player;
        private final Map<String, Double> stats;
        
        public CachedStats(final EquipmentSlot equipmentSlot) {
            this.stats = new HashMap<String, Double>();
            this.player = PlayerStats.this.playerData.getPlayer();
            if (equipmentSlot.isHand()) {
                equipmentSlot.getOppositeHand();
                for (final StatInstance statInstance : PlayerStats.this.getMap().getInstances()) {
                    final EquipmentSlot equipmentSlot2;
                    this.stats.put(statInstance.getStat(), statInstance.getFilteredTotal(statModifier -> statModifier.getSlot() != equipmentSlot2));
                }
            }
            else {
                for (final StatInstance statInstance2 : PlayerStats.this.getMap().getInstances()) {
                    this.stats.put(statInstance2.getStat(), statInstance2.getTotal());
                }
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
