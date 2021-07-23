// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util;

import java.lang.reflect.Field;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.bukkit.inventory.ItemFlag;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.version.VersionMaterial;

public class CustomSkull extends ConfigItem
{
    private final String textureValue;
    
    public CustomSkull(final String s, final String s2) {
        this(s, s2, null, new String[0]);
    }
    
    public CustomSkull(final String s, final String textureValue, final String s2, final String... array) {
        super(s, VersionMaterial.PLAYER_HEAD.toMaterial(), s2, array);
        this.textureValue = textureValue;
    }
    
    @Override
    public void updateItem() {
        this.setItem(VersionMaterial.PLAYER_HEAD.toItem());
        final ItemMeta itemMeta = this.getItem().getItemMeta();
        itemMeta.setDisplayName(MythicLib.plugin.parseColors(this.getName()));
        itemMeta.addItemFlags(ItemFlag.values());
        final GameProfile value = new GameProfile(UUID.randomUUID(), (String)null);
        value.getProperties().put((Object)"textures", (Object)new Property("textures", this.textureValue));
        try {
            final Field declaredField = itemMeta.getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            declaredField.set(itemMeta, value);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load skull texture");
        }
        if (this.hasLore()) {
            final ArrayList<String> lore = new ArrayList<String>();
            this.getLore().forEach(s -> lore.add(ChatColor.GRAY + MythicLib.plugin.parseColors(s)));
            itemMeta.setLore((List)lore);
        }
        this.getItem().setItemMeta(itemMeta);
        this.setItem(MythicLib.plugin.getVersion().getWrapper().getNBTItem(this.getItem()).addTag(new ItemTag[] { new ItemTag("ItemId", (Object)this.getId()) }).toItem());
    }
}
