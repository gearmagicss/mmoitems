// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Iterator;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import org.bukkit.potion.PotionEffect;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.potion.PotionEffectType;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import java.util.List;
import java.util.Map;

public class ItemSet
{
    private final Map<Integer, SetBonuses> bonuses;
    private final List<String> loreTag;
    private final String name;
    private final String id;
    private static final int itemLimit = 10;
    
    public ItemSet(final ConfigurationSection configurationSection) {
        this.bonuses = new HashMap<Integer, SetBonuses>();
        this.id = configurationSection.getName().toUpperCase().replace("-", "_");
        this.loreTag = (List<String>)configurationSection.getStringList("lore-tag");
        this.name = configurationSection.getString("name");
        Validate.isTrue(configurationSection.contains("bonuses"), "Could not find item set bonuses");
        for (int i = 2; i <= 10; ++i) {
            if (configurationSection.getConfigurationSection("bonuses").contains("" + i)) {
                final SetBonuses setBonuses = new SetBonuses();
                for (final String str : configurationSection.getConfigurationSection("bonuses." + i).getKeys(false)) {
                    try {
                        final String replace = str.toUpperCase().replace("-", "_").replace(" ", "_");
                        if (str.startsWith("ability-")) {
                            setBonuses.addAbility(new AbilityData(configurationSection.getConfigurationSection("bonuses." + i + "." + str)));
                        }
                        else if (str.startsWith("potion-")) {
                            final PotionEffectType byName = PotionEffectType.getByName(replace.substring("potion-".length()));
                            Validate.notNull((Object)byName, "Could not load potion effect type from '" + replace + "'");
                            setBonuses.addPotionEffect(new PotionEffect(byName, MMOUtils.getEffectDuration(byName), configurationSection.getInt("bonuses." + i + "." + str) - 1, true, false));
                        }
                        else if (str.startsWith("particle-")) {
                            setBonuses.addParticle(new ParticleData(configurationSection.getConfigurationSection("bonuses." + i + "." + str)));
                        }
                        else {
                            final ItemStat value = MMOItems.plugin.getStats().get(replace);
                            Validate.notNull((Object)value, "Could not find stat called '" + replace + "'");
                            setBonuses.addStat(value, configurationSection.getDouble("bonuses." + i + "." + str));
                        }
                    }
                    catch (IllegalArgumentException ex) {
                        throw new IllegalArgumentException("Could not load set bonus '" + str + "': " + ex.getMessage());
                    }
                }
                this.bonuses.put(i, setBonuses);
            }
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public SetBonuses getBonuses(final int a) {
        final SetBonuses setBonuses = new SetBonuses();
        for (int i = 2; i <= Math.min(a, 10); ++i) {
            if (this.bonuses.containsKey(i)) {
                setBonuses.merge(this.bonuses.get(i));
            }
        }
        return setBonuses;
    }
    
    public List<String> getLoreTag() {
        return this.loreTag;
    }
    
    public static class SetBonuses
    {
        private final Map<ItemStat, Double> stats;
        private final Map<PotionEffectType, PotionEffect> permEffects;
        private final Set<AbilityData> abilities;
        private final Set<ParticleData> particles;
        
        public SetBonuses() {
            this.stats = new HashMap<ItemStat, Double>();
            this.permEffects = new HashMap<PotionEffectType, PotionEffect>();
            this.abilities = new HashSet<AbilityData>();
            this.particles = new HashSet<ParticleData>();
        }
        
        public void addStat(final ItemStat itemStat, final double d) {
            this.stats.put(itemStat, d);
        }
        
        public void addPotionEffect(final PotionEffect potionEffect) {
            this.permEffects.put(potionEffect.getType(), potionEffect);
        }
        
        public void addAbility(final AbilityData abilityData) {
            this.abilities.add(abilityData);
        }
        
        public void addParticle(final ParticleData particleData) {
            this.particles.add(particleData);
        }
        
        public Map<ItemStat, Double> getStats() {
            return this.stats;
        }
        
        public Collection<PotionEffect> getPotionEffects() {
            return this.permEffects.values();
        }
        
        public Set<ParticleData> getParticles() {
            return this.particles;
        }
        
        public Set<AbilityData> getAbilities() {
            return this.abilities;
        }
        
        public void merge(final SetBonuses setBonuses) {
            final Double n2;
            setBonuses.getStats().forEach((itemStat, n) -> n2 = this.stats.put(itemStat, (this.stats.containsKey(itemStat) ? this.stats.get(itemStat) : 0.0) + n));
            for (final PotionEffect potionEffect : setBonuses.getPotionEffects()) {
                if (!this.permEffects.containsKey(potionEffect.getType()) || this.permEffects.get(potionEffect.getType()).getAmplifier() < potionEffect.getAmplifier()) {
                    this.permEffects.put(potionEffect.getType(), potionEffect);
                }
            }
            this.abilities.addAll(setBonuses.getAbilities());
        }
    }
}
