// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class CreatureSpawnEvent extends EntitySpawnEvent
{
    private final SpawnReason spawnReason;
    
    public CreatureSpawnEvent(final LivingEntity spawnee, final SpawnReason spawnReason) {
        super(spawnee);
        this.spawnReason = spawnReason;
    }
    
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    public SpawnReason getSpawnReason() {
        return this.spawnReason;
    }
    
    public enum SpawnReason
    {
        NATURAL("NATURAL", 0), 
        JOCKEY("JOCKEY", 1), 
        CHUNK_GEN("CHUNK_GEN", 2), 
        SPAWNER("SPAWNER", 3), 
        EGG("EGG", 4), 
        SPAWNER_EGG("SPAWNER_EGG", 5), 
        LIGHTNING("LIGHTNING", 6), 
        @Deprecated
        BED("BED", 7), 
        BUILD_SNOWMAN("BUILD_SNOWMAN", 8), 
        BUILD_IRONGOLEM("BUILD_IRONGOLEM", 9), 
        BUILD_WITHER("BUILD_WITHER", 10), 
        VILLAGE_DEFENSE("VILLAGE_DEFENSE", 11), 
        VILLAGE_INVASION("VILLAGE_INVASION", 12), 
        BREEDING("BREEDING", 13), 
        SLIME_SPLIT("SLIME_SPLIT", 14), 
        REINFORCEMENTS("REINFORCEMENTS", 15), 
        NETHER_PORTAL("NETHER_PORTAL", 16), 
        DISPENSE_EGG("DISPENSE_EGG", 17), 
        INFECTION("INFECTION", 18), 
        CURED("CURED", 19), 
        OCELOT_BABY("OCELOT_BABY", 20), 
        SILVERFISH_BLOCK("SILVERFISH_BLOCK", 21), 
        MOUNT("MOUNT", 22), 
        TRAP("TRAP", 23), 
        CUSTOM("CUSTOM", 24), 
        DEFAULT("DEFAULT", 25);
        
        private SpawnReason(final String name, final int ordinal) {
        }
    }
}
