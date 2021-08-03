// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.recipe.Recipe;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.crafting.recipe.CraftingRecipe;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.HashMap;
import java.util.Map;

public class CraftingStatus
{
    private final Map<String, CraftingQueue> queues;
    
    public CraftingStatus() {
        this.queues = new HashMap<String, CraftingQueue>();
    }
    
    public void load(final PlayerData playerData, final ConfigurationSection configurationSection) {
        final String str = playerData.isOnline() ? playerData.getPlayer().getName() : "Unknown Player";
        for (final String s : configurationSection.getKeys(false)) {
            if (!MMOItems.plugin.getCrafting().hasStation(s)) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "An error occured while trying to load crafting station recipe data of '" + str + "': could not find crafting station with ID '" + s + "', make sure you backup that player data file before the user logs off.");
            }
            else {
                final CraftingStation station = MMOItems.plugin.getCrafting().getStation(s);
                final CraftingQueue craftingQueue = new CraftingQueue(station);
                this.queues.put(s, craftingQueue);
                for (final String str2 : configurationSection.getConfigurationSection(s).getKeys(false)) {
                    final String string = configurationSection.getString(s + "." + str2 + ".recipe");
                    if (string == null || !station.hasRecipe(string)) {
                        MMOItems.plugin.getLogger().log(Level.WARNING, "An error occured while trying to load crafting station recipe data of '" + str + "': could not find recipe with ID '" + string + "', make sure you backup that player data file before the user logs off.");
                    }
                    else {
                        final Recipe recipe = station.getRecipe(string);
                        if (!(recipe instanceof CraftingRecipe)) {
                            MMOItems.plugin.getLogger().log(Level.WARNING, "An error occured while trying to load crafting station recipe data of '" + str + "': recipe '" + recipe.getId() + "' is not a CRAFTING recipe.");
                        }
                        else {
                            craftingQueue.add((CraftingRecipe)recipe, configurationSection.getLong(s + "." + str2 + ".started"), configurationSection.getLong(s + "." + str2 + ".delay"));
                        }
                    }
                }
            }
        }
    }
    
    public void save(final ConfigurationSection configurationSection) {
        for (final String str : this.queues.keySet()) {
            for (final CraftingQueue.CraftingInfo craftingInfo : this.queues.get(str).getCrafts()) {
                configurationSection.set(str + ".recipe-" + craftingInfo.getUniqueId().toString() + ".recipe", (Object)craftingInfo.getRecipe().getId());
                configurationSection.set(str + ".recipe-" + craftingInfo.getUniqueId().toString() + ".started", (Object)craftingInfo.started);
                configurationSection.set(str + ".recipe-" + craftingInfo.getUniqueId().toString() + ".delay", (Object)craftingInfo.delay);
            }
        }
    }
    
    public CraftingQueue getQueue(final CraftingStation craftingStation) {
        if (!this.queues.containsKey(craftingStation.getId())) {
            this.queues.put(craftingStation.getId(), new CraftingQueue(craftingStation));
        }
        return this.queues.get(craftingStation.getId());
    }
    
    public static class CraftingQueue
    {
        private final String station;
        private final List<CraftingInfo> crafts;
        
        public CraftingQueue(final CraftingStation craftingStation) {
            this.crafts = new ArrayList<CraftingInfo>();
            this.station = craftingStation.getId();
        }
        
        public List<CraftingInfo> getCrafts() {
            return this.crafts;
        }
        
        public boolean isFull(final CraftingStation craftingStation) {
            return this.crafts.size() >= craftingStation.getMaxQueueSize();
        }
        
        public void remove(final CraftingInfo craftingInfo) {
            final int index = this.crafts.indexOf(craftingInfo);
            if (index != -1) {
                for (int i = index; i < this.crafts.size(); ++i) {
                    this.crafts.get(i).removeDelay(Math.max(0L, craftingInfo.getLeft() - craftingInfo.getElapsed()));
                }
            }
            this.crafts.remove(craftingInfo);
        }
        
        public CraftingInfo getCraft(final UUID obj) {
            for (final CraftingInfo craftingInfo : this.crafts) {
                if (craftingInfo.getUniqueId().equals(obj)) {
                    return craftingInfo;
                }
            }
            return null;
        }
        
        public void add(final CraftingRecipe craftingRecipe) {
            this.add(craftingRecipe, System.currentTimeMillis(), ((this.crafts.size() == 0) ? 0L : this.crafts.get(this.crafts.size() - 1).getLeft()) + (long)craftingRecipe.getCraftingTime() * 1000L);
        }
        
        private void add(final CraftingRecipe craftingRecipe, final long n, final long n2) {
            this.crafts.add(new CraftingInfo(craftingRecipe, n, n2));
        }
        
        public CraftingStation getStation() {
            return MMOItems.plugin.getCrafting().getStation(this.station);
        }
        
        public class CraftingInfo
        {
            private final String recipe;
            private final UUID uuid;
            private final long started;
            private long delay;
            
            private CraftingInfo(final CraftingRecipe craftingRecipe, final long started, final long delay) {
                this.uuid = UUID.randomUUID();
                this.recipe = craftingRecipe.getId();
                this.started = started;
                this.delay = delay;
            }
            
            public UUID getUniqueId() {
                return this.uuid;
            }
            
            public CraftingRecipe getRecipe() {
                return (CraftingRecipe)CraftingQueue.this.getStation().getRecipe(this.recipe);
            }
            
            public boolean isReady() {
                return this.getLeft() == 0L;
            }
            
            public void removeDelay(final long n) {
                this.delay -= n;
            }
            
            public long getElapsed() {
                return Math.max((long)this.getRecipe().getCraftingTime() * 1000L, System.currentTimeMillis() - this.started);
            }
            
            public long getLeft() {
                return Math.max(0L, this.started + this.delay - System.currentTimeMillis());
            }
            
            @Override
            public boolean equals(final Object o) {
                return o instanceof CraftingInfo && ((CraftingInfo)o).uuid.equals(this.uuid);
            }
        }
    }
}
