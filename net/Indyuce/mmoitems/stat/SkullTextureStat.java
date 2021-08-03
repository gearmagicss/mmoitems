// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import java.lang.reflect.Field;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.MMOItems;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import com.mojang.authlib.properties.Property;
import net.Indyuce.mmoitems.stat.data.SkullTextureData;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class SkullTextureStat extends StringStat
{
    public SkullTextureStat() {
        super("SKULL_TEXTURE", VersionMaterial.PLAYER_HEAD.toMaterial(), "Skull Texture", new String[] { "The head texture &nvalue&7.", "Can be found on heads databases." }, new String[] { "all" }, new Material[] { VersionMaterial.PLAYER_HEAD.toMaterial() });
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        final String string = configurationSection.getString("value");
        Validate.notNull((Object)string, "Could not load skull texture value");
        final String string2 = configurationSection.getString("uuid");
        Validate.notNull((Object)string2, "Could not find skull texture UUID: re-enter your skull texture value and one will be selected randomly.");
        final SkullTextureData skullTextureData = new SkullTextureData(new GameProfile(UUID.fromString(string2), (String)null));
        skullTextureData.getGameProfile().getProperties().put((Object)"textures", (Object)new Property("textures", string));
        return skullTextureData;
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        editionInventory.getEditedSection().set("skull-texture.value", (Object)str);
        editionInventory.getEditedSection().set("skull-texture.uuid", (Object)UUID.randomUUID().toString());
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to " + str + ".");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (itemStackBuilder.getItemStack().getType() != VersionMaterial.PLAYER_HEAD.toMaterial()) {
            return;
        }
        try {
            final Field declaredField = itemStackBuilder.getMeta().getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            declaredField.set(itemStackBuilder.getMeta(), ((SkullTextureData)statData).getGameProfile());
        }
        catch (NoSuchFieldException | IllegalAccessException ex) {
            final Object o;
            throw new IllegalArgumentException(((Throwable)o).getMessage());
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        try {
            final Field declaredField = readMMOItem.getNBT().getItem().getItemMeta().getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            readMMOItem.setData(ItemStats.SKULL_TEXTURE, new SkullTextureData((GameProfile)declaredField.get(readMMOItem.getNBT().getItem().getItemMeta())));
        }
        catch (NoSuchFieldException ex) {}
        catch (IllegalArgumentException ex2) {}
        catch (IllegalAccessException ex3) {}
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new SkullTextureData(new GameProfile(UUID.fromString("df930b7b-a84d-4f76-90ac-33be6a5b6c88"), "gunging"));
    }
}
