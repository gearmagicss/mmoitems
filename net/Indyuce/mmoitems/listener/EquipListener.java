// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.block.Action;
import io.lumine.mythic.utils.Events;
import org.bukkit.event.player.PlayerInteractEvent;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.manager.Reloadable;

public class EquipListener implements Reloadable, Listener
{
    public EquipListener() {
        this.reload();
    }
    
    @Override
    public void reload() {
        if (MMOItems.plugin.getConfig().getBoolean("auto-equip-feature", true)) {
            final Integer n;
            Events.subscribe((Class)PlayerInteractEvent.class).handler(playerInteractEvent -> {
                if (!(!playerInteractEvent.getAction().equals((Object)Action.RIGHT_CLICK_AIR))) {
                    if (playerInteractEvent.getItem() != null && playerInteractEvent.getPlayer() != null) {
                        if (playerInteractEvent.getItem().getType().toString().toLowerCase().contains("boots") || playerInteractEvent.getItem().getType().toString().toLowerCase().contains("leggings") || playerInteractEvent.getItem().getType().toString().toLowerCase().contains("chestplate") || playerInteractEvent.getItem().getType().toString().toLowerCase().contains("helmet")) {
                            NBTItem.get(playerInteractEvent.getItem()).getInteger("MMOITEMS_EQUIP_PRIORITY");
                            if (playerInteractEvent.getItem().getType().toString().toLowerCase().contains("helmet")) {
                                if (n >= NBTItem.get(playerInteractEvent.getPlayer().getInventory().getHelmet()).getInteger("MMOITEMS_EQUIP_PRIORITY")) {
                                    playerInteractEvent.getPlayer().getInventory().getHelmet();
                                }
                            }
                            else if (playerInteractEvent.getItem().getType().toString().toLowerCase().contains("chestplate")) {
                                if (n >= NBTItem.get(playerInteractEvent.getPlayer().getInventory().getChestplate()).getInteger("MMOITEMS_EQUIP_PRIORITY")) {
                                    playerInteractEvent.getPlayer().getInventory().getChestplate();
                                }
                            }
                            else if (playerInteractEvent.getItem().getType().toString().toLowerCase().contains("leggings")) {
                                if (n >= NBTItem.get(playerInteractEvent.getPlayer().getInventory().getLeggings()).getInteger("MMOITEMS_EQUIP_PRIORITY")) {
                                    playerInteractEvent.getPlayer().getInventory().getLeggings();
                                }
                            }
                            else if (n >= NBTItem.get(playerInteractEvent.getPlayer().getInventory().getBoots()).getInteger("MMOITEMS_EQUIP_PRIORITY")) {
                                playerInteractEvent.getPlayer().getInventory().getBoots();
                            }
                        }
                    }
                }
            });
        }
    }
}
