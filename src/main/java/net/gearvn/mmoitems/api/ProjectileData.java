// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import com.google.gson.JsonObject;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import org.bukkit.potion.PotionEffectType;
import com.google.gson.JsonElement;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonArray;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import io.lumine.mythic.lib.api.item.NBTItem;

public class ProjectileData
{
    private final NBTItem sourceItem;
    private final PlayerStats.CachedStats playerStats;
    private final boolean customWeapon;
    
    public ProjectileData(final NBTItem sourceItem, final PlayerStats.CachedStats playerStats, final boolean customWeapon) {
        this.playerStats = playerStats;
        this.sourceItem = sourceItem;
        this.customWeapon = customWeapon;
    }
    
    public NBTItem getSourceItem() {
        return this.sourceItem;
    }
    
    public PlayerStats.CachedStats getPlayerStats() {
        return this.playerStats;
    }
    
    public boolean isCustomWeapon() {
        return this.customWeapon;
    }
    
    public void applyPotionEffects(final LivingEntity livingEntity) {
        if (this.sourceItem.hasTag("MMOITEMS_ARROW_POTION_EFFECTS")) {
            for (final JsonElement jsonElement : (JsonArray)MythicLib.plugin.getJson().parse(this.sourceItem.getString("MMOITEMS_ARROW_POTION_EFFECTS"), (Class)JsonArray.class)) {
                if (!jsonElement.isJsonObject()) {
                    continue;
                }
                final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                livingEntity.addPotionEffect(new PotionEffectData(PotionEffectType.getByName(asJsonObject.get("type").getAsString()), asJsonObject.get("duration").getAsDouble(), asJsonObject.get("level").getAsInt()).toEffect());
            }
        }
    }
}
