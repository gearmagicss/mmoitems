// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.attribute.Attribute;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.ChatColor;
import org.bukkit.util.Vector;
import java.lang.reflect.Field;
import org.apache.commons.codec.binary.Base64;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.inventory.ItemStack;

public class MMOUtils
{
    private static final String[] romanChars;
    private static final int[] romanValues;
    
    public static String getSkullTextureURL(final ItemStack itemStack) {
        try {
            final Field declaredField = itemStack.getItemMeta().getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            return new String(Base64.decodeBase64(((GameProfile)declaredField.get(itemStack.getItemMeta())).getProperties().get((Object)"textures").toArray(new Property[0])[0].getValue())).replace("{textures:{SKIN:{url:\"", "").replace("\"}}}", "");
        }
        catch (Exception ex) {
            return "";
        }
    }
    
    public static Vector normalize(final Vector vector) {
        return (vector.getX() == 0.0 && vector.getY() == 0.0) ? vector : vector.normalize();
    }
    
    public static double parseDouble(final String s) {
        try {
            return Double.parseDouble(s);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Could not read number from '" + s + "'");
        }
    }
    
    public static String getProgressBar(final double n, final int n2, final String str) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n2; ++i) {
            sb.append(str);
        }
        return sb.substring(0, (int)(n * n2)) + ChatColor.WHITE + sb.substring((int)(n * n2));
    }
    
    public static LivingEntity getDamager(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamager() instanceof LivingEntity) {
            return (LivingEntity)entityDamageByEntityEvent.getDamager();
        }
        if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
            final Projectile projectile = (Projectile)entityDamageByEntityEvent.getDamager();
            if (projectile.getShooter() instanceof LivingEntity) {
                return (LivingEntity)projectile.getShooter();
            }
        }
        return null;
    }
    
    public static int getEffectDuration(final PotionEffectType potionEffectType) {
        return (potionEffectType.equals((Object)PotionEffectType.NIGHT_VISION) || potionEffectType.equals((Object)PotionEffectType.CONFUSION)) ? 260 : (potionEffectType.equals((Object)PotionEffectType.BLINDNESS) ? 140 : 80);
    }
    
    @NotNull
    public static String getDisplayName(@Nullable final ItemStack itemStack) {
        if (itemStack == null) {
            return "null";
        }
        return (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) ? itemStack.getItemMeta().getDisplayName() : caseOnWords(itemStack.getType().name().toLowerCase().replace("_", " "));
    }
    
    public static boolean twoHandedCase(final Player player) {
        return PlayerData.get((OfflinePlayer)player).areHandsFull();
    }
    
    public static String caseOnWords(final String str) {
        final StringBuilder sb = new StringBuilder(str);
        int n = 1;
        for (int i = 0; i < sb.length(); ++i) {
            final char char1 = sb.charAt(i);
            if (n != 0 && char1 >= 'a' && char1 <= 'z') {
                sb.setCharAt(i, (char)(char1 - 32));
                n = 0;
            }
            else {
                n = ((char1 == ' ') ? 1 : 0);
            }
        }
        return sb.toString();
    }
    
    public static boolean isMetaItem(final ItemStack itemStack, final boolean b) {
        return itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getDisplayName() != null && (!b || itemStack.getItemMeta().getLore() != null);
    }
    
    public static void saturate(@NotNull final Player player, final double n) {
        saturate(player, n, false);
    }
    
    public static void saturate(@NotNull final Player player, final double n, final boolean b) {
        if (n > 0.0 || b) {
            player.setSaturation(Math.min(0.0f, Math.min(20.0f, player.getSaturation() + (float)n)));
        }
    }
    
    public static void feed(@NotNull final Player player, final int n) {
        feed(player, n, false);
    }
    
    public static void feed(@NotNull final Player player, final int n, final boolean b) {
        if (n > 0 || b) {
            player.setFoodLevel(Math.max(Math.min(20, player.getFoodLevel() + n), 0));
        }
    }
    
    public static void heal(@NotNull final LivingEntity livingEntity, final double n) {
        heal(livingEntity, n, false);
    }
    
    public static void heal(@NotNull final LivingEntity livingEntity, final double n, final boolean b) {
        if (n > 0.0 || b) {
            livingEntity.setHealth(Math.min(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), livingEntity.getHealth() + n));
        }
    }
    
    public static boolean canDamage(final Player player, final Entity entity) {
        return canDamage(player, null, entity);
    }
    
    public static boolean canDamage(final Entity entity) {
        return canDamage(null, null, entity);
    }
    
    public static boolean canDamage(@Nullable final Player obj, @Nullable final Location location, final Entity entity) {
        return !entity.equals(obj) && !entity.isDead() && entity instanceof LivingEntity && !(entity instanceof ArmorStand) && !MythicLib.plugin.getEntities().findCustom(entity) && (!(entity instanceof Player) || (MMOItems.plugin.getLanguage().abilityPlayerDamage && MMOItems.plugin.getFlags().isPvpAllowed(entity.getLocation()))) && (location == null || MythicLib.plugin.getVersion().getWrapper().isInBoundingBox(entity, location));
    }
    
    public static String intToRoman(int i) {
        if (i < 1 || i > 3999) {
            throw new IllegalArgumentException("Input must be between 1 and 3999");
        }
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < MMOUtils.romanChars.length; ++j) {
            while (i >= MMOUtils.romanValues[j]) {
                sb.append(MMOUtils.romanChars[j]);
                i -= MMOUtils.romanValues[j];
            }
        }
        return sb.toString();
    }
    
    public static double truncation(final double n, final int n2) {
        final double pow = Math.pow(10.0, n2);
        return Math.floor(n * pow) / pow;
    }
    
    public static Vector rotAxisX(final Vector vector, final double n) {
        return vector.setY(vector.getY() * Math.cos(n) - vector.getZ() * Math.sin(n)).setZ(vector.getY() * Math.sin(n) + vector.getZ() * Math.cos(n));
    }
    
    public static Vector rotAxisY(final Vector vector, final double n) {
        return vector.setX(vector.getX() * Math.cos(n) + vector.getZ() * Math.sin(n)).setZ(vector.getX() * -Math.sin(n) + vector.getZ() * Math.cos(n));
    }
    
    public static Vector rotAxisZ(final Vector vector, final double n) {
        return vector.setX(vector.getX() * Math.cos(n) - vector.getY() * Math.sin(n)).setY(vector.getX() * Math.sin(n) + vector.getY() * Math.cos(n));
    }
    
    public static Vector rotateFunc(Vector vector, final Location location) {
        final double n = location.getYaw() / 180.0f * 3.141592653589793;
        vector = rotAxisX(vector, location.getPitch() / 180.0f * 3.141592653589793);
        vector = rotAxisY(vector, -n);
        return vector;
    }
    
    public static List<Entity> getNearbyChunkEntities(final Location location) {
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<Entity>();
        final int x = location.getChunk().getX();
        final int z = location.getChunk().getZ();
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                list.addAll(Arrays.asList(location.getWorld().getChunkAt(x + i, z + j).getEntities()));
            }
        }
        return (List<Entity>)list;
    }
    
    public static ItemStack readIcon(final String s) {
        final String[] split = s.split(":");
        final Material value = Material.valueOf(split[0].toUpperCase().replace("-", "_").replace(" ", "_"));
        return (split.length > 1) ? MythicLib.plugin.getVersion().getWrapper().textureItem(value, Integer.parseInt(split[1])) : new ItemStack(value);
    }
    
    static {
        romanChars = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        romanValues = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    }
}
