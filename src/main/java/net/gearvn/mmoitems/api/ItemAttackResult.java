// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import net.Indyuce.mmoitems.api.ability.Ability;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.LivingEntity;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.api.AttackResult;

public class ItemAttackResult extends AttackResult
{
    public ItemAttackResult(final boolean b, final DamageType... array) {
        super(b, 0.0, array);
    }
    
    public ItemAttackResult(final double n, final DamageType... array) {
        super(true, n, array);
    }
    
    public ItemAttackResult(final ItemAttackResult itemAttackResult) {
        super((AttackResult)itemAttackResult);
    }
    
    public ItemAttackResult clone() {
        return new ItemAttackResult(this);
    }
    
    public ItemAttackResult setSuccessful(final boolean successful) {
        return (ItemAttackResult)super.setSuccessful(successful);
    }
    
    public ItemAttackResult multiplyDamage(final double n) {
        return (ItemAttackResult)super.multiplyDamage(n);
    }
    
    public void applyEffectsAndDamage(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final LivingEntity livingEntity) {
        MythicLib.plugin.getDamage().damage(cachedStats.getPlayer(), livingEntity, (AttackResult)this.applyEffects(cachedStats, nbtItem, livingEntity));
    }
    
    public ItemAttackResult applyEffects(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final LivingEntity livingEntity) {
        if (this.hasType(DamageType.WEAPON)) {
            this.applyElementalEffects(cachedStats, nbtItem, livingEntity);
            this.applyOnHitEffects(cachedStats, livingEntity);
        }
        return this;
    }
    
    public ItemAttackResult applyElementalEffects(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final LivingEntity livingEntity) {
        new ElementalAttack(nbtItem, this, livingEntity).apply(cachedStats);
        return this;
    }
    
    public ItemAttackResult applyOnHitEffects(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity) {
        cachedStats.getData().castAbilities(cachedStats, livingEntity, this, Ability.CastingMode.ON_HIT);
        return this;
    }
}
