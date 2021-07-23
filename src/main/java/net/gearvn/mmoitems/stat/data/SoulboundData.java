// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import java.util.UUID;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class SoulboundData implements StatData
{
    private final UUID uuid;
    private final String name;
    private final int level;
    
    public SoulboundData(final Player player, final int n) {
        this(player.getUniqueId(), player.getName(), n);
    }
    
    public SoulboundData(final UUID uuid, final String name, final int level) {
        this.uuid = uuid;
        this.name = name;
        this.level = level;
    }
    
    public SoulboundData(final JsonObject jsonObject) {
        this.uuid = UUID.fromString(jsonObject.get("UUID").getAsString());
        this.name = jsonObject.get("Name").getAsString();
        this.level = jsonObject.get("Level").getAsInt();
    }
    
    public UUID getUniqueId() {
        return this.uuid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Level", (Number)this.level);
        jsonObject.addProperty("Name", this.name);
        jsonObject.addProperty("UUID", this.uuid.toString());
        return jsonObject;
    }
}
