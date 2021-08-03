// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.util.HashMap;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import java.util.Map;

public class ElementalAttack
{
    private final Map<Element, Double> relative;
    private final Map<Element, Double> absolute;
    private final ItemAttackResult result;
    private final LivingEntity target;
    private static final Random random;
    
    public ElementalAttack(final NBTItem nbtItem, final ItemAttackResult result, final LivingEntity target) {
        this.relative = new HashMap<Element, Double>();
        this.absolute = new HashMap<Element, Double>();
        this.result = result;
        this.target = target;
        for (final Element element : Element.values()) {
            final double stat = nbtItem.getStat(element.name() + "_DAMAGE");
            if (stat > 0.0) {
                this.relative.put(element, stat);
                final double d = stat / 100.0 * result.getDamage();
                result.addDamage(-d);
                this.absolute.put(element, d);
            }
        }
    }
    
    public void apply(final PlayerStats.CachedStats cachedStats) {
        final ItemStack[] armorContents = this.target.getEquipment().getArmorContents();
        for (int length = armorContents.length, i = 0; i < length; ++i) {
            final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(armorContents[i]);
            if (nbtItem.getType() != null) {
                for (final Element element : this.absolute.keySet()) {
                    final double n = nbtItem.getStat(element.name() + "_DEFENSE") / 100.0;
                    if (n > 0.0) {
                        this.relative.put(element, this.relative.get(element) * (1.0 - n));
                        this.absolute.put(element, this.absolute.get(element) * (1.0 - n));
                    }
                }
            }
        }
        double n2 = 1.0;
        if (!cachedStats.getData().isOnCooldown(PlayerData.CooldownType.ELEMENTAL_ATTACK)) {
            for (final Element element2 : this.relative.keySet()) {
                final double doubleValue = this.relative.get(element2);
                if (ElementalAttack.random.nextDouble() < doubleValue / 100.0 / n2) {
                    cachedStats.getData().applyCooldown(PlayerData.CooldownType.ELEMENTAL_ATTACK, 2.0);
                    element2.getHandler().elementAttack(cachedStats, this.result, this.target, doubleValue, this.absolute.get(element2));
                    break;
                }
                n2 -= doubleValue / 100.0;
            }
        }
        for (final Element element3 : this.absolute.keySet()) {
            final double doubleValue2 = this.absolute.get(element3);
            if (doubleValue2 > 0.0) {
                this.result.addDamage(doubleValue2);
                element3.getParticle().displayParticle((Entity)this.target);
            }
        }
    }
    
    static {
        random = new Random();
    }
}
