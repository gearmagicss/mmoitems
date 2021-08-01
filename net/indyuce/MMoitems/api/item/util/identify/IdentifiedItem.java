// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util.identify;

import java.io.IOException;
import java.io.InputStream;
import org.bukkit.util.io.BukkitObjectInputStream;
import java.io.ByteArrayInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.NBTItem;

public class IdentifiedItem
{
    private final NBTItem item;
    
    public IdentifiedItem(final NBTItem item) {
        this.item = item;
    }
    
    public ItemStack identify() {
        return this.deserialize(this.item.getString("MMOITEMS_UNIDENTIFIED_ITEM"));
    }
    
    private ItemStack deserialize(final String s) {
        try {
            final BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream((InputStream)new ByteArrayInputStream(Base64Coder.decodeLines(s)));
            final ItemStack itemStack = (ItemStack)bukkitObjectInputStream.readObject();
            bukkitObjectInputStream.close();
            return itemStack;
        }
        catch (ClassNotFoundException | IOException ex) {
            final Throwable t;
            t.printStackTrace();
            return null;
        }
    }
}
