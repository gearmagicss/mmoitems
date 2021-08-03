// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ConsumableItemInteraction
{
    boolean handleConsumableEffect(@NotNull final InventoryClickEvent p0, @NotNull final PlayerData p1, @NotNull final Consumable p2, @NotNull final NBTItem p3, @Nullable final Type p4);
}
