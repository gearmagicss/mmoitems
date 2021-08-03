// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs;

import io.lumine.xikage.mythicmobs.drops.droppables.ItemDrop;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitItemStack;
import io.lumine.xikage.mythicmobs.drops.LootBag;
import io.lumine.xikage.mythicmobs.drops.DropMetadata;
import net.Indyuce.mmoitems.api.Type;
import java.util.logging.Level;
import net.Indyuce.mmoitems.api.droptable.item.MMOItemDropItem;
import org.apache.commons.lang.Validate;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import net.Indyuce.mmoitems.api.droptable.item.DropItem;
import io.lumine.xikage.mythicmobs.drops.IMultiDrop;
import io.lumine.xikage.mythicmobs.skills.placeholders.PlaceholderMeta;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicReloadedEvent;
import org.bukkit.event.EventHandler;
import io.lumine.xikage.mythicmobs.drops.Drop;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicDropLoadEvent;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import io.lumine.xikage.mythicmobs.skills.placeholders.Placeholder;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.event.Listener;

public class MythicMobsHook implements Listener
{
    public MythicMobsHook() {
        MythicMobs.inst().getPlaceholderManager().register("mmoitems.skill", (Placeholder)Placeholder.meta((placeholderMeta, s) -> String.valueOf(PlayerData.get(placeholderMeta.getCaster().getEntity().getUniqueId()).getAbilityData().getCachedModifier(s))));
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
    }
    
    @EventHandler
    public void a(final MythicDropLoadEvent mythicDropLoadEvent) {
        if (mythicDropLoadEvent.getDropName().equalsIgnoreCase("mmoitems") || mythicDropLoadEvent.getDropName().equalsIgnoreCase("mmoitem")) {
            mythicDropLoadEvent.register((Drop)new MMOItemsDrop(mythicDropLoadEvent.getConfig()));
        }
    }
    
    @EventHandler
    public void b(final MythicReloadedEvent mythicReloadedEvent) {
        MythicMobs.inst().getPlaceholderManager().register("mmoitems.skill", (Placeholder)Placeholder.meta((placeholderMeta, s) -> String.valueOf(PlayerData.get(placeholderMeta.getCaster().getEntity().getUniqueId()).getAbilityData().getCachedModifier(s))));
    }
    
    public static class MMOItemsDrop extends Drop implements IMultiDrop
    {
        private DropItem dropItem;
        
        public MMOItemsDrop(final MythicLineConfig mythicLineConfig) {
            super(mythicLineConfig.getLine(), mythicLineConfig);
            try {
                final String replace = mythicLineConfig.getString("type").toUpperCase().replace("-", "_");
                Validate.isTrue(MMOItems.plugin.getTypes().has(replace), "Could not find type with ID " + replace);
                final Type value = MMOItems.plugin.getTypes().get(replace);
                final String string = mythicLineConfig.getString("id");
                Validate.notNull((Object)string, "MMOItems ID cannot be null");
                this.dropItem = new MMOItemDropItem(value, string, 1.0, mythicLineConfig.getDouble("unidentified", 0.0), 1, 1);
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load drop item: " + ex.getMessage());
            }
        }
        
        public LootBag get(final DropMetadata dropMetadata) {
            final LootBag lootBag = new LootBag(dropMetadata);
            if (this.dropItem != null) {
                lootBag.add((Drop)new ItemDrop(this.getLine(), (MythicLineConfig)this.getConfig(), new BukkitItemStack(this.dropItem.getItem(null, 1))));
            }
            return lootBag;
        }
    }
}
