// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import io.lumine.mythic.lib.api.item.NBTItem;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.crafting.uifilters.VanillaUIFilter;
import io.lumine.mythic.lib.api.crafting.uimanager.UIFilterCountermatch;

public class VanillaMMOItemCountermatch implements UIFilterCountermatch
{
    public static void enable() {
        VanillaUIFilter.get().addMatchOverride((UIFilterCountermatch)new VanillaMMOItemCountermatch());
    }
    
    public boolean preventsMatching(@NotNull final ItemStack itemStack, @Nullable final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        return NBTItem.get(itemStack).hasType();
    }
}
